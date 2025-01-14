package com.shine.system.controller;

import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import com.shine.security.annotation.PreAuthorize;
import com.shine.system.model.TestModel;
import com.shine.common.response.Result;
import com.shine.system.properties.UserProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huihui
 * @date 2024/11/4 09:41
 * @description UserController
 */
@Slf4j
@Tag(name = "测试集成Swagger3", description = "测试用例")
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private UserProperties userProperties;

    @Operation(summary = "测试获取一个虚假的Model")
    @GetMapping("/getTestModel")
    public Result<TestModel> getTestModel() {
        TestModel testModel = new TestModel();
        testModel.setId(10086L);
        testModel.setName("测试一个Swagger返回对象");
        return Result.success(testModel);
    }

    @Operation(summary = "测试异常返回")
    @GetMapping("/testException")
    public Result<?> testException() {
        throw new BaseException(ResponseStatus.ERROR, "我是一个超级牛逼的错误");
    }

}
