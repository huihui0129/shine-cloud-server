package com.shine.common.exception;

import com.shine.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/11/7 11:34
 * @description IException
 */
public interface IException<T> {

    /**
     * 获取错误的枚举
     *
     * @return 通用异常枚举
     */
    IEnum<T> getEnum();

    /**
     * 获取异常信息
     *
     * @return
     */
    String getMessage();

    /**
     * 格式化异常信息：编码+内容
     *
     * @return
     */
    default String formatMessage() {
        return String.format("[%s] %s", getEnum().getCode(), getMessage());
    }

}
