package com.shine.security.authorization.impl;

import com.shine.security.authorization.Principal;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/7 11:43
 * @description AuthorityPrincipal
 */
@Data
public final class AuthorityPrincipal implements Principal {

    private String clientId;

    private Long id;

    private String username;

    private String password;

    private List<String> roleList;

    private List<String> permissionList;

    @Override
    public boolean hasRole(String role) {
        List<String> roleList = getRoleList();
        if (CollectionUtils.isEmpty(roleList) || StringUtils.isBlank(role)) {
            return false;
        }
        return roleList.contains(role);
    }

}
