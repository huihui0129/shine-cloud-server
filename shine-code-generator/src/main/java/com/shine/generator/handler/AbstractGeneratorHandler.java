package com.shine.generator.handler;

import com.shine.generator.entity.Table;
import com.shine.generator.properties.GeneratorProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author huihui
 * @date 2024/11/24 15:33
 * @description AGeneratorHandler
 */
@Data
@Slf4j
@Component
public abstract class AbstractGeneratorHandler implements GeneratorHandler {

    @Autowired
    private GeneratorProperties properties;

    @Override
    public List<String> getIgnoreColumnList() {
        return Collections.emptyList();
    }

    @Override
    public void handleTable(Table table) {
    }

}
