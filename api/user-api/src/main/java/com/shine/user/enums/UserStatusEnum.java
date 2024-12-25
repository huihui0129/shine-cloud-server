package com.shine.user.enums;

import com.shine.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/12/25 16:58
 * @description UserStatusEnum
 */
public enum UserStatusEnum implements IEnum<Integer> {

    /**
     * 禁用
     */
    S0(0, "禁用"),

    /**
     * 启用
     */
    S1(1, "启用"),

    ;

    private Integer code;

    private String name;

    UserStatusEnum(Integer code, String name) {
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
