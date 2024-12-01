package ${packagePath}.${moduleName}.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packagePath}.${moduleName}.entity.${entityName};
import ${packagePath}.${moduleName}.mapper.${entityName}Mapper;
import ${packagePath}.${moduleName}.mapper.${entityName}Service;

/**
* @author ${author}
* @date ${generatorDate}
* @description ${className}
*/
@Slf4j
@Service
public interface ${className} extends ServiceImpl<${entityName}Mapper, ${entityName}> implements ${entityName}Service {

}