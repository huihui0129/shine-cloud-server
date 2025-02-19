package com.shine.system.mapper;

import com.shine.system.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.system.info.MenuInfo;
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

    List<MenuInfo> selectByUserId(@Param("clientId") Long clientId, @Param("userId") Long userId);

    List<MenuInfo> listByClientId(@Param("clientId") String clientId, @Param("userId") Long userId);

}
