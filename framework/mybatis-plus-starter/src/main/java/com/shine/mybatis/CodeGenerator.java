package com.shine.mybatis;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.shine.mybatis.eneity.BaseEntity;

/**
 * @author huihui
 * @date 2024/11/10 01:35
 * @description CodeGenerator
 */
public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/hh_user?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true", "root", "123456")
                .globalConfig(builder -> {
                    builder.disableOpenDir()
//                            .enableSwagger()
//                            .enableSpringdoc()
                            .author("huihui") // 设置作者
                            .outputDir("src/main/java"); // 输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.shine.user") // 设置父包名
                            .entity("entity") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .xml("mappers"); // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude(
                                "menu",
                                "role",
                                "role_menu",
                                "user_role"
                            ) // 设置需要生成的表名
                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .superClass(BaseEntity.class)
                            .enableTableFieldAnnotation() // 启用字段注解
                            .enableColumnConstant()
                            .controllerBuilder()
                            .enableRestStyle(); // 启用 REST 风格
                })
//                .injectionConfig(consumer -> {
//                    Map<String, String> customFile = new HashMap<>();
//                    // DTO
//                    customFile.put("Info.java", "/templates/entityInfo.java.ftl");
//                    consumer.customFile(customFile);
//                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .templateConfig(builder -> {
                    builder
//                     .disable(TemplateType.ENTITY)
                            .entity("/templates/entity.java")
                            .service("/templates/service.java")
                            .serviceImpl("/templates/serviceImpl.java")
                            .mapper("/templates/mapper.java")
                            .xml("/templates/mapper.xml")
                            .controller("/templates/controller.java");
                })
                .execute(); // 执行生成
    }

}
