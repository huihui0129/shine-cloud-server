package com.hh.security.authorization.impl;

import com.hh.security.authorization.Principal;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/12/3 15:53
 * @description ClientAuthorityPrincipal
 */
@Data
public final class ClientAuthorityPrincipal implements Principal {

    private String clientId;

    private Long id;

    private String username;

    private String password;

}
