package com.hh.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.mybatis.eneity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
@Data
@TableName("menu")
public class Menu extends BaseEntity {

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 父ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单路由
     */
    @TableField("path")
    private String path;

    /**
     * 权限
     */
    @TableField("permission")
    private String permission;

    /**
     * 类型 0：菜单 1：按钮
     */
    @TableField("type")
    private Byte type;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    public static final String APP_ID = "app_id";

    public static final String PARENT_ID = "parent_id";

    public static final String MENU_NAME = "menu_name";

    public static final String PATH = "path";

    public static final String PERMISSION = "permission";

    public static final String TYPE = "type";

    public static final String ICON = "icon";

    public static final String SORT = "sort";
}
