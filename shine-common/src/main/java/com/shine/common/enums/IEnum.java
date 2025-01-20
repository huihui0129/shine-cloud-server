package com.shine.common.enums;

/**
 * @author huihui
 * @date 2024/11/7 11:33
 * @description IEnum
 */
public interface IEnum<T> {

    /**
     * 获取枚举值
     *
     * @return
     */
    T getCode();

    /**
     * 获取枚举说明
     *
     * @return
     */
    String getName();

}
