package com.shine.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.eneity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "oauth_client")
public class Client extends BaseEntity {

    /**
    * 客户点ID
    */
    @TableField(value = "client_id")
    private String clientId;

    /**
    * 客户端密钥
    */
    @TableField(value = "client_secret")
    private String clientSecret;

    /**
    * 客户端名称
    */
    @TableField(value = "client_name")
    private String clientName;

    /**
    * 访问令牌默认失效时间
    */
    @TableField(value = "access_token_lefetime")
    private Integer accessTokenLefetime;

    /**
    * 刷新令牌默认失效时间
    */
    @TableField(value = "refresh_token_lefetime")
    private Integer refreshTokenLefetime;

    /**
    * 枚举：客户端状态：0：禁用，1：启用
    */
    @TableField(value = "status")
    private Integer status;

}