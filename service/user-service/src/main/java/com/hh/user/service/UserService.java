package com.hh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.user.entity.User;
import com.hh.user.info.UserInfo;
import com.hh.user.request.CaptchaVerifyRequest;
import com.hh.user.request.LoginRequest;
import com.hh.user.response.CaptchaResponse;
import com.hh.user.response.UserLoginResponse;

/**
 * @author huihui
 * @date 2024/11/9 23:52
 * @description UserService
 */
public interface UserService extends IService<User> {

    UserInfo getUserById(Long id);

    CaptchaResponse getCaptcha();

    void verifyCaptcha(CaptchaVerifyRequest request);

    UserLoginResponse login(LoginRequest request);

}
