package com.shine.security.utils;

import com.shine.security.authorization.Principal;
import com.shine.security.context.SecurityContextHolder;

import java.util.List;

/**
 * 验权
 */
public class SecurityAuthentication {

    /**
     * 获取当前用户
     *
     * @return
     */
    private Principal getCurrentUser() {
        return SecurityContextHolder.getContext().getPrincipal();
    }

    /**
     * 验证用户是否具有某个角色
     *
     * @param role
     * @return
     */
    public boolean hasRole(String role) {
        Principal user = getCurrentUser();
        List<String> roleList = user.getRoleList();
        if (roleList == null || role == null) {
            return false;
        }
        return roleList.contains(role);
    }

    /**
     * 验证用户是否具有某个权限
     *
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        Principal user = getCurrentUser();
        List<String> permissionList = user.getPermissionList();
        if (permissionList == null || permission == null) {
            return false;
        }
        return permissionList.contains(permission);
    }

}
