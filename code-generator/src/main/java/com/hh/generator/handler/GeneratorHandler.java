package com.hh.generator.handler;

import com.hh.generator.entity.Table;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author huihui
 * @date 2024/11/24 15:20
 * @description Handle
 */
public interface GeneratorHandler {

    Table prepare();

    void handler() throws Exception;

}
