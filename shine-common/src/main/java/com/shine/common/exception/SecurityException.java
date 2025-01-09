package com.shine.common.exception;

import com.shine.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/12/9 15:38
 * @description AuthException
 */

public class SecurityException extends RuntimeException implements IException {

    private final IEnum<String> iEnum;

    public SecurityException(IEnum<String> iEnum) {
        super(iEnum.getName());
        this.iEnum = iEnum;
    }

    public SecurityException(IEnum<String> iEnum, String message) {
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
