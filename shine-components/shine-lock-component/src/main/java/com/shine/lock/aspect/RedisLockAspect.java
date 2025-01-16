package com.shine.lock.aspect;

import com.shine.lock.annotation.RedisLock;
import com.shine.lock.constant.LockConstant;
import com.shine.lock.manager.LockManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis 锁切面类
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {

    @Resource
    private LockManager lockManager;

    @Around(value = "@annotation(com.shine.lock.annotation.RedisLock) && @annotation(redisLock)")
    public Object redisLock(ProceedingJoinPoint point, RedisLock redisLock) throws Throwable {
        String key = redisLock.key();
        String el = redisLock.el();
        long expire = redisLock.expire();
        long waitTime = redisLock.waitTime();
        String message = redisLock.message();
        if (isEl(el)) {
            key = key + ":" + getByEl(el, point);
        }
        // 需要转换的
        TimeUnit timeUnit = redisLock.timeUnit();
        TimeUnit milliseconds = TimeUnit.MILLISECONDS;
        // 将milliseconds的时间expire转换为timeUnit
        // 转换后的毫秒数
        long expireOfConvert = milliseconds.convert(expire, timeUnit);
        long waitOfConvert = milliseconds.convert(waitTime, timeUnit);
        RLock lock = lockManager.lock(LockConstant.LOCK_KEY_PREFIX + key, expireOfConvert, waitOfConvert, message);
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
    public String getByEl(String key, ProceedingJoinPoint point) {
        // 初始化 SpEL 解析上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        // 将整个参数对象绑定到上下文中
        if (parameterNames != null && args != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        // 解析表达式
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(key).getValue(context, String.class);
    }

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
     * @param el
     * @return
     */
    private boolean isEl(String el) {
        return StringUtils.isNotBlank(el) && el.contains("#");
    }

}
