package com.hh.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.mybatis.eneity.BaseEntity;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/4 10:28
 * @description User
 */
@Data
@TableName(value = "user")
public class User extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(exist = false)
    private String password;

}
