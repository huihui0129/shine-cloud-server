package com.shine.generator.model;

import lombok.Data;

import java.util.List;

/**
 * @author huihui
 * @date 2024/12/25 14:26
 * @description EnumModel
 */
@Data
public class EnumModel {

    private String author = "huihui";

    private String packagePath;

    private String moduleName;

    private String className;

    private String generatorDate;

    private List<Item> contentList;

    @Data
    public static class Item {

        private String value;

        private String code;

        private String name;

        private String comment;

    }

}
