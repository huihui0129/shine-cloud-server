package com.shine.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shine.common.response.Result;
import com.shine.user.info.UserInfo;
import com.shine.user.request.UserPageRequest;
import com.shine.user.response.UserPermissionResponse;
import com.shine.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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

    @Resource
    private UserService userService;

    @GetMapping("/page")
    @Operation(summary = "用户分页查询")
    private Result<IPage<UserInfo>> pageUser(UserPageRequest request) {
        IPage<UserInfo> userPage = userService.pageUser(request);
        return Result.success(userPage);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "用户详情查询")
    private Result<UserInfo> getUserById(@PathVariable("id") Long id) {
        UserInfo user = userService.getUserById(id);
        return Result.success(user);
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "根据ID删除用户")
    private Result<Boolean> deleteUserById(@PathVariable("id") Long id) {
        Boolean flag = userService.deleteById(id);
        return Result.success(flag);
    }

    @GetMapping("/get/perm/{id}")
    @Operation(summary = "查询用户以及权限详细信息")
    private Result<UserPermissionResponse> getUserPermById(@PathVariable("id") Long id) {
        return Result.success(userService.getPerm(id));
    }

}
