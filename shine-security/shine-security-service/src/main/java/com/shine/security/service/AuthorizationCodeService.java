package com.shine.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shine.security.entity.AuthorizationCode;
import com.shine.security.info.AuthorizationCodeInfo;
import com.shine.security.request.AuthorizationCodePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author huihui
 * @date 2025/01/22 17:23
 * @description AuthorizationCodeService
 */
public interface AuthorizationCodeService extends IService<AuthorizationCode> {

    /**
     * 分页查询授权码
     *
     * @param request
     * @return
     */
    IPage<AuthorizationCodeInfo> pageQuery(AuthorizationCodePageRequest request);

   /**
    * 详情查询授权码
    *
    * @param id
    * @return
    */
    AuthorizationCodeInfo getById(Long id);

   /**
    * 根据ID删除授权码
    *
    * @param id
    * @return
    */
   Boolean deleteById(Long id);

}
