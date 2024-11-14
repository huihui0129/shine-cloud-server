package com.hh.security.password.impl;

import com.hh.security.password.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author huihui
 * @date 2024/11/12 14:41
 * @description BCryptPasswordEncoder
 */
public class BCryptPasswordEncoder implements PasswordEncoder {

    /**
     * 加密密码
     *
     * @param password 密码
     * @return
     */
    @Override
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 验证密码
     *
     * @param plainPassword 密码
     * @param hashedPassword 加密的密码
     * @return
     */
    @Override
    public boolean matches(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }

}
