package com.shine.security.controller;

import com.shine.common.response.Result;
import com.shine.security.request.CaptchaVerifyRequest;
import com.shine.security.request.LoginRequest;
import com.shine.security.response.CaptchaResponse;
import com.shine.security.response.UserLoginResponse;
import com.shine.security.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author huihui
 * @date 2024/12/4 14:48
 * @description LoginController
 */
@Slf4j
@Tag(name = "登录 Controller", description = "登录 Controller")
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Resource
    private LoginService loginService;

    @Operation(summary = "获取验证码图片")
    @GetMapping("/captcha/getCaptcha")
    public Result<CaptchaResponse> getCaptcha() {
        CaptchaResponse response = loginService.getCaptcha();
        return Result.success(response);
    }

    @Operation(summary = "验证验证码")
    @PostMapping("/captcha/verify")
    public Result<?> verifyCaptcha(@RequestBody CaptchaVerifyRequest request) {
        loginService.verifyCaptcha(request);
        return Result.success();
    }

    @Operation(summary = "登录")
    @PostMapping("/user/login")
    public Result<UserLoginResponse> login(@RequestBody LoginRequest request) {
        UserLoginResponse response = loginService.login(request);
        return Result.success(response);
    }

}
