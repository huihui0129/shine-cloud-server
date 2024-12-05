package com.shine.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.eneity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "oauth_access_token")
public class AccessToken extends BaseEntity {

    /**
     * 客户端ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 访问令牌
     */
    @TableField(value = "access_token")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @TableField(value = "refresh_token")
    private String refreshToken;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 授权类型
     */
    @TableField(value = "grant_type")
    private String grantType;

    /**
     * 授权范围
     */
    @TableField(value = "scope")
    private String scope;

    /**
     * token类型
     */
    @TableField(value = "token_type")
    private String tokenType;

    /**
     * 重定向地址
     */
    @TableField(value = "redirect_uri")
    private String redirectUri;

    /**
     * 访问令牌过期时间
     */
    @TableField(value = "access_token_expire_time")
    private LocalDateTime accessTokenExpireTime;

    /**
     * 刷新令牌过期时间
     */
    @TableField(value = "refresh_token_expire_time")
    private LocalDateTime refreshTokenExpireTime;

}