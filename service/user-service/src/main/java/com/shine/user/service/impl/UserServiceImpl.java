package com.shine.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.user.entity.User;
import com.shine.user.info.UserInfo;
import com.shine.user.mapper.UserMapper;
import com.shine.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huihui
 * @date 2024/11/9 23:53
 * @description UserServiceImpl
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public UserInfo getUserById(Long id) {
        if (id == null) {
            return null;
        }
        User user = this.getOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getId, id)
        );
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setDeleted(user.getDeleted());
        return userInfo;
    }

}
