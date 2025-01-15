package com.shine.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.security.context.SecurityContextHolder;
import com.shine.system.entity.Menu;
import com.shine.system.info.MenuInfo;
import com.shine.system.mapper.MenuMapper;
import com.shine.system.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<MenuInfo> getByUserId(Long clientId, Long userId) {
        if (userId == null) {
            log.error("获取用户菜单用户ID为空");
            return Collections.emptyList();
        }
        return this.baseMapper.selectByUserId(clientId, userId);
    }

    @Override
    public List<MenuInfo> listByClient(String clientId) {
        if (StringUtils.isBlank(clientId)) {
            log.error("获取用户菜单无客户端ID");
            return Collections.emptyList();
        }
        Long id = SecurityContextHolder.getContext().getPrincipal().getId();
        List<MenuInfo> menuInfoList = this.baseMapper.listByClientId(clientId, id);
        List<MenuInfo> parentList = menuInfoList.stream().filter(item -> item.getParentId().equals(0L)).toList();
        for (MenuInfo menuInfo : parentList) {
            List<MenuInfo> children = menuInfoList.stream().filter(item -> item.getParentId().equals(menuInfo.getId())).toList();
            menuInfo.setChildren(children);
        }
        return parentList;
    }

    @Override
    public List<MenuInfo> getPermissionByUserId(String clientId, Long userId) {
        if (StringUtils.isBlank(clientId)) {
            log.error("获取用户权限无客户端ID");
            return Collections.emptyList();
        }
        List<MenuInfo> menuInfoList = this.baseMapper.listByClientId(clientId, userId);
        return menuInfoList.stream().filter(item -> item.getParentId().equals(1L)).toList();
    }
}
