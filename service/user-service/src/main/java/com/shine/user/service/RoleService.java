package com.shine.user.service;

import com.shine.user.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shine.user.info.RoleInfo;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
public interface RoleService extends IService<Role> {

    List<RoleInfo> getByUserId(Long appId, Long userId);

}
