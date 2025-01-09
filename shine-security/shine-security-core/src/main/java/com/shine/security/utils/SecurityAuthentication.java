package com.shine.security.utils;

import com.shine.security.authorization.Principal;
import com.shine.security.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 验权
 */
@Slf4j
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
        if (CollectionUtils.isEmpty(roleList) || StringUtils.isBlank(role)) {
            log.error("用户：{} 无所需角色：{}", user.getId(), role);
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
    public boolean hasAuthority(String permission) {
        Principal user = getCurrentUser();
        List<String> permissionList = user.getPermissionList();
        if (CollectionUtils.isEmpty(permissionList) || StringUtils.isBlank(permission)) {
            log.error("用户：{} 无所需权限：{}", user.getId(), permission);
            return false;
        }
        return permissionList.contains(permission);
    }

    /**
     * 验证是否是指定用户
     *
     * @param username 用户名
     * @return 是否验证通过
     */
    public boolean hasUsername(String username) {
        if (StringUtils.isBlank(username)) {
            log.error("需要用户名");
            return false;
        }
        Principal user = getCurrentUser();
        if (!StringUtils.equals(username, user.getUsername())) {
            log.error("用户：{} 非此用户：{}", user.getId(), username);
            return false;
        }
        return true;
    }

}
