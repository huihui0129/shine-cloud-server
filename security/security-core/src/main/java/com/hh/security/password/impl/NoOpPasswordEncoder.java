package com.hh.security.password.impl;

import com.hh.security.password.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;

/**
 * @author huihui
 * @date 2024/12/3 15:33
 * @description NoOpPasswordEncoder 不加密的密码加密器
 */
public class NoOpPasswordEncoder implements PasswordEncoder {

    /**
     * 加密密码
     *
     * @param password
     * @return
     */
    @Override
    public String encode(String password) {
        return password;
    }

    /**
     * 验证密码
     *
     * @param plainPassword
     * @param hashedPassword
     * @return
     */
    @Override
    public boolean matches(String plainPassword, String hashedPassword) {
        return StringUtils.equals(plainPassword, hashedPassword);
    }

}
