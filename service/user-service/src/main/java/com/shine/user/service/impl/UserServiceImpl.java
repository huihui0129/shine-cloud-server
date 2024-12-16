package com.shine.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.mybatis.utils.PageUtil;
import com.shine.user.entity.User;
import com.shine.user.info.UserInfo;
import com.shine.user.mapper.UserMapper;
import com.shine.user.request.UserPageRequest;
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
    public IPage<UserInfo> pageUser(UserPageRequest request) {
        return this.baseMapper.pageUser(PageUtil.buildPage(request), request);
    }

    @Override
    public UserInfo getUserById(Long id) {
        return this.baseMapper.getById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        return this.removeById(id);
    }

}
