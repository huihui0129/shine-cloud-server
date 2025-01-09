package com.shine.security.controller;

import com.shine.security.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huihui
 * @date 2025/01/06 13:41
 * @description RefreshTokenController
 */
@Slf4j
@Tag(name = "刷新令牌 Controller", description = "刷新令牌 Controller")
@RestController
@RequestMapping("/refreshToken")
public class RefreshTokenController {

    @Resource
    private RefreshTokenService refreshTokenService;


}