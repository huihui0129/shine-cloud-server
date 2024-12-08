package com.shine.lock.aspect;

import com.shine.lock.annotation.RedisLock;
import com.shine.lock.manager.LockManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;

/**
 * redis 锁切面类
 */
@Aspect
// @Component 每个服务都有具体的实现 所以就不用给这个注入到bean 而且这个类还是个抽象类
@Slf4j
public abstract class RedisLockAspect {

    @Resource
    private LockManager lockManager;

    @Around(value = "@annotation(com.shine.lock.annotation.RedisLock) && @annotation(redisLock)")
    public Object redisLock(ProceedingJoinPoint point, RedisLock redisLock) throws Throwable {
        String key = redisLock.key();
        long expire = redisLock.expire();
        long waitTime = redisLock.waitTime();
        if (isEl(key)) {
            key = getByEl(key, point);
        }
        RLock lock = lockManager.lock(getRealLockKey(key), expire, waitTime);
        try {
            // 执行业务逻辑
            return point.proceed();
        } finally {
            // 释放锁
            log.info("释放锁");
            lockManager.unlock(lock);
        }
    }

    /**
     * 获取真正的redis key值
     *
     * @param key
     * @param point
     * @return
     */
    abstract String getByEl(String key, ProceedingJoinPoint point);

    /**
     * 锁的键
     *
     * @param key
     * @return
     */
    private String getRealLockKey(String key) {
        return String.format("lock:%s", key);
    }

    /**
     * 解析spEl表达式
     *
     * @param key
     * @return
     */
    private boolean isEl(String key) {
        return key.contains("#");
    }

}
