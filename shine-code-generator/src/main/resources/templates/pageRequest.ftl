package ${packagePath}.${moduleName}.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ${packagePath}.mybatis.request.BaseQueryRequest;

@Data
public class ${className} extends BaseQueryRequest {

<#-- 循环字段生成代码 -->
<#list columnList as column>
    @Schema(description = "${column.comment}")
    private ${column.javaType} ${column.fieldName};

</#list>
}