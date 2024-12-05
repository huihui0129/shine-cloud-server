package com.hh.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import eneity.com.shine.mybatis.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "${tableName}")
public class ${className} extends BaseEntity {

<#-- 循环字段生成代码 -->
<#list columnList as column>
    /**
    * ${column.comment!""}
    */
    @TableField(value = "${column.columnName}")
    private ${column.javaType} ${column.fieldName};

</#list>
}