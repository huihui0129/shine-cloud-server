package com.shine.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shine.system.entity.User;
import com.shine.system.info.UserInfo;
import com.shine.system.request.UserPageRequest;
import com.shine.system.response.UserPermissionResponse;

import java.util.List;

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

    UserPermissionResponse getPerm(Long appId, Long userId);

    List<UserInfo> listByUserIdList(List<Long> userIdList);

}
