package com.shine.system.feign.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shine.common.exception.BaseException;
import com.shine.common.response.Result;
import com.shine.common.status.ResponseStatus;
import com.shine.system.entity.User;
import com.shine.system.feign.UserFeign;
import com.shine.system.info.MenuInfo;
import com.shine.system.info.RoleInfo;
import com.shine.system.info.UserInfo;
import com.shine.system.request.UserRequest;
import com.shine.system.response.UserPermissionResponse;
import com.shine.system.service.MenuService;
import com.shine.system.service.RoleService;
import com.shine.system.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Override
    public Result<UserPermissionResponse> getUser(UserRequest request) {
        if (request.getUserId() == null && StringUtils.isBlank(request.getUsername())) {
            throw new BaseException(ResponseStatus.PARAMS_ERROR, "缺少用户ID或用户名");
        }
        User user = userService.getOne(
                Wrappers.<User>lambdaQuery()
                        .eq(request.getUserId() != null, User::getId, request.getUserId())
                        .eq(StringUtils.isNotBlank(request.getUsername()), User::getUsername, request.getUsername())
        );
        if (user == null) {
            return Result.error();
        }
        UserPermissionResponse userInfo = new UserPermissionResponse();
        BeanUtil.copyProperties(user, userInfo, true);
        List<RoleInfo> roleList = roleService.getByUserId(user.getId());
        userInfo.setRoleList(roleList.stream().map(RoleInfo::getCode).toList());
        List<MenuInfo> permissionList = menuService.getPermissionByUserId(request.getClientId(), user.getId());
        userInfo.setPermissionList(permissionList.stream().map(MenuInfo::getPermission).toList());
        return Result.success(userInfo);
    }

    @Override
    public Result<Boolean> saveUser(UserInfo userInfo) {
        log.info("用户新增：{}", JSON.toJSONString(userInfo));
        User user = new User();
        BeanUtil.copyProperties(userInfo, user, true);
        boolean flag = userService.save(user);
        return Result.success(flag);
    }

    @Override
    public List<UserInfo> listByUserIdList(List<Long> userIdList) {
        return userService.listByUserIdList(userIdList);
    }
}
