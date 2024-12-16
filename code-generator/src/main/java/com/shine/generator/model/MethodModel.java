package com.shine.generator.model;

import com.shine.generator.entity.Table;
import com.shine.generator.enums.MethodEnum;
import lombok.Data;

import java.util.List;

/**
 * @author huihui
 * @date 2024/12/16 10:45
 * @description Model
 */
@Data
public class MethodModel extends Table {

    private String infoName;

    private List<MethodEnum> methodList;

    private String datasourceName;

}
