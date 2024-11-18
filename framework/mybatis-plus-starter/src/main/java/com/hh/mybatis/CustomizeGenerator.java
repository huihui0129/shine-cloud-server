package com.hh.mybatis;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huihui
 * @date 2024/11/18 15:30
 * @description CustomizeGenerator
 */
public class CustomizeGenerator {

    public static void main(String[] args) throws Exception {
        // 初始化 FreeMarker 配置
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        // 模板文件目录
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        cfg.setDefaultEncoding("UTF-8");

        // 获取模板文件
        Template template = cfg.getTemplate("entityInfo.java.ftl");

        // 生成代码
        generateCode(template, "User", "com.hh.user.dto.info");
    }

    private static void generateCode(Template template, String entityName, String packageName) throws Exception {
        // 数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("entityName", entityName);
        dataModel.put("packageName", packageName);
        // 输出目录
        String outputPath = "src/main/java/" + packageName.replace(".", "/") + "/";
        new File(outputPath).mkdirs();
        // 输出文件
        File outputFile = new File(outputPath + entityName + "Info.java");
        try (FileWriter writer = new FileWriter(outputFile)) {
            template.process(dataModel, writer);
            System.out.println("Generated file: " + outputFile.getAbsolutePath());
        }
    }

}
