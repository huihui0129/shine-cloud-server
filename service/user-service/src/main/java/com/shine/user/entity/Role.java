package com.shine.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.eneity.BaseEntity;
import lombok.Data;


@Data
@TableName(value = "role")
public class Role extends BaseEntity {

    /**
    * 应用ID
    */
    @TableField(value = "app_id")
    private Long appId;

    /**
    * 角色编码
    */
    @TableField(value = "code")
    private String code;

    /**
    * 角色名称
    */
    @TableField(value = "name")
    private String name;

}