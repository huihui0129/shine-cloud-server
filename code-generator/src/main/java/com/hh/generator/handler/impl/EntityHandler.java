package com.hh.generator.handler.impl;

import com.hh.generator.GeneratorApplication;
import com.hh.generator.entity.Table;
import com.hh.generator.handler.AbstractGeneratorHandler;
import com.hh.generator.handler.GeneratorHandler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author huihui
 * @date 2024/11/24 15:21
 * @description EntityHandler
 */
@Component
public class EntityHandler extends AbstractGeneratorHandler implements GeneratorHandler {

    @Override
    public void handler() throws Exception {
        Table table = this.prepare();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(GeneratorApplication.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        // 2. 加载模板
        Template template = cfg.getTemplate("entity.ftl");
        // 4. 生成文件
        File output = new File("code-generator/target/generator/package/User.java");
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
        System.out.println("生成实体成功");
    }

}
