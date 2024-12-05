package com.shine.security.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author huihui
* @date 2024/12/05 15:28
* @description AccessTokenController
*/
@Slf4j
@Tag(name = "访问令牌记录 Controller", description = "访问令牌记录 Controller")
@RestController
@RequestMapping("/access_token")
public class AccessTokenController {

}