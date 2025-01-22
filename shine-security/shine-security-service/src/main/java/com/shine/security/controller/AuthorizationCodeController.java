package com.shine.security.controller;

import com.shine.common.response.Result;
import com.shine.security.service.AuthorizationCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shine.security.request.AuthorizationCodePageRequest;
import com.shine.security.info.AuthorizationCodeInfo;

import jakarta.annotation.Resource;

/**
* @author huihui
* @date 2025/01/22 17:23
* @description AuthorizationCodeController
*/
@Slf4j
@Tag(name = "授权码 Controller", description = "授权码 Controller")
@RestController
@RequestMapping("/authorizationCode")
public class AuthorizationCodeController {

    @Resource
    private AuthorizationCodeService authorizationCodeService;

    @GetMapping("/page")
    @Operation(summary = "授权码分页查询")
    public Result<IPage<AuthorizationCodeInfo>> pageQueryAuthorizationCode(AuthorizationCodePageRequest request) {
        IPage<AuthorizationCodeInfo> authorizationCodePage = authorizationCodeService.pageQuery(request);
        return Result.success(authorizationCodePage);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "授权码详情查询")
    public Result<AuthorizationCodeInfo> getAuthorizationCodeById(@PathVariable("id") Long id) {
        AuthorizationCodeInfo authorizationCode = authorizationCodeService.getById(id);
        return Result.success(authorizationCode);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "根据ID删除授权码")
    public Result<Boolean> deleteAuthorizationCodeById(@PathVariable("id") Long id) {
        Boolean flag = authorizationCodeService.deleteById(id);
        return Result.success(flag);
    }

}