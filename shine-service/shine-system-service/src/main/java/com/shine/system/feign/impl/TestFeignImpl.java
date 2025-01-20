package com.shine.system.feign.impl;

import com.shine.system.feign.TestFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huihui
 * @date 2025/1/17 16:08
 * @description TestFeignImpl
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class TestFeignImpl implements TestFeign {

    @Override
    public String getUser(Long userId) {
        return "获取到用户：" + userId + "啦~";
    }

}
