package com.shine.limit.annotation;

import com.shine.limit.constant.LimitConstant;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 方法限流
 * 会限制某一个方法或接口在一定时间内的访问量 无论ip是多少
 */
@Target(ElementType.METHOD) // 在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时
@Documented
@Inherited
public @interface RedisLimit {

    /**
     * 键
     *
     * @return
     */
    String key();

    /**
     * 时间
     * time 后失效 单位：ms
     * 默认一分钟
     *
     * @return
     */
    long expire() default 60 * 1000;

    /**
     * 频率 请求次数
     * 默认不限次数(最大是int的最大值)
     *
     * @return
     */
    int frequency() default Integer.MAX_VALUE;

    /**
     * 时间单位 默认毫秒
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 信息，请求超过次数后返回给前端的信息
     * 默认当前排队人数过多
     *
     * @return
     */
    String message() default LimitConstant.LINEUP_PERSON_MANY;

}
