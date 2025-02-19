package com.shine.system.service;

import com.shine.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shine.system.info.MenuInfo;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取用户所有菜单权限
     *
     * @param appId
     * @param userId
     * @return
     */
    List<MenuInfo> getByUserId(Long appId, Long userId);

    List<MenuInfo> listByClient(String clientId);

    List<MenuInfo> getPermissionByUserId(String clientId, Long userId);

}
