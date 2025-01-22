package com.shine.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.security.entity.AuthorizationCode;
import com.shine.security.info.AuthorizationCodeInfo;
import com.shine.security.request.AuthorizationCodePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author huihui
 * @date 2025/01/22 17:21
 * @description AuthorizationCodeMapper
 */
public interface AuthorizationCodeMapper extends BaseMapper<AuthorizationCode> {

    /**
     * 分页查询授权码
     *
     * @param iPage
     * @param request
     * @return
     */
    IPage<AuthorizationCodeInfo> pageQuery(Page<AuthorizationCode> iPage, @Param("request") AuthorizationCodePageRequest request);

    /**
     * 详情查询授权码
     *
     * @param id
     * @return
     */
    AuthorizationCodeInfo getById(@Param("id") Long id);

}
