package com.shine.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.security.entity.AccessToken;
import com.shine.security.mapper.AccessTokenMapper;
import com.shine.security.service.AccessTokenService;
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