package com.hh.user.controller.impl;

import com.hh.common.response.Result;
import com.hh.user.controller.TestApiController;
import com.hh.user.manager.AsyncManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

/**
 * @author huihui
 * @date 2024/11/10 01:15
 * @description TestApiControllerImpl
 */
@Slf4j
@Controller
public class TestApiControllerImpl implements TestApiController {

    @Resource
    private AsyncManager asyncManager;

    @Override
    public Result<String> getApiTest() {
        return Result.success("牛666");
    }

    @Override
    public Result<?> testAsyncTask() {
        log.info("我是外面的");
        asyncManager.asyncTask();
        log.info("我是外面后面的");
        return Result.success();
    }

}
