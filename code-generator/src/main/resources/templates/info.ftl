package com.hh.user.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ${className} {

<#-- 循环字段生成代码 -->
<#list columnList as column>
    @Schema(description = "${column.comment}")
    private ${column.javaType} ${column.fieldName};

</#list>
}