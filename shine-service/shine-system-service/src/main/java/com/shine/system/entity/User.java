package com.shine.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.entity.BaseEntity;
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

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 头像
     */
    @TableField(value = "head_image")
    private String headImage;

}