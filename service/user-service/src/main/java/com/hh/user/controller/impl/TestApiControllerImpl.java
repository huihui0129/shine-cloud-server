package com.hh.user.controller.impl;

import com.hh.common.response.Result;
import com.hh.user.controller.TestApiController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * @author huihui
 * @date 2024/11/10 01:15
 * @description TestApiControllerImpl
 */
@Slf4j
@Controller
public class TestApiControllerImpl implements TestApiController {

    @Override
    public Result<String> getApiTest() {
        return Result.success("牛666");
    }
}
