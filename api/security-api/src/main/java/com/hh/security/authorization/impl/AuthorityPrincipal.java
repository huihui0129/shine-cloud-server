package com.hh.security.authorization.impl;

import com.hh.security.authorization.Principal;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/7 11:43
 * @description AuthorityPrincipal
 */
@Data
public class AuthorityPrincipal implements Principal {

    private Long id;

    private String username;

    private String password;

}
