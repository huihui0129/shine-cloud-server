package com.hh.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hh.common.exception.BaseException;
import com.hh.common.status.ResponseStatus;
import com.hh.security.authorization.AuthorityPrincipal;
import com.hh.security.password.PasswordEncoder;
import com.hh.security.token.TokenManager;
import com.hh.user.constant.UserConstant;
import com.hh.user.entity.User;
import com.hh.user.info.UserInfo;
import com.hh.user.mapper.UserMapper;
import com.hh.user.request.CaptchaVerifyRequest;
import com.hh.user.request.LoginRequest;
import com.hh.user.response.CaptchaResponse;
import com.hh.user.response.UserLoginResponse;
import com.hh.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author huihui
 * @date 2024/11/9 23:53
 * @description UserServiceImpl
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserInfo getUserById(Long id) {
        if (id == null) {
            return null;
        }
        User user = this.getOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getId, id)
        );
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setDeleted(user.getDeleted());
        return userInfo;
    }

    @Override
    public CaptchaResponse getCaptcha() {
        try {
            // 生成验证码文本
            String captchaText = defaultKaptcha.createText();
            // 生成验证码图片
            BufferedImage captchaImage = defaultKaptcha.createImage(captchaText);
            // 将图片写入 ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(captchaImage, "jpeg", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            // 将图片字节数组编码为 base64 字符串
            String base64Image = Base64Utils.encodeToString(imageBytes);
            // 可将 captchaText 保存到 session 或 Redis 中以供验证
            // 返回 base64 编码的图片字符串，格式为 "data:image/jpeg;base64,BASE64_STRING"
            CaptchaResponse response = new CaptchaResponse();
            response.setImageBase64("data:image/jpeg;base64," + base64Image);
            // 生成唯一的ID
            String uuid = UUID.randomUUID().toString();
            response.setUuid(uuid);
            redisTemplate.opsForValue().set(UserConstant.CAPTCHA_CATCH_KEY_PREFIX + uuid, captchaText, UserConstant.CAPTCHA_CATCH_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            return response;
        } catch (IOException e) {
            log.error("生成图片验证码失败", e);
            throw new BaseException(ResponseStatus.ERROR, "哎呀，验证码生成失败了，要不重新试试？");
        }
    }

    @Override
    public void verifyCaptcha(CaptchaVerifyRequest request) {
        if (StringUtils.isBlank(request.getCaptcha())) {
            throw new BaseException(ResponseStatus.ERROR, "我没有看见你输入的验证码哦，请输入呢");
        }
        String key = UserConstant.CAPTCHA_CATCH_KEY_PREFIX + request.getUuid();
        String captcha = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(captcha)) {
            throw new BaseException(ResponseStatus.ERROR, "还没有获取验证码哦，请获取呢");
        }
        if (!StringUtils.equals(captcha, request.getCaptcha())) {
            throw new BaseException(ResponseStatus.ERROR, "验证码输入的不对哦，请仔细看看呢");
        }
        // 删除redis缓存
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("缓存删除失败了");
        }
    }

    @Override
    public UserLoginResponse login(LoginRequest request) {
        CaptchaVerifyRequest captchaVerifyRequest = new CaptchaVerifyRequest();
        captchaVerifyRequest.setUuid(request.getUuid());
        captchaVerifyRequest.setCaptcha(request.getCaptcha());
        this.verifyCaptcha(captchaVerifyRequest);
        // 验证账号密码
        User user = this.getOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, request.getUsername())
        );
        if (user == null) {
            throw new BaseException(ResponseStatus.ERROR, "用户名不存在哦，请仔细看看呢");
        }
        // 验证密码
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) {
            throw new BaseException(ResponseStatus.UNAUTHORIZED, "密码错了呢，好好想想");
        }
        // 生成token
        AuthorityPrincipal principal = new AuthorityPrincipal();
        principal.setId(user.getId());
        principal.setUsername(user.getUsername());
        String token = TokenManager.generate(principal);
        UserLoginResponse response = new UserLoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setToken(token);
        response.setUrl(user.getHeadImage());
        return response;
    }
}
