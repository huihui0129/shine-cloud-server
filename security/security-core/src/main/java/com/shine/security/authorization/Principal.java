package com.shine.security.authorization;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/22 13:19
 * @description Principal
 *
 * 注意：因为使用反射获取属性以及对应值，不能读取到父类属性值，所以实现类不可被继承，请使用final修饰
 */
public interface Principal {

    String CLIENT_ID_KEY = "clientId";
    String USER_ID_KEY = "userId";
    String USERNAME_KEY = "username";
    String ROLE_KEY = "role";
    String PERMISSION_KEY = "permission";

    String getClientId();

    Long getId();

    String getUsername();

    String getPassword();

    List<String> getRoleList();

    List<String> getPermissionList();

    default boolean hasRole(String role) {
        List<String> roleList = getRoleList();
        if (CollectionUtils.isEmpty(roleList) || StringUtils.isBlank(role)) {
            return false;
        }
        return roleList.contains(role);
    }

}
