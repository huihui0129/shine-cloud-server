package com.hh.generator.handler;

import com.hh.generator.conver.TypeConvert;
import com.hh.generator.entity.Column;
import com.hh.generator.entity.Table;
import com.hh.generator.mapper.DatabaseMapper;
import com.hh.generator.properties.GeneratorProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/24 15:33
 * @description AGeneratorHandler
 */
@Data
@Component
public abstract class AbstractGeneratorHandler implements GeneratorHandler {

    @Autowired
    private GeneratorProperties properties;

    @Autowired
    private DatabaseMapper databaseMapper;

    @Autowired
    private TypeConvert typeConvert;

    protected void typeConvert(List<Column> columnList) {
        this.typeConvert.convert(columnList);
    }

    protected void tableNameConvert(Table table) {
        String className = this.nameConvert(table.getTableName());
        table.setClassName(className);
    }

    protected void columnNameConvert(List<Column> columnList) {
        columnList.forEach(item -> {
            String fieldName = this.nameConvert(item.getColumnName());
            item.setFieldName(fieldName);
        });
    }

    private String nameConvert(String name) {
        if (name == null || name.isEmpty()) {
            throw new NullPointerException();
        }

        // 使用下划线分割字符串
        String[] parts = name.split("_");
        StringBuilder camelCaseString = new StringBuilder();
        // 遍历每一部分
        for (int i = 0; i < parts.length; i++) {
            if (i == 0) {
                // 第一个单词全部小写
                camelCaseString.append(parts[i].toLowerCase());
            } else {
                // 其它单词首字母大写
                camelCaseString.append(parts[i].substring(0, 1).toUpperCase());
                camelCaseString.append(parts[i].substring(1).toLowerCase());
            }
        }
        return camelCaseString.toString();
    }

}
