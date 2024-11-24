package com.hh.generator.handler.impl;

import com.hh.generator.GeneratorApplication;
import com.hh.generator.entity.Column;
import com.hh.generator.entity.Table;
import com.hh.generator.handler.AbstractGeneratorHandler;
import com.hh.generator.handler.GeneratorHandler;
import com.hh.generator.mapper.DatabaseMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huihui
 * @date 2024/11/24 15:21
 * @description EntityHandler
 */
@Component
public class EntityHandler extends AbstractGeneratorHandler implements GeneratorHandler {

    @Override
    public Table prepare() {
        // 查询数据库
        DatabaseMapper databaseMapper = this.getDatabaseMapper();
        Table table = databaseMapper.getTableInfo("hh_user", "user");
        this.tableNameConvert(table);
        // 转换表
        List<Column> columnList = databaseMapper.getColumnInfo("hh_user", "user");
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
            if (StringUtils.equals(item.getColumnName(), "remark")) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        super.columnNameConvert(columnList);
        super.typeConvert(columnList);
        table.setColumnList(columnList);
        return table;
    }

    @Override
    public void handler() throws Exception {
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
        Table table = this.prepare();
        try (FileWriter writer = new FileWriter(output)) {
            template.process(table, writer);
        }
        System.out.println("生成实体成功");
    }

}
