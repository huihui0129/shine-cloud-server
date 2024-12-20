package com.shine.generator.handler.impl;

import com.shine.generator.GeneratorApplication;
import com.shine.generator.entity.Column;
import com.shine.generator.entity.Table;
import com.shine.generator.enums.GeneratorEnum;
import com.shine.generator.handler.AbstractGeneratorHandler;
import com.shine.generator.handler.GeneratorHandler;
import com.shine.generator.model.MethodModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Column> columnList = table.getColumnList();
        columnList = columnList.stream().filter(item -> {
            if (StringUtils.equals(item.getColumnName(), "id")) {
                return false;
            }
            if (StringUtils.equals(item.getColumnName(), "create_time")) {
                return false;
            }
            if (StringUtils.equals(item.getColumnName(), "update_time")) {
                return false;
            }
            if (StringUtils.equals(item.getColumnName(), "create_user")) {
                return false;
            }
            if (StringUtils.equals(item.getColumnName(), "update_user")) {
                return false;
            }
            if (StringUtils.equals(item.getColumnName(), "deleted")) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        table.setColumnList(columnList);
    }

    @Override
    public void handler(Table table) throws Exception {
        log.info("开始生成{}MapperXml", table.getEntityName());
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(GeneratorApplication.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        // 2. 加载模板
        Template template = cfg.getTemplate("mapperXml.ftl");
        // 4. 生成文件
        File output = new File("code-generator/target/generator/package/mapperXml/" + table.getClassName() + ".xml");
        File parentDir = output.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                log.info("成功创建目录：{}", parentDir.getAbsolutePath());
            } else {
                throw new IOException("无法创建目录：" + parentDir.getAbsolutePath());
            }
        }
        try (FileWriter writer = new FileWriter(output)) {
            // 方法
            MethodModel model = new MethodModel();
            BeanUtils.copyProperties(table, model);
            model.setInfoName(table.getEntityName() + "Info");
            model.setMethodList(getProperties().getGenerator().getMethods());
            model.setDatasourceName(getProperties().getGenerator().getDatabase());
            template.process(model, writer);
        }
        log.info("生成{}MapperXml成功", table.getEntityName());
    }

}
