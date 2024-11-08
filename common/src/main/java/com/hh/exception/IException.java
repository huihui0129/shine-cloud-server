package com.hh.exception;

import com.hh.enums.IEnum;

/**
 * @author huihui
 * @date 2024/11/7 11:34
 * @description IException
 */
public interface IException {

    /**
     * 获取错误的枚举
     *
     * @return 通用异常枚举
     */
    IEnum getEnum();

    String getMessage();

    default String formatMessage() {
        return String.format("[%s] %s", getEnum().getCode(), getMessage());
    }

}
