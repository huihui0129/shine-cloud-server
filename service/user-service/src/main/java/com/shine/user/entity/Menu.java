package com.shine.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.entity.BaseEntity;
import lombok.Data;


@Data
@TableName(value = "menu")
public class Menu extends BaseEntity {

    /**
    * 应用ID
    */
    @TableField(value = "app_id")
    private Long appId;

    /**
    * 父ID
    */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
    * 菜单名称
    */
    @TableField(value = "name")
    private String name;

    /**
    * 菜单路由
    */
    @TableField(value = "path")
    private String path;

    /**
    * 权限
    */
    @TableField(value = "permission")
    private String permission;

    /**
    * 枚举 类型：0-菜单|1-按钮
    */
    @TableField(value = "type")
    private Integer type;

    /**
    * 菜单图标
    */
    @TableField(value = "icon")
    private String icon;

    /**
    * 排序
    */
    @TableField(value = "sort")
    private Integer sort;

}