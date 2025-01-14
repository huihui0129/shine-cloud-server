package com.shine.system.feign;

import com.shine.common.response.Result;
import com.shine.feign.constant.FeignClientConstant;
import com.shine.system.info.UserInfo;
import com.shine.system.request.UserRequest;
import com.shine.system.response.UserPermissionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author huihui
 * @date 2024/10/31 17:11
 * @description UserFeign
 */
@FeignClient(name = FeignClientConstant.SYSTEM_NAME, contextId = FeignClientConstant.SYSTEM_CONTENT_ID, path = "/user")
public interface UserFeign {

    @PostMapping("/getUser")
    Result<UserPermissionResponse> getUser(@RequestBody UserRequest request);

    @PostMapping("/save")
    Result<Boolean> saveUser(UserInfo userInfo);

    @PostMapping("/listByUserIdList")
    List<UserInfo> listByUserIdList(@RequestBody List<Long> userIdList);

}
