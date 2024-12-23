package com.shine.generator.entity;

import lombok.Data;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/24 15:26
 * @description Table
 */
@Data
public class Table {

    private String tableName;

    private String className;

    private String lowercaseClassName;

    private String entityName;

    private String comment;

    private List<Column> columnList;

    private String generatorDate;

    private String author = "huihui";

    private String packagePath;

    private String moduleName;

}
