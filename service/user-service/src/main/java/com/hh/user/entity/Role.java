package com.hh.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.mybatis.eneity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
@Data
@TableName("role")
public class Role extends BaseEntity {

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    public static final String APP_ID = "app_id";

    public static final String ROLE_NAME = "role_name";
}
