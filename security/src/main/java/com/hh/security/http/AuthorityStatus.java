package com.hh.security.http;

import com.hh.enums.IEnum;
import com.hh.exception.IException;

/**
 * @author huihui
 * @date 2024/11/7 11:38
 * @description AuthorityStatus 200000 开头
 */
public enum AuthorityStatus implements IEnum {

    ENC_DATA_NULL("200001", "加密数据不能为空"),

    ;

    private String code;

    private String message;

    AuthorityStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
