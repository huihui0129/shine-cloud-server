package com.shine.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import com.shine.mybatis.utils.PageUtil;
import com.shine.system.entity.User;
import com.shine.system.enums.MenuTypeEnum;
import com.shine.system.info.MenuInfo;
import com.shine.system.info.UserInfo;
import com.shine.system.mapper.UserMapper;
import com.shine.system.request.UserPageRequest;
import com.shine.system.response.UserPermissionResponse;
import com.shine.system.service.MenuService;
import com.shine.system.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huihui
 * @date 2024/11/9 23:53
 * @description UserServiceImpl
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private MenuService menuService;

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

    @Override
    public UserPermissionResponse getPerm(Long appId, Long userId) {
        UserInfo user = this.getUserById(userId);
        if (user == null) {
            throw new BaseException(ResponseStatus.PARAMS_ERROR, "用户不存在");
        }
        UserPermissionResponse response = new UserPermissionResponse();
        BeanUtil.copyProperties(user, response, true);
        List<MenuInfo> menuInfoList = menuService.getByUserId(appId, userId);
        if (CollectionUtils.isEmpty(menuInfoList)) {
            log.error("用户：{}，没有权限", userId);
            return response;
        }
        Map<Integer, List<MenuInfo>> typeMap = menuInfoList.stream().collect(Collectors.groupingBy(MenuInfo::getType));
        List<MenuInfo> menuList = typeMap.get(MenuTypeEnum.T0.getCode());
        List<MenuInfo> buttonList = typeMap.get(MenuTypeEnum.T1.getCode());
        // TODO
//        response.setMenuList(menuList);
//        if (CollectionUtils.isNotEmpty(buttonList)) {
//            response.setButtonList(buttonList.stream().map(MenuInfo::getPermission).collect(Collectors.toList()));
//        }
        return response;
    }

    @Override
    public List<UserInfo> listByUserIdList(List<Long> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Collections.emptyList();
        }
        return this.baseMapper.listByIdList(userIdList);
    }
}
