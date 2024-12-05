package com.shine.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.eneity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
@Data
@TableName("role_menu")
public class RoleMenu extends BaseEntity {

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;

    public static final String ROLE_ID = "role_id";

    public static final String MENU_ID = "menu_id";
}
