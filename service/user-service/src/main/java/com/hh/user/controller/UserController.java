package com.hh.user.controller;

import com.hh.common.response.Result;
import com.hh.user.entity.User;
import com.hh.user.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
    private UserMapper userMapper;

    @GetMapping("/getUserById/{id}")
    @Operation(summary = "根据ID获取用户")
    public Result<User> getUserById(@PathVariable("id") Long id) {
        User user = userMapper.selectById(id);
        return Result.success(user);
    }

}
