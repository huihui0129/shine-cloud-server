package com.hh.user.controller;

import com.hh.user.model.TestModel;
import com.hh.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/user")
public class UserController {

    @Operation(summary = "测试获取一个虚假的Model")
    @GetMapping("/getTestModel")
    public Result<TestModel> getTestModel() {
        TestModel testModel = new TestModel();
        testModel.setId(10086L);
        testModel.setName("测试一个Swagger返回对象");
        return Result.success(testModel);
    }

}
