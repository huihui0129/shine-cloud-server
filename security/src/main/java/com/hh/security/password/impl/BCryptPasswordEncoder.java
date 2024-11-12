package com.hh.security.password.impl;

import com.hh.security.password.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/11/12 14:41
 * @description BCryptPasswordEncoder
 */
@Component
public class BCryptPasswordEncoder implements PasswordEncoder {

    /**
     * 加密密码
     *
     * @param password
     * @return
     */
    @Override
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
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
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
