package com.shine.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.security.entity.RefreshToken;
import com.shine.security.mapper.RefreshTokenMapper;
import com.shine.security.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huihui
 * @date 2025/01/06 13:41
 * @description RefreshTokenServiceImpl
 */
@Slf4j
@Service
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper, RefreshToken> implements RefreshTokenService {


}