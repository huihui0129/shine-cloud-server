package com.shine.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import eneity.com.shine.mybatis.BaseEntity;
import lombok.Data;

<#assign hasDate = columnList?filter(item -> item.javaType?contains("LocalDateTime"))>
<#if (hasDate?size > 0)>
    import java.time.LocalDateTime;
</#if>

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