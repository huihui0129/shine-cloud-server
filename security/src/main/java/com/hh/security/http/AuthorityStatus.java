package com.hh.security.http;

import com.hh.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/11/7 11:38
 * @description AuthorityStatus 200000 开头
 */
public enum AuthorityStatus implements IEnum {

    ENC_DATA_NULL("200001", "加密数据不能为空"),

    ;

    private final String code;

    private final String name;

    AuthorityStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
