package com.hh.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hh.mybatis.eneity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "oauth_authorization_code")
public class AuthorizationCode extends BaseEntity {

    /**
     * 客户点ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 授权码
     */
    @TableField(value = "authorization_code")
    private String authorizationCode;

    /**
     * 重定向地址
     */
    @TableField(value = "redirect_uri")
    private String redirectUri;

    /**
     * 授权范围
     */
    @TableField(value = "scope")
    private String scope;

    /**
     * 过期时间
     */
    @TableField(value = "expire_at")
    private LocalDateTime expireAt;

    /**
     * 枚举：授权码状态：1：未使用 2：已使用 3：已过期
     */
    @TableField(value = "status")
    private Integer status;

}