package ${packagePath}.${moduleName}.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<#if (methodList?size > 0)>
import org.springframework.web.bind.annotation.GetMapping;
</#if>
<#assign hasDelete = methodList?filter(item -> item?string == "DELETE")>
<#if (hasDelete?size > 0)>
import org.springframework.web.bind.annotation.PathVariable;
</#if>
<#assign hasPage = methodList?filter(item -> item?string == "PAGE")>
<#if (hasPage?size > 0)>
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${packagePath}.${moduleName}.request.${entityName}PageRequest;
import ${packagePath}.${moduleName}.info.${infoName};
</#if>

import jakarta.annotation.Resource;

/**
* @author ${author}
* @date ${generatorDate}
* @description ${className}
*/
@Slf4j
@Tag(name = "${comment} Controller", description = "${comment} Controller")
@RestController
@RequestMapping("/${lowercaseClassName}")
public class ${className} {

    @Resource
    private ${entityName}Service ${lowercaseClassName}Service;

    @GetMapping("/page")
    @Operation(summary = "${comment}分页查询")
    private Result<IPage<${infoName}>> page${entityName}(${entityName}PageRequest request) {
        IPage<${infoName}> ${lowercaseClassName}Page = ${lowercaseClassName}Service.page${entityName}(request);
        return Result.success(${lowercaseClassName}Page);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "${comment}详情查询")
    private Result<${infoName}> get${entityName}ById(@PathVariable("id") Long id) {
        ${entityName}Info ${lowercaseClassName} = ${lowercaseClassName}Service.getUserById(id);
        return Result.success(${lowercaseClassName});
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "根据ID删除${comment}")
    private Result<Boolean> delete${entityName}ById(@PathVariable("id") Long id) {
        Boolean flag = ${lowercaseClassName}Service.deleteById(id);
        return Result.success(flag);
    }

}