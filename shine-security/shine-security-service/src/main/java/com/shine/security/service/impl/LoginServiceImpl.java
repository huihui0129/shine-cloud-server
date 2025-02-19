package com.shine.security.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.shine.common.exception.BaseException;
import com.shine.common.response.Result;
import com.shine.common.status.ResponseStatus;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.constant.SecurityConstant;
import com.shine.security.context.SecurityContextHolder;
import com.shine.security.password.PasswordEncoder;
import com.shine.security.properties.SecurityProperties;
import com.shine.security.request.CaptchaVerifyRequest;
import com.shine.security.request.LoginRequest;
import com.shine.security.request.UserRegisterRequest;
import com.shine.security.response.CaptchaResponse;
import com.shine.security.response.UserLoginResponse;
import com.shine.security.service.LoginService;
import com.shine.security.token.TokenManager;
import com.shine.system.enums.UserStatusEnum;
import com.shine.system.feign.UserFeign;
import com.shine.system.info.UserInfo;
import com.shine.system.request.UserRequest;
import com.shine.system.response.UserPermissionResponse;
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
 * @date 2024/12/4 14:49
 * @description LoginServiceImpl
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private UserFeign userFeign;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SecurityProperties properties;

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
            redisTemplate.opsForValue().set(SecurityConstant.CAPTCHA_CATCH_KEY_PREFIX + uuid, captchaText, SecurityConstant.CAPTCHA_CATCH_TIMEOUT_SECONDS, TimeUnit.SECONDS);
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
        String key = SecurityConstant.CAPTCHA_CATCH_KEY_PREFIX + request.getUuid();
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
//        this.verifyCaptcha(captchaVerifyRequest);
        // 验证账号密码
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(request.getUsername());
        userRequest.setClientId("security");
        Result<UserPermissionResponse> userResult = userFeign.getUser(userRequest);
        if (!userResult.getSuccess()) {
            throw new BaseException(ResponseStatus.FEIGN_ERROR, "用户名不存在哦，请仔细看看呢");
        }
        UserPermissionResponse user = userResult.getData();
        // 验证密码
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) {
            throw new BaseException(ResponseStatus.UNAUTHORIZED, "密码错了呢，好好想想");
        }
        // 生成token
        AuthorityPrincipal principal = new AuthorityPrincipal();
        principal.setId(user.getId());
        principal.setClientId(properties.getClientId());
        principal.setUsername(user.getUsername());
        principal.setRoleList(user.getRoleList());
        principal.setPermissionList(user.getPermissionList());
        // 一天过期
        int expire = 86400;
        String token = TokenManager.generate(principal, expire);
        UserLoginResponse response = new UserLoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setToken(token);
        response.setUrl(user.getHeadImage());
        // 存入redis
        redisTemplate.opsForValue().set(SecurityConstant.TOKEN_REDIS_PREFIX + user.getId(), token, expire, TimeUnit.SECONDS);
        return response;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public UserInfo getUserInfo() {
        Long userId = SecurityContextHolder.getContext().getPrincipal().getId();
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(userId);
        Result<UserPermissionResponse> user = userFeign.getUser(userRequest);
        if (!user.getSuccess()) {
            throw new BaseException(ResponseStatus.FEIGN_ERROR, "获取用户信息失败哦");
        }
        return user.getData();
    }

    /**
     * 用户注册
     *
     * @param request
     * @return
     */
    @Override
    public Boolean register(UserRegisterRequest request) {
        Long userId = SecurityContextHolder.getContext().getPrincipal().getId();
        log.info("用户注册：{}", userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(request.getUsername());
        userInfo.setPassword(passwordEncoder.encode(request.getPassword()));
        userInfo.setNickName(request.getNickName());
        userInfo.setHeadImage(request.getHeadImage());
        userInfo.setStatus(UserStatusEnum.S1.getCode());
        userInfo.setCreateUser(userId);
        userInfo.setUpdateUser(userId);
        userInfo.setRemark(request.getRemark());
        Result<Boolean> result = userFeign.saveUser(userInfo);
        return result.getData();
    }
}
