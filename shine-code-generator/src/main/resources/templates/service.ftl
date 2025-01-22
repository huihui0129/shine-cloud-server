package ${packagePath}.${moduleName}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ${packagePath}.${moduleName}.entity.${entityName};
import ${packagePath}.${moduleName}.info.${infoName};
<#assign hasPage = methodList?filter(item -> item?string == "PAGE")>
<#if (hasPage?size > 0)>
import ${packagePath}.${moduleName}.request.${entityName}PageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
</#if>

/**
 * @author ${author}
 * @date ${generatorDate}
 * @description ${className}
 */
public interface ${className} extends IService<${entityName}> {

<#assign hasPage = methodList?filter(item -> item?string == "PAGE")>
<#if (hasPage?size > 0)>
    /**
     * 分页查询${comment}
     *
     * @param request
     * @return
     */
    IPage<${infoName}> pageQuery(${entityName}PageRequest request);
</#if>

<#assign hasGet = methodList?filter(item -> item?string == "GET")>
<#if (hasGet?size > 0)>
   /**
    * 详情查询${comment}
    *
    * @param id
    * @return
    */
    ${infoName} getById(Long id);
</#if>

<#assign hasDelete = methodList?filter(item -> item?string == "DELETE")>
<#if (hasDelete?size > 0)>
   /**
    * 根据ID删除${comment}
    *
    * @param id
    * @return
    */
   Boolean deleteById(Long id);
</#if>

}
