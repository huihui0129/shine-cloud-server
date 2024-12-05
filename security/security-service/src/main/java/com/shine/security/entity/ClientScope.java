package com.shine.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shine.mybatis.eneity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "oauth_client_scope")
public class ClientScope extends BaseEntity {

    /**
    * 客户点ID
    */
    @TableField(value = "client_id")
    private String clientId;

    /**
    * 授权范围
    */
    @TableField(value = "scope")
    private String scope;

}