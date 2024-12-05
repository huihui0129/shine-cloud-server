package ${packagePath}.${moduleName}.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packagePath}.${moduleName}.entity.${entityName};
import ${packagePath}.${moduleName}.mapper.${entityName}Mapper;
import ${packagePath}.${moduleName}.service.${entityName}Service;
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

}