package com.hh.security.exception;

import com.hh.enums.IEnum;
import com.hh.exception.IException;

/**
 * @author huihui
 * @date 2024/11/7 11:36
 * @description AuthorizationException
 */
public class AuthorityException extends RuntimeException implements IException {

    private final IEnum iEnum;

    private final String message;

    public AuthorityException(IEnum iEnum) {
        super(iEnum.getMessage());
        this.iEnum = iEnum;
        this.message = iEnum.getMessage();
    }

    public AuthorityException(IEnum iEnum, String message) {
        super(message);
        this.iEnum = iEnum;
        this.message = message;
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
        return message;
    }

}
