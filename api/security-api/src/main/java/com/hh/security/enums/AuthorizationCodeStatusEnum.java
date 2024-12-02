package com.hh.security.enums;

import com.hh.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/12/1 15:35
 * @description AuthorizationCodeStatusEnum
 */
public enum AuthorizationCodeStatusEnum implements IEnum<Integer> {

    S1(1, "未使用"),

    S2(2, "已使用"),

    S3(3, "已过期"),

    ;

    private Integer code;
    private String name;

    AuthorizationCodeStatusEnum(Integer code, String name) {
        this.code =code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
