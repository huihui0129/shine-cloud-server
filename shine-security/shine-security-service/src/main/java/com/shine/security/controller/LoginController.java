package com.shine.security.controller;

import com.shine.common.response.Result;
import com.shine.security.annotation.PreAuthorize;
import com.shine.security.request.CaptchaVerifyRequest;
import com.shine.security.request.LoginRequest;
import com.shine.security.request.UserRegisterRequest;
import com.shine.security.response.CaptchaResponse;
import com.shine.security.response.UserLoginResponse;
import com.shine.security.service.LoginService;
import com.shine.system.info.UserInfo;
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

    @Operation(summary = "获取用户信息")
    @GetMapping("/user/getUserInfo")
    public Result<UserInfo> getUserInfo() {
        UserInfo userInfo = loginService.getUserInfo();
        userInfo.setPassword("你想知道个屁");
        return Result.success(userInfo);
    }

    @Operation(summary = "用户注册")
    @PostMapping("/user/register")
    public Result<Boolean> registerUser(@RequestBody UserRegisterRequest request) {
        return Result.success(loginService.register(request));
    }

    @Operation(summary = "测试权限")
    @PreAuthorize("hasRole('system_admin')")
    @GetMapping("/test")
    public Result<CaptchaResponse> test() {
        CaptchaResponse captcha = loginService.getCaptcha();
        return Result.success(captcha);
    }

}
