package com.hh.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hh.common.exception.BaseException;
import com.hh.common.response.Result;
import com.hh.common.status.ResponseStatus;
import com.hh.user.constant.UserConstant;
import com.hh.user.entity.User;
import com.hh.user.request.CaptchaVerifyRequest;
import com.hh.user.request.LoginRequest;
import com.hh.user.response.CaptchaResponse;
import com.hh.user.response.UserLoginResponse;
import com.hh.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private UserService userService;

    @Operation(summary = "获取验证码图片")
    @GetMapping("/captcha/getCaptcha")
    public Result<CaptchaResponse> getCaptcha() {
        CaptchaResponse response = userService.getCaptcha();
        return Result.success(response);
    }

    @Operation(summary = "验证验证码")
    @PostMapping("/captcha/verify")
    public Result<?> verifyCaptcha(@RequestBody CaptchaVerifyRequest request) {
        userService.verifyCaptcha(request);
        return Result.success();
    }

    @Operation(summary = "登录")
    @PostMapping("/user/login")
    public Result<UserLoginResponse> login(@RequestBody LoginRequest request) {
        UserLoginResponse response = userService.login(request);
        return Result.success(response);
    }

}
