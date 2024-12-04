package com.hh.security.service;

import com.hh.security.request.CaptchaVerifyRequest;
import com.hh.security.request.LoginRequest;
import com.hh.security.response.CaptchaResponse;
import com.hh.security.response.UserLoginResponse;

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
