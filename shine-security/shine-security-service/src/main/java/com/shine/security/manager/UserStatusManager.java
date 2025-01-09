package com.shine.security.manager;

import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import com.shine.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2025/1/9 16:31
 * @description UserStatusManager
 */
@Slf4j
@Component
public class UserStatusManager {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 踢人下线
     *
     * @param userId
     * @return
     */
    public Boolean offline(String service, Long userId) {
        String key = switch (service) {
            case "security" -> SecurityConstant.TOKEN_REDIS_PREFIX + userId;
            case "user" -> SecurityConstant.ACCESS_TOKEN_REDIS_PREFIX + userId;
            default -> throw new BaseException(ResponseStatus.PARAMS_ERROR, "未找到服务");
        };
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(SecurityConstant.OFFLINE_REDIS_PREFIX + userId, "1");
        return true;
    }

}
