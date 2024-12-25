package com.shine.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import com.shine.mybatis.utils.PageUtil;
import com.shine.user.entity.User;
import com.shine.user.enums.MenuTypeEnum;
import com.shine.user.info.MenuInfo;
import com.shine.user.info.UserInfo;
import com.shine.user.mapper.UserMapper;
import com.shine.user.request.UserPageRequest;
import com.shine.user.response.UserPermissionResponse;
import com.shine.user.service.MenuService;
import com.shine.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

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
    public UserPermissionResponse getPerm(Long id) {
        UserInfo user = this.getUserById(id);
        if (user == null) {
            throw new BaseException(ResponseStatus.PARAMS_ERROR, "用户不存在");
        }
        UserPermissionResponse response = new UserPermissionResponse();
        BeanUtil.copyProperties(user, response, true);
        List<MenuInfo> menuInfoList = menuService.getByUserId(id);
        if (CollectionUtils.isEmpty(menuInfoList)) {
            log.error("用户：{}，没有权限", id);
            return response;
        }
        Map<Integer, List<MenuInfo>> typeMap = menuInfoList.stream().collect(Collectors.groupingBy(MenuInfo::getType));
        List<MenuInfo> menuList = typeMap.get(MenuTypeEnum.T0.getCode());
        List<MenuInfo> buttonList = typeMap.get(MenuTypeEnum.T1.getCode());
        response.setMenuList(menuList);
        if (CollectionUtils.isNotEmpty(buttonList)) {
            response.setButtonList(buttonList.stream().map(MenuInfo::getPermission).collect(Collectors.toList()));
        }
        return response;
    }

}
