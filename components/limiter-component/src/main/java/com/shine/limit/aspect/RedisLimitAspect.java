package com.shine.limit.aspect;

import com.shine.limit.annotation.RedisLimit;
import com.shine.limit.constant.LimitConstant;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis 控流切面类
 */
@Aspect
@Component
@Slf4j
public class RedisLimitAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private DefaultRedisScript<Long> redisScript;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redisLimit.lua")));
    }

    @Before(value = "@annotation(com.shine.limit.annotation.RedisLimit)")
    public void redisLimit(JoinPoint point) {
        // 拿到方法名称 方法签名
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 拿到注解
        RedisLimit redisLimitAnno = methodSignature.getMethod().getAnnotation(RedisLimit.class);
        // 拿到属性
        // redis的key
        String redisKey = redisLimitAnno.key();
        // 过期时间
        long expire = redisLimitAnno.expire();
        // 访问次数
        int frequency = redisLimitAnno.frequency();
        // 需要转换的
        TimeUnit timeUnit = redisLimitAnno.timeUnit();
        TimeUnit milliseconds = TimeUnit.MILLISECONDS;
        // 将milliseconds的时间expire转换为timeUnit
        // 转换后的毫秒数
        long millisecondsOfConvert = milliseconds.convert(expire, timeUnit);
        // 返回信息
        String message = redisLimitAnno.message();
        // key 为空
        if (StringUtils.isEmpty(redisKey)) {
            throw new RuntimeException(LimitConstant.REDIS_KEY_NULL);
        }
        List<String> keys = new ArrayList<>();
        keys.add(redisKey);
        Long currentNum = stringRedisTemplate.execute(redisScript, keys, String.valueOf(frequency), String.valueOf(millisecondsOfConvert));
        if (currentNum.equals(0L)) {
            log.warn("当前访问次数已达上限：{}，被限流", frequency);
            throw new RuntimeException(message);
        }
    }

}
