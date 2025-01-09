package com.shine.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.user.entity.Role;
import com.shine.user.info.RoleInfo;
import com.shine.user.mapper.RoleMapper;
import com.shine.user.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<RoleInfo> getByUserId(Long appId, Long userId) {
        if (userId == null) {
            log.error("查询用户角色信息缺失用户ID");
        }
        return this.baseMapper.selectByUserId(appId, userId);
    }

}
