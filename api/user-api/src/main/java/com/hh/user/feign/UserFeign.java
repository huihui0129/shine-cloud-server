package com.hh.user.feign;

import com.hh.feign.constant.FeignClientConstant;
import com.hh.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author huihui
 * @date 2024/10/31 17:11
 * @description UserFeign
 */
@FeignClient(name = FeignClientConstant.USER_NAME, contextId = FeignClientConstant.USER_CONTENT_ID, path = "/user")
public interface UserFeign {

    @GetMapping("/getUser")
    Result<String> getUser();

    @GetMapping("/getException")
    Result<String> getException();

    @GetMapping("/testConnTime")
    Result<String> getConnTime();

}
