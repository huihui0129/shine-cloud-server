package com.shine.security.authorization;

/**
 * @author huihui
 * @date 2024/11/22 13:19
 * @description Principal
 */
public interface Principal {

    Long getId();

    String getUsername();

    String getPassword();

}
