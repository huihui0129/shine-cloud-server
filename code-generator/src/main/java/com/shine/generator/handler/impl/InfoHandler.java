package com.shine.generator.handler.impl;

import com.shine.generator.GeneratorApplication;
import com.shine.generator.entity.Table;
import com.shine.generator.enums.GeneratorEnum;
import com.shine.generator.handler.AbstractGeneratorHandler;
import com.shine.generator.handler.GeneratorHandler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author huihui
 * @date 2024/11/25 16:32
 * @description InfoHandler
 */
@Slf4j
@Component
public class InfoHandler extends AbstractGeneratorHandler implements GeneratorHandler {

    private final GeneratorEnum generatorEnum = GeneratorEnum.INFO;

    @Override
    public GeneratorEnum getEnum() {
        return this.generatorEnum;
    }

    @Override
    public void handleTable(Table table) {
        table.setClassName(table.getClassName() + "Info");
    }

    @Override
    public void handler(Table table) throws Exception {
        log.info("开始生成{}Info", table.getEntityName());
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(GeneratorApplication.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        // 2. 加载模板
        Template template = cfg.getTemplate("info.ftl");
        // 4. 生成文件
        File output = new File("code-generator/target/generator/package/info/" + table.getClassName() + ".java");
        File parentDir = output.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                log.info("成功创建目录：{}", parentDir.getAbsolutePath());
            } else {
                throw new IOException("无法创建目录：" + parentDir.getAbsolutePath());
            }
        }
        try (FileWriter writer = new FileWriter(output)) {
            template.process(table, writer);
        }
        log.info("生成{}Info成功", table.getEntityName());
    }

}
