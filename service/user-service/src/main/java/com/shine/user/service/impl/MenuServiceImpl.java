package com.shine.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.user.entity.Menu;
import com.shine.user.info.MenuInfo;
import com.shine.user.mapper.MenuMapper;
import com.shine.user.service.MenuService;
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
    public List<MenuInfo> getByUserId(Long appId, Long userId) {
        if (userId == null) {
            log.error("获取用户菜单用户ID为空");
            return Collections.emptyList();
        }
        return this.baseMapper.selectByUserId(appId, userId);
    }

}
