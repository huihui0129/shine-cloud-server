package com.shine.user.enums;

import com.shine.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/12/25 17:48
 * @description MenuTypeEnum
 */
public enum MenuTypeEnum implements IEnum<Integer> {

    /**
     * 菜单
     */
    T0(0, "菜单"),

    /**
     * 按钮
     */
    T1(1, "按钮"),

    ;

    private Integer code;

    private String name;

    MenuTypeEnum(Integer code, String name) {
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