<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packagePath}.${moduleName}.mapper.${entityName}Mapper">

    <#assign hasPage = methodList?filter(item -> item?string == "PAGE")>
    <#if (hasPage?size > 0)>
    <select id="page${entityName}" resultType="${packagePath}.${moduleName}.info.${infoName}">
        select t1.*
        from ${datasourceName}.${tableName} t1
        where t1.deleted = 0
        <#if (columnList?size > 0)>
        <#list columnList as column>
        <#if (column.javaType == "String")>
        <if test="request.${column.fieldName} != null and request.${column.fieldName} != ''">
            and t1.${column.columnName} like concat('%', ${"#"}{request.${column.fieldName}}, '%')
        </if>
        <#else>
        <if test="request.${column.fieldName} != null">
            and t1.${column.columnName} = ${"#"}{request.${column.fieldName}}
        </if>
        </#if>
        </#list>
        </#if>
        order by t1.create_time desc
    </select>
    </#if>

    <#assign hasGet = methodList?filter(item -> item?string == "GET")>
    <#if (hasGet?size > 0)>
    <select id="getById" resultType="${packagePath}.${moduleName}.info.${infoName}">
        select t1.*
        from ${datasourceName}.${tableName} t1
        where t1.deleted = 0
        and t1.id = ${"#"}{id}
    </select>
    </#if>

</mapper>