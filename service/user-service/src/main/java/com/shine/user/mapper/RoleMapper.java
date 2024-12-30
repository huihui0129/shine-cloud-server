package com.shine.user.mapper;

import com.shine.user.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.user.info.RoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<RoleInfo> selectByUserId(@Param("appId") Long appId, @Param("userId") Long userId);

}
