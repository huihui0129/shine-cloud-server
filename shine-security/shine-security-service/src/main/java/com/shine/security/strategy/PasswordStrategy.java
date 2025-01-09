package com.shine.security.strategy;

import com.shine.security.request.PasswordTokenRequest;
import com.shine.security.response.AccessTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2025/1/9 14:46
 * @description PasswordStrategy
 */
@Slf4j
@Component
public class PasswordStrategy implements AuthorizationStrategy<PasswordTokenRequest> {

    @Override
    public AccessTokenResponse token(PasswordTokenRequest request) {
        log.info("使用密码模式获取Token：{}", request.getUsername());
        return null;
    }

}
