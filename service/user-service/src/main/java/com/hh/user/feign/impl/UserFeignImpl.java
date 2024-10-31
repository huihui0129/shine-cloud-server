package com.hh.user.feign.impl;

import com.hh.user.feign.UserFeign;
import com.hh.utils.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huihui
 * @date 2024/10/31 17:25
 * @description UserFeignImpl
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserFeignImpl implements UserFeign {

    @Override
    public Result<String> getUser() {
        return Result.success("成功获取用户ID：666");
    }

}
