package com.hh.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.mybatis.eneity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 用户角色
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
@Data
@TableName("user_role")
public class UserRole extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    public static final String USER_ID = "user_id";

    public static final String ROLE_ID = "role_id";
}
