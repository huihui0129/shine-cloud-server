package com.hh.generator.entity;

import lombok.Data;

/**
 * @author huihui
 * @date 2024/11/24 15:19
 * @description Column
 */
@Data
public class Column {

    private String columnName;

    private String fieldName;

    private String dataType;

    private String javaType;

    private String comment;

}
