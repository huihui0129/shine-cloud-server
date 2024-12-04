package com.hh.user.feign.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.common.response.Result;
import com.hh.user.entity.User;
import com.hh.user.feign.UserFeign;
import com.hh.user.info.UserInfo;
import com.hh.user.request.UserRequest;
import com.hh.user.service.UserService;
import jakarta.annotation.Resource;
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

    @Resource
    private UserService userService;

    @Override
    public Result<UserInfo> getUser(UserRequest request) {
        User user = userService.getOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, request.getUsername())
        );
        if (user == null) {
            return Result.error();
        }
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(user, userInfo, true);
        return Result.success(userInfo);
    }
}
