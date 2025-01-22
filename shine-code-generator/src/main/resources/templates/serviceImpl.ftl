package ${packagePath}.${moduleName}.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packagePath}.${moduleName}.entity.${entityName};
import ${packagePath}.${moduleName}.info.${infoName};
import ${packagePath}.${moduleName}.mapper.${entityName}Mapper;
import ${packagePath}.${moduleName}.service.${entityName}Service;
import ${packagePath}.${moduleName}.info.${infoName};
<#assign hasPage = methodList?filter(item -> item?string == "PAGE")>
<#if (hasPage?size > 0)>
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${packagePath}.${moduleName}.request.${entityName}PageRequest;
</#if>
<#if (methodList?size > 0)>
import org.apache.ibatis.annotations.Param;
</#if>
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ${author}
 * @date ${generatorDate}
 * @description ${className}
 */
@Slf4j
@Service
public class ${className} extends ServiceImpl<${entityName}Mapper, ${entityName}> implements ${entityName}Service {

<#assign hasPage = methodList?filter(item -> item?string == "PAGE")>
<#if (hasPage?size > 0)>
    @Override
    public IPage<${infoName}> pageQuery(${entityName}PageRequest request) {
        return this.baseMapper.pageQuery(PageUtil.buildPage(request), request);
    }
</#if>

<#assign hasGet = methodList?filter(item -> item?string == "GET")>
<#if (hasGet?size > 0)>
    @Override
    public ${infoName} getById(Long id) {
        return this.baseMapper.getById(id);
    }
</#if>

<#assign hasDelete = methodList?filter(item -> item?string == "DELETE")>
<#if (hasDelete?size > 0)>
    @Override
    public Boolean deleteById(Long id) {
        return this.removeById(id);
    }
</#if>

}