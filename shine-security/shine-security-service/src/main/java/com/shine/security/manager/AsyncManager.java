package com.shine.security.manager;

import com.shine.async.contsant.AsyncConstant;
import com.shine.security.entity.AccessToken;
import com.shine.security.entity.RefreshToken;
import com.shine.security.service.AccessTokenService;
import com.shine.security.service.RefreshTokenService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/11/26 14:31
 * @description AsyncManager
 */
@Slf4j
@Component
public class AsyncManager {

    @Resource
    private AccessTokenService accessTokenService;

    @Resource
    private RefreshTokenService refreshTokenService;

    @Async(AsyncConstant.DEFAULT_ASYNC_EXECUTOR)
    public void saveToken(AccessToken token, RefreshToken refreshToken) {
        log.info("异步保存授权码");
        accessTokenService.save(token);
        refreshTokenService.save(refreshToken);
        log.info("保存成功");
    }

}
