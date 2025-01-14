package com.shine.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shine.common.response.Result;
import com.shine.system.info.UserInfo;
import com.shine.system.request.UserPageRequest;
import com.shine.system.response.UserPermissionResponse;
import com.shine.system.service.UserService;
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

    @GetMapping("/page")
    @Operation(summary = "用户分页查询")
    public Result<IPage<UserInfo>> pageUser(UserPageRequest request) {
        IPage<UserInfo> userPage = userService.pageUser(request);
        return Result.success(userPage);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "用户详情查询")
    public Result<UserInfo> getUserById(@PathVariable("id") Long id) {
        UserInfo user = userService.getUserById(id);
        return Result.success(user);
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "根据ID删除用户")
    public Result<Boolean> deleteUserById(@PathVariable("id") Long id) {
        Boolean flag = userService.deleteById(id);
        return Result.success(flag);
    }

    @GetMapping("/get/perm/{appId}/{userId}")
    @Operation(summary = "查询用户以及权限详细信息")
    public Result<UserPermissionResponse> getUserPermById(@PathVariable("appId") Long appId, @PathVariable("userId") Long userId) {
        return Result.success(userService.getPerm(appId, userId));
    }

}
