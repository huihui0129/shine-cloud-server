package com.hh.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.security.entity.AccessToken;
import com.hh.security.mapper.AccessTokenMapper;
import com.hh.security.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huihui
 * @date 2024/12/05 15:28
 * @description AccessTokenServiceImpl
 */
@Slf4j
@Service
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenMapper, AccessToken> implements AccessTokenService {

}