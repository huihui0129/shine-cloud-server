package com.shine.user.controller;

import com.shine.common.response.Result;
import com.shine.user.info.MenuInfo;
import com.shine.user.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
@Slf4j
@Tag(name = "菜单 前端控制器", description = "菜单 前端控制器")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/listByClient/{clientId}")
    @Operation(summary = "获取用户菜单")
    public Result<List<MenuInfo>> listMenuByClient(@PathVariable("clientId") String clientId) {
        return Result.success(menuService.listByClient(clientId));
    }

}
