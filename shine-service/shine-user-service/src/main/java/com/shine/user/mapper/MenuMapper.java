package com.shine.user.mapper;

import com.shine.user.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.user.info.MenuInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author huihui
 * @since 2024-11-18
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<MenuInfo> selectByUserId(@Param("appId") Long appId, @Param("userId") Long userId);

}
