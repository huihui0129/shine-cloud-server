package ${packagePath}.${moduleName}.enums;

import com.shine.common.enums.IEnum;

/**
 * @author ${author}
 * @date ${generatorDate}
 * @description ${className}
 */
public enum ${className} implements IEnum<Integer> {

    <#list contentList as content>
    /**
     * ${content.comment}
     */
    ${content.value}(${content.code}, "${content.name}"),

    </#list>
    ;

    private Integer code;

    private String name;

    ${className}(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

}