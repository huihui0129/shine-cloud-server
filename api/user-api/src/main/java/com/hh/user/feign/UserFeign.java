package com.hh.user.feign;

import com.hh.common.response.Result;
import com.hh.feign.constant.FeignClientConstant;
import com.hh.user.info.UserInfo;
import com.hh.user.request.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author huihui
 * @date 2024/10/31 17:11
 * @description UserFeign
 */
@FeignClient(name = FeignClientConstant.USER_NAME, contextId = FeignClientConstant.USER_CONTENT_ID, path = "/user")
public interface UserFeign {

    @PostMapping("/getUser")
    Result<UserInfo> getUser(@RequestBody UserRequest request);

}
