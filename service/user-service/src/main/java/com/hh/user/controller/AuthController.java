package com.hh.user.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hh.common.response.Result;
import com.hh.user.response.CaptchaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author huihui
 * @date 2024/11/13 13:26
 * @description AuthController
 */
@Slf4j
@Tag(name = "认证授权 Controller", description = "认证授权 Controller")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Operation(summary = "获取验证码图片")
    @GetMapping("/getCaptcha")
    public Result<CaptchaResponse> getCaptcha() {
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
            redisTemplate.opsForValue().setIfAbsent("captch", captchaText, 100, TimeUnit.SECONDS);
            return Result.success(response);
        } catch (IOException e) {
            log.error("生成图片验证码失败", e);
            throw new RuntimeException(e);
        }
    }

}
