package com.shine.common.exception;

import com.shine.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/11/8 14:04
 * @description BaseException
 */
public class BaseException extends RuntimeException implements IException<String> {

    private final IEnum<String> iEnum;

    public BaseException(IEnum<String> iEnum) {
        super(iEnum.getName());
        this.iEnum = iEnum;
    }

    public BaseException(IEnum<String> iEnum, String message) {
        super(message);
        this.iEnum = iEnum;
    }

    /**
     * 获取错误的枚举
     *
     * @return 通用异常枚举
     */
    @Override
    public IEnum<String> getEnum() {
        return iEnum;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
