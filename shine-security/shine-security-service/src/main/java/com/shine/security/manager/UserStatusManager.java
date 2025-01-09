package com.shine.security.manager;

import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import com.shine.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    private static final long TIMEOUT = 5 * 60 * 1000; // 过期时间五分钟

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

    /**
     * 用户在线续约
     *
     * @param service
     * @param userId
     * @return
     */
    public Boolean renew(String service, Long userId) {
        String key = SecurityConstant.ONLINE_REDIS_PREFIX + service + ":" + userId;
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey != null && hasKey) {
            redisTemplate.expire(key, TIMEOUT, TimeUnit.MILLISECONDS);
        } else {
            redisTemplate.opsForValue().set(key, "online", TIMEOUT, TimeUnit.MILLISECONDS);
        }
        return true;
    }

    public long getOnlineUserCount(String service) {
        Set<String> userKeys = new HashSet<>();
        long cursor;
        String pattern = SecurityConstant.ONLINE_REDIS_PREFIX + (StringUtils.isNotBlank(service) ? service : "*") + ":*";
        do {
            // 执行 SCAN 命令，扫描匹配的键
            Cursor<byte[]> resultCursor = redisTemplate.execute(
                    (RedisCallback<Cursor<byte[]>>) (connection) -> connection.scan(ScanOptions.scanOptions().match(pattern).count(100).build())
            );
            // 处理扫描结果
            while (resultCursor.hasNext()) {
                byte[] key = resultCursor.next();
                userKeys.add(new String(key));
            }
            // 更新游标
            cursor = resultCursor.getCursorId();
        } while (cursor != 0);
        // 返回匹配的键数量
        return userKeys.size();
    }

}
