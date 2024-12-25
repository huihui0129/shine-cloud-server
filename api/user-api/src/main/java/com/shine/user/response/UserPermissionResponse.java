package com.shine.user.response;

import com.shine.user.info.MenuInfo;
import com.shine.user.info.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author huihui
 * @date 2024/12/25 17:29
 * @description UserPermissionResponse
 */
@Data
public class UserPermissionResponse extends UserInfo {

    @Schema(description = "菜单集合")
    private List<MenuInfo> menuList;

    @Schema(description = "按钮权限集合")
    private List<String> buttonList;

}
