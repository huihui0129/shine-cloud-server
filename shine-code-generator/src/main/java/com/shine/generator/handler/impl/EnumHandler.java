package com.shine.generator.handler.impl;

import com.shine.generator.GeneratorApplication;
import com.shine.generator.entity.Column;
import com.shine.generator.entity.Table;
import com.shine.generator.enums.GeneratorEnum;
import com.shine.generator.handler.AbstractGeneratorHandler;
import com.shine.generator.handler.GeneratorHandler;
import com.shine.generator.model.EnumModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
@Slf4j
@Component
public class EnumHandler extends AbstractGeneratorHandler implements GeneratorHandler {

    private final GeneratorEnum generatorEnum = GeneratorEnum.ENUM;

    @Override
    public GeneratorEnum getEnum() {
        return this.generatorEnum;
    }

    @Override
    public void handleTable(Table table) {
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
            if (StringUtils.equals(item.getColumnName(), "remark")) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        table.setColumnList(columnList);
    }

    @Override
    public void handler(Table table) throws Exception {
        try {
            log.info("开始生成{}枚举", table.getEntityName());
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setClassForTemplateLoading(GeneratorApplication.class, "/templates");
            cfg.setDefaultEncoding("UTF-8");
            // 2. 加载模板
            Template template = cfg.getTemplate("enum.ftl");
            // 4. 生成文件
            List<EnumModel> modelList = table.getColumnList().stream()
                    .filter(item -> item.getComment().contains("枚举 "))
                    .map(item -> {
                        EnumModel model = new EnumModel();
                        String initial = item.getFieldName().substring(0, 1);
                        model.setClassName(table.getClassName() + initial.toUpperCase() + item.getFieldName().substring(1) + "Enum");
                        String enumComment = item.getComment();
                        String[] split = enumComment.split("：");
                        String enumStr = split[1];
                        String[] enums = enumStr.split("\\|");
                        List<EnumModel.Item> itemList = new ArrayList<>(enums.length);
                        for (String anEnum : enums) {
                            EnumModel.Item single = new EnumModel.Item();
                            String[] content = anEnum.split("-");
                            single.setValue(initial.toUpperCase() + content[0]);
                            single.setCode(content[0]);
                            single.setName(content[1]);
                            single.setComment(content[1]);
                            itemList.add(single);
                        }
                        model.setContentList(itemList);
                        model.setAuthor(table.getAuthor());
                        model.setPackagePath(table.getPackagePath());
                        model.setModuleName(table.getModuleName());
                        model.setGeneratorDate(table.getGeneratorDate());
                        return model;
                    }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(modelList)) {
                for (EnumModel item : modelList) {
                    File output = new File(super.getProperties().getPackageConfig().getModalName() + "/enums/" + item.getClassName() + ".java");
                    File parentDir = output.getParentFile();
                    if (!parentDir.exists()) {
                        if (parentDir.mkdirs()) {
                            log.info("成功创建目录：{}", parentDir.getAbsolutePath());
                        } else {
                            throw new IOException("无法创建目录：" + parentDir.getAbsolutePath());
                        }
                    }
                    try (FileWriter writer = new FileWriter(output)) {
                        template.process(item, writer);
                    }
                }
                log.info("生成{}枚举成功", table.getEntityName());
            } else {
                log.info("{}没有枚举", table.getEntityName());
            }
        } catch (Exception e) {
            log.error("生成枚举失败，可能是解析错误，原因：", e);
        }
    }

}
