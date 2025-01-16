package com.shine.lock.annotation;

import com.shine.lock.constant.LockConstant;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 锁
 */
@Target(ElementType.METHOD) // 在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时
@Documented
@Inherited
public @interface RedisLock {

    /**
     * 锁的key
     *
     * @return
     */
    String key();

    /**
     * el表达式 动态Key必须
     *
     * @return
     */
    String el() default "";

    /**
     * 锁超时时间
     * 默认5000ms
     *
     * @return
     */
    long expire() default 0;

    /**
     * 锁等待时间
     * 默认50ms
     *
     * @return
     */
    long waitTime() default 0;

    /**
     * 时间单位 默认毫秒
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 错误信息
     *
     * @return
     */
    String message() default LockConstant.LOCK_ERROR;

}
