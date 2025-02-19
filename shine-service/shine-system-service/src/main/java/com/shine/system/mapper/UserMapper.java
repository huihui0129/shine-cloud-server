package com.shine.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.system.entity.User;
import com.shine.system.info.UserInfo;
import com.shine.system.request.UserPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/9 21:56
 * @description UserMapper
 */
//@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询用户
     *
     * @param iPage
     * @param request
     * @return
     */
    IPage<UserInfo> pageUser(Page<User> iPage, @Param("request") UserPageRequest request);

    /**
     * 详情查询用户
     *
     * @param id
     * @return
     */
    UserInfo getById(@Param("id") Long id);

    List<UserInfo> listByIdList(@Param("idList") List<Long> idList);

}
