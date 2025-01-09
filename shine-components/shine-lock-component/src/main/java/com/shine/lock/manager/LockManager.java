package com.shine.lock.manager;

import com.shine.lock.constant.LockConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
public class LockManager {

    /**
     * 锁最小等待时间 单位：ms
     */
    private static final long MIN_LOCK_WAIT_TIME = 10;

    @Resource
    private RedissonClient redissonClient;

    public RLock lock(String key, long expireTime, long waitTime) {
        return lock(key, expireTime, waitTime, () -> new RuntimeException(LockConstant.LOCK_ERROR));
    }

    private RLock lock(String key, long expireTime, long waitTime, Supplier<RuntimeException> exceptionSupplier) {
        log.info("执行lock");
        if (waitTime < MIN_LOCK_WAIT_TIME) {
            // waitTime = MIN_LOCK_WAIT_TIME;
            waitTime = 0;
        }
        RLock rLock = null;
        try {
            rLock = redissonClient.getLock(key);
            try {
                // 尝试加锁
                // rLock.lock(); 注意：我们自己设置过期时间那么这个锁就不会自动续期，看门狗就不会生效
                // 我们自己不设置锁的过期时间看门狗就可以生效
                // 所以我们可以优化一下注解 如果传入的expireTime和waitTime都为0 那么就说明没指定过期时间
                // 那么就用默认的，看门狗就可以生效，这样就不怕过期了
                if (expireTime == 0 && waitTime == 0) {
                    log.warn("加锁并且启用看门狗");
                    rLock.lock();
                } else {
                    if (rLock.tryLock(waitTime, expireTime, TimeUnit.MILLISECONDS)) {
                        log.warn("加锁并且不启用看门狗");
                    } else {
                        log.error("尝试加锁失败");
                        rLock = null;
                    }
                }
            } catch (InterruptedException e) {
                log.error("获取分布式锁失败, key：{}, e：{}", key, e.getMessage());
                rLock.unlock();
            }
        } catch (Exception e) {
            log.error("获取分布式锁失败, key：{}, e：{}", key, e.getMessage());
            throw new RuntimeException(LockConstant.LOCK_FAIL);
        }
        log.info("加锁结果：{}，key：{}", rLock != null ? "成功" : "失败", key);
        return rLock;
//        if (ObjectUtil.isNotEmpty(rLock)) {
//            return rLock;
//        } else {
//            try {
//                Thread.sleep(waitTime);
//                log.warn("继续尝试加锁，key：{}", key);
//                return this.lock(key, expireTime, waitTime);
//            } catch (InterruptedException e) {
//                log.error("等待失败");
//                throw new RuntimeException(e);
//            }
//        }
    }

    /**
     * 解锁
     *
     * @param rLock
     */
    public void unlock(RLock rLock) {
        try {
            rLock.unlock();
        } catch (Exception e) {
            log.warn("解锁失败，{}", e.getMessage());
        }
    }

}
