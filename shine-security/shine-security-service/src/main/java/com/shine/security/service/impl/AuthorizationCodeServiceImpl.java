package com.shine.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.mybatis.utils.PageUtil;
import com.shine.security.entity.AuthorizationCode;
import com.shine.security.info.AuthorizationCodeInfo;
import com.shine.security.mapper.AuthorizationCodeMapper;
import com.shine.security.service.AuthorizationCodeService;
import com.shine.security.info.AuthorizationCodeInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shine.security.request.AuthorizationCodePageRequest;
import org.apache.ibatis.annotations.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huihui
 * @date 2025/01/22 17:21
 * @description AuthorizationCodeServiceImpl
 */
@Slf4j
@Service
public class AuthorizationCodeServiceImpl extends ServiceImpl<AuthorizationCodeMapper, AuthorizationCode> implements AuthorizationCodeService {

    @Override
    public IPage<AuthorizationCodeInfo> pageQuery(AuthorizationCodePageRequest request) {
        return this.baseMapper.pageQuery(PageUtil.buildPage(request), request);
    }

    @Override
    public AuthorizationCodeInfo getById(Long id) {
        return this.baseMapper.getById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        return this.removeById(id);
    }

}