package com.shine.security.controller;

import com.shine.common.response.Result;
import com.shine.security.annotation.PreAuthorize;
import com.shine.security.manager.UserStatusManager;
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
 * @date 2025/1/9 16:05
 * @description UserStatusController
 */
@Slf4j
@Tag(name = "用户状态管理 Controller", description = "用户状态管理 Controller")
@RestController
@RequestMapping("/userStatue")
public class UserStatusController {

    @Resource
    private UserStatusManager userStatusManager;

    @Operation(summary = "踢人下线")
    @GetMapping("/offline/{service}/{userId}")
    @PreAuthorize("hasUsername('admin')")
    public Result<Boolean> offlineUser(@PathVariable("service") String service, @PathVariable("userId") Long userId) {
        return Result.success(userStatusManager.offline(service, userId));
    }

    @Operation(summary = "在线续约")
    @GetMapping("/line/renew/{service}/{userId}")
    public Result<Boolean> userLineRenew(@PathVariable("service") String service, @PathVariable("userId") Long userId) {
        return Result.success(userStatusManager.renew(service, userId));
    }

    @Operation(summary = "在线续约")
    @GetMapping("/line/renew/{service}")
    public Result<Long> getOnlineUserCount(@PathVariable("service") String service) {
        return Result.success(userStatusManager.getOnlineUserCount(service));
    }

}
