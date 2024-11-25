package com.hh.generator.entrance;

import com.hh.generator.conver.TypeConvert;
import com.hh.generator.entity.Column;
import com.hh.generator.entity.Table;
import com.hh.generator.enums.GeneratorEnum;
import com.hh.generator.handler.GeneratorHandler;
import com.hh.generator.handler.impl.EntityHandler;
import com.hh.generator.handler.impl.InfoHandler;
import com.hh.generator.mapper.DatabaseMapper;
import com.hh.generator.properties.GeneratorProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huihui
 * @date 2024/11/24 15:53
 * @description GeneratorEntrance
 */
@Slf4j
@Component
public class GeneratorEntrance {

//    private final List<GeneratorHandler> handlerList = new ArrayList<>();

    @Autowired
    private GeneratorProperties properties;

    @Autowired
    private List<GeneratorHandler> handlerList;

    @Autowired
    private DatabaseMapper databaseMapper;

    @Autowired
    private TypeConvert typeConvert;

    private List<Table> tableList = new ArrayList<>();

    @PostConstruct
    public void generator() throws Exception {
        ready();
        handler();
    }

    public void ready() {
        List<GeneratorEnum> enumList = properties.getGenerator().getCode();
        handlerList = handlerList.stream().filter(item -> enumList.contains(item.getEnum())).collect(Collectors.toList());
        log.info("查询表对象");
        List<String> tableNameList = properties.getGenerator().getTableName();
        for (String tableName : tableNameList) {
            // 查询数据库
            Table table = databaseMapper.getTableInfo(properties.getGenerator().getDatabase(), tableName);
            this.tableNameConvert(table);
            // 转换表
            List<Column> columnList = databaseMapper.getColumnInfo(properties.getGenerator().getDatabase(), tableName);
            this.columnNameConvert(columnList);
            this.typeConvert(columnList);
            table.setColumnList(columnList);
            tableList.add(table);
        }
        log.info("表对象封装完成");
    }

    public void handler() throws Exception {
        for (GeneratorHandler handler : this.handlerList) {
            for (Table table : tableList) {
                handler.handleTable(table);
                handler.handler(table);
            }
        }
    }

    protected void typeConvert(List<Column> columnList) {
        this.typeConvert.convert(columnList);
    }

    protected void tableNameConvert(Table table) {
        String tableName = table.getTableName();
        table.setClassName(tableName.substring(0, 1).toUpperCase() + tableName.substring(1));
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
