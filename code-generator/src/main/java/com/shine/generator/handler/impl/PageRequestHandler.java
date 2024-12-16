package com.shine.generator.handler.impl;

import com.shine.generator.entity.Table;
import com.shine.generator.enums.GeneratorEnum;
import com.shine.generator.handler.AbstractGeneratorHandler;
import com.shine.generator.handler.GeneratorHandler;

/**
 * @author huihui
 * @date 2024/12/16 11:22
 * @description PageRequestHandler
 */
public class PageRequestHandler extends AbstractGeneratorHandler implements GeneratorHandler {

    private final GeneratorEnum generatorEnum = GeneratorEnum.PAGE_REQUEST;

    @Override
    public GeneratorEnum getEnum() {
        return generatorEnum;
    }

    @Override
    public void handler(Table table) throws Exception {
    }

}
