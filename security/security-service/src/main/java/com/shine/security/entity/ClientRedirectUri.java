package com.shine.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.entity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "oauth_client_redirect_uri")
public class ClientRedirectUri extends BaseEntity {

    /**
    * 客户点ID
    */
    @TableField(value = "client_id")
    private String clientId;

    /**
    * 允许的重定向地址
    */
    @TableField(value = "redirect_uri")
    private String redirectUri;

}