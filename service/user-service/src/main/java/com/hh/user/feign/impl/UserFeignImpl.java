package com.hh.user.feign.impl;

import com.hh.common.exception.BaseException;
import com.hh.common.status.ResponseStatus;
import com.hh.user.feign.UserFeign;
import com.hh.common.response.Result;
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
        log.info("远程调用我了");
        return Result.success("成功获取用户ID：666");
    }

    @Override
    public Result<String> getException() {
        log.info("搞个异常");
        throw new BaseException(ResponseStatus.UNAUTHORIZED);
    }

    @Override
    public Result<String> getConnTime() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.success("算你厉害");
    }
}
