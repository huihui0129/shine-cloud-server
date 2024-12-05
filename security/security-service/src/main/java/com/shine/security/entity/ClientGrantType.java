package com.shine.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.eneity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "oauth_client_grant_type")
public class ClientGrantType extends BaseEntity {

    /**
    * 客户点ID
    */
    @TableField(value = "client_id")
    private String clientId;

    /**
    * 支持的授权模式
    */
    @TableField(value = "grant_type")
    private String grantType;

}