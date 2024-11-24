package com.hh.generator.entrance;

import com.hh.generator.handler.GeneratorHandler;
import com.hh.generator.handler.impl.EntityHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huihui
 * @date 2024/11/24 15:53
 * @description GeneratorEntrance
 */
@Component
public class GeneratorEntrance {

    private final List<GeneratorHandler> handlerList = new ArrayList<>();

    @Resource
    private GeneratorHandler entityHandler;

    @PostConstruct
    public void generator() throws Exception {
        ready();
        handler();
    }

    public void ready() {
        handlerList.add(entityHandler);
    }

    public void handler() throws Exception {
        for (GeneratorHandler handler : this.handlerList) {
            handler.handler();
        }
    }

}
