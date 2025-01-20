package com.shine.system.feign;

import com.shine.feign.constant.FeignClientConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huihui
 * @date 2025/1/17 16:04
 * @description TestFeign
 */
@FeignClient(name = FeignClientConstant.SYSTEM_NAME, contextId = "test", path = "/user")
public interface TestFeign {

    @GetMapping("/getUser")
    String getUser(@RequestParam("userId") Long userId);

}
