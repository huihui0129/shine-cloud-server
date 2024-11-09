package com.hh.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.mybatis.eneity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User extends BaseEntity implements Serializable {

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}