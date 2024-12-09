package com.shine.security.authorization.impl;

import com.shine.security.authorization.Principal;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/7 11:43
 * @description AuthorityPrincipal
 */
@Data
public final class AuthorityPrincipal implements Principal {

    private Long id;

    private String username;

    private String password;

}