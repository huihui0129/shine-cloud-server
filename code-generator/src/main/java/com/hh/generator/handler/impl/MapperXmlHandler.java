package com.hh.generator.handler.impl;

import com.hh.generator.GeneratorApplication;
import com.hh.generator.entity.Table;
import com.hh.generator.enums.GeneratorEnum;
import com.hh.generator.handler.AbstractGeneratorHandler;
import com.hh.generator.handler.GeneratorHandler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author huihui
 * @date 2024/12/1 13:44
 * @description MapperHandler
 */
@Slf4j
@Component
public class MapperXmlHandler extends AbstractGeneratorHandler implements GeneratorHandler {

    private final GeneratorEnum generatorEnum = GeneratorEnum.MAPPER;

    @Override
    public GeneratorEnum getEnum() {
        return generatorEnum;
    }

    @Override
    public void handleTable(Table table) {
        table.setClassName(table.getClassName() + "Mapper");
    }

    @Override
    public void handler(Table table) throws Exception {
        log.info("开始生成{}MapperXml", table.getEntityName());
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(GeneratorApplication.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        // 2. 加载模板
        Template template = cfg.getTemplate("mapper.ftl");
        // 4. 生成文件
        File output = new File("code-generator/target/generator/package/mapperXml/" + table.getClassName() + ".xml");
        File parentDir = output.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("成功创建目录：" + parentDir.getAbsolutePath());
            } else {
                throw new IOException("无法创建目录：" + parentDir.getAbsolutePath());
            }
        }
        try (FileWriter writer = new FileWriter(output)) {
            template.process(table, writer);
        }
        log.info("生成{}MapperXml成功", table.getEntityName());
    }

}
