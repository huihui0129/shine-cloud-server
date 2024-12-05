package com.shine.user.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

<#assign hasDate = columnList?filter(item -> item.javaType?contains("LocalDateTime"))>
<#if (hasDate?size > 0)>
import java.time.LocalDateTime;
</#if>

@Data
public class ${className} {

<#-- 循环字段生成代码 -->
<#list columnList as column>
    @Schema(description = "${column.comment}")
    private ${column.javaType} ${column.fieldName};

</#list>
}