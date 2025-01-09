package com.shine.security.enums;

import com.shine.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/12/25 15:32
 * @description AuthorizationCodeStatusEnum
 */
public enum AuthorizationCodeStatusEnum implements IEnum<Integer> {

    /**
     * 未使用
     */
    S1(1, "未使用"),

    /**
     * 已使用
     */
    S2(2, "已使用"),

    /**
     * 已过期
     */
    S3(3, "已过期"),

    ;

    private Integer code;

    private String name;

    AuthorizationCodeStatusEnum(Integer code, String name) {
        this.code = code;
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