package com.shine.lock.annotation;

import java.lang.annotation.*;

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

}
