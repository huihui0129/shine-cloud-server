package ${packagePath}.${moduleName}.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author ${author}
* @date ${generatorDate}
* @description ${className}
*/
@Slf4j
@Tag(name = "${comment} Controller", description = "${comment} Controller")
@RestController
@RequestMapping("/${lowercaseClassName}")
public interface ${className} {

}