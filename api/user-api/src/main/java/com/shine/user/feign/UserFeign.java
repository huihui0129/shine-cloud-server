package com.shine.user.feign;

import com.shine.common.response.Result;
import com.shine.feign.constant.FeignClientConstant;
import com.shine.user.info.UserInfo;
import com.shine.user.request.UserRequest;
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

    @PostMapping("/save")
    Result<Boolean> saveUser(UserInfo userInfo);

}
