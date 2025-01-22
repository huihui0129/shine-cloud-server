package ${packagePath}.${moduleName}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packagePath}.${moduleName}.entity.${entityName};
import ${packagePath}.${moduleName}.info.${infoName};
import ${packagePath}.${moduleName}.request.${entityName}PageRequest;
<#assign hasPage = methodList?filter(item -> item?string == "PAGE")>
<#if (hasPage?size > 0)>
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
</#if>
<#if (methodList?size > 0)>
import org.apache.ibatis.annotations.Param;
</#if>

/**
 * @author ${author}
 * @date ${generatorDate}
 * @description ${className}
 */
public interface ${className} extends BaseMapper<${entityName}> {

<#assign hasPage = methodList?filter(item -> item?string == "PAGE")>
<#if (hasPage?size > 0)>
    /**
     * 分页查询${comment}
     *
     * @param iPage
     * @param request
     * @return
     */
    IPage<${infoName}> pageQuery(Page<${entityName}> iPage, @Param("request") ${entityName}PageRequest request);
</#if>

<#assign hasGet = methodList?filter(item -> item?string == "GET")>
<#if (hasGet?size > 0)>
    /**
     * 详情查询${comment}
     *
     * @param id
     * @return
     */
    ${infoName} getById(@Param("id") Long id);
</#if>

}
