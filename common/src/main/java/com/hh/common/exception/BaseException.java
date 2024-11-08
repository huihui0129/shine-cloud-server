package com.hh.common.exception;

import com.hh.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/11/8 14:04
 * @description BaseException
 */
public class BaseException extends RuntimeException implements IException {

    private final IEnum iEnum;

    public BaseException(IEnum iEnum) {
        super(iEnum.getName());
        this.iEnum = iEnum;
    }

    public BaseException(IEnum iEnum, String message) {
        super(message);
        this.iEnum = iEnum;
    }

    /**
     * 获取错误的枚举
     *
     * @return 通用异常枚举
     */
    @Override
    public IEnum getEnum() {
        return iEnum;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
