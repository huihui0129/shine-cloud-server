package com.shine.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shine.user.entity.User;
import com.shine.user.info.UserInfo;

/**
 * @author huihui
 * @date 2024/11/9 23:52
 * @description UserService
 */
public interface UserService extends IService<User> {

    UserInfo getUserById(Long id);

}
