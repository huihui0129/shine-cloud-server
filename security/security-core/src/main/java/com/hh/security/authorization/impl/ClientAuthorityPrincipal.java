package com.hh.security.authorization.impl;

import com.hh.security.authorization.Principal;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/12/3 15:53
 * @description ClientAuthorityPrincipal
 */
@Data
public class ClientAuthorityPrincipal extends AuthorityPrincipal implements Principal {

    private String clientId;

}
