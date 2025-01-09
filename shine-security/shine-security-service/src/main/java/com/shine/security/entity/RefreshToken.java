package com.shine.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "oauth_refresh_token")
public class RefreshToken extends BaseEntity {

    /**
     * 客户端ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 访问令牌
     */
    @TableField(value = "refresh_token")
    private String refreshToken;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 重定向地址
     */
    @TableField(value = "redirect_uri")
    private String redirectUri;

    /**
     * 令牌过期时间
     */
    @TableField(value = "expire_time")
    private LocalDateTime expireTime;

    /**
     * 已使用
     */
    @TableField(value = "used")
    private Boolean used;

    /**
     * 授权范围
     */
    @TableField(value = "scope")
    private String scope;

}