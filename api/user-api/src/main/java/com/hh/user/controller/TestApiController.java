package com.hh.user.controller;

import com.hh.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huihui
 * @date 2024/11/10 01:14
 * @description TestApiController
 */
@Tag(name = "测试API Controller", description = "测试API Controller")
@RequestMapping("/testApi")
public interface TestApiController {

    @Operation(summary = "测试将Controller放在API模块下的访问情况")
    @GetMapping("/testGet")
    Result<String> getApiTest();

}
