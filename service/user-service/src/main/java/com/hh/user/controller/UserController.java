package com.hh.user.controller;

import com.hh.common.response.Result;
import com.hh.user.info.UserInfo;
import com.hh.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huihui
 * @date 2024/11/9 22:00
 * @description UserController
 */
@Slf4j
@Tag(name = "用户 Controller", description = "用户 Controller")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserById/{id}")
    @Operation(summary = "根据ID获取用户")
    public Result<UserInfo> getUserById(@PathVariable("id") Long id) {
        UserInfo user = userService.getUserById(id);
        return Result.success(user);
    }

}
