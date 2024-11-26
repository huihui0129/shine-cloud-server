package com.hh.security.password;

/**
 * @author huihui
 * @date 2024/11/12 14:39
 * @description PasswordEncode
 */
public interface PasswordEncoder {

    /**
     * 加密密码
     *
     * @param password
     * @return
     */
    String encode(String password);

    /**
     * 验证密码
     * @return
     */
    boolean matches(String plainPassword, String hashedPassword);

}
