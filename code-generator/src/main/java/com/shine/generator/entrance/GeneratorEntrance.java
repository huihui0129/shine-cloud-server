package com.shine.generator.entrance;

import com.shine.generator.conver.TypeConvert;
import com.shine.generator.entity.Column;
import com.shine.generator.entity.Table;
import com.shine.generator.enums.GeneratorEnum;
import com.shine.generator.handler.GeneratorHandler;
import com.shine.generator.mapper.DatabaseMapper;
import com.shine.generator.properties.GeneratorProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /**
     * 准备
     */
    public void ready() {
        List<GeneratorEnum> enumList = properties.getGenerator().getCode();
        handlerList = handlerList.stream().filter(item -> enumList.contains(item.getEnum())).collect(Collectors.toList());
        log.info("查询表对象");
        List<String> tableNameList = properties.getGenerator().getTableName();
        for (String tableName : tableNameList) {
            // 查询数据库
            Table table = databaseMapper.getTableInfo(properties.getGenerator().getDatabase(), tableName);
            // 包和模块信息
            table.setPackagePath(properties.getPackageConfig().getBasePackage());
            table.setModuleName(properties.getPackageConfig().getModalName());
            this.tableNameConvert(table);
            // 转换表
            List<Column> columnList = databaseMapper.getColumnInfo(properties.getGenerator().getDatabase(), tableName);
            this.columnNameConvert(columnList);
            this.typeConvert(columnList);
            table.setColumnList(columnList);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            String date = formatter.format(now);
            table.setGeneratorDate(date);
            tableList.add(table);
        }
        log.info("表对象封装完成");
    }

    /**
     * 处理
     *
     * @throws Exception
     */
    public void handler() throws Exception {
        for (GeneratorHandler handler : this.handlerList) {
            for (Table table : tableList) {
                Table newTable = new Table();
                BeanUtils.copyProperties(table, newTable);
                handler.handleTable(newTable);
                handler.handler(newTable);
            }
        }
    }

    /**
     * 列类型转换
     *
     * @param columnList
     */
    protected void typeConvert(List<Column> columnList) {
        this.typeConvert.convert(columnList);
    }

    /**
     * 表名处理
     *
     * @param table
     */
    protected void tableNameConvert(Table table) {
        String tableName = table.getTableName();
        String tablePrefix = properties.getGenerator().getTablePrefix();
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.substring(tablePrefix.length());
        }
        String className = tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
        table.setLowercaseClassName(tableName.substring(0, 1) + tableName.substring(1));

        table.setEntityName(this.toCamelCase(className));
        table.setClassName(this.toCamelCase(className));
    }

    /**
     * 列名处理
     *
     * @param columnList
     */
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

    public String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder();
        boolean toUpperCase = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                toUpperCase = true; // 遇到下划线，标记下一个字符需要大写
            } else {
                if (toUpperCase) {
                    result.append(Character.toUpperCase(c)); // 转为大写
                    toUpperCase = false;
                } else {
                    result.append(c); // 保留原样
                }
            }
        }

        return result.toString();
    }

}
