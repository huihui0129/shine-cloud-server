package com.shine.generator.handler;

import com.shine.generator.entity.Table;
import com.shine.generator.enums.GeneratorEnum;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/24 15:20
 * @description Handle
 */
public interface GeneratorHandler {

    GeneratorEnum getEnum();

    List<String> getIgnoreColumnList();

    void handleTable(Table table);

    void handler(Table table) throws Exception;

}
