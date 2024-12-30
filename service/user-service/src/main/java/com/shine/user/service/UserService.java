package com.shine.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shine.user.entity.User;
import com.shine.user.info.UserInfo;
import com.shine.user.request.UserPageRequest;
import com.shine.user.response.UserPermissionResponse;

/**
 * @author huihui
 * @date 2024/11/9 23:52
 * @description UserService
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询用户
     *
     * @param request
     * @return
     */
    IPage<UserInfo> pageUser(UserPageRequest request);

    /**
     * 详情查询用户
     *
     * @param id
     * @return
     */
    UserInfo getUserById(Long id);

    /**
     * 根据ID删除用户
     *
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    UserPermissionResponse getPerm(Long appId, Long id);

}
