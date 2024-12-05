package com.shine.security.service;

import com.shine.security.request.CaptchaVerifyRequest;
import com.shine.security.request.LoginRequest;
import com.shine.security.response.CaptchaResponse;
import com.shine.security.response.UserLoginResponse;

/**
 * @author huihui
 * @date 2024/12/4 14:49
 * @description LoginService
 */
public interface LoginService {

    /**
     * 获取验证码
     *
     * @return
     */
    CaptchaResponse getCaptcha();

    /**
     * 验证验证码
     *
     * @param request
     */
    void verifyCaptcha(CaptchaVerifyRequest request);

    /**
     * 登录
     *
     * @param request
     * @return
     */
    UserLoginResponse login(LoginRequest request);

}
