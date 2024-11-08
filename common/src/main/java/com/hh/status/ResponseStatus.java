package com.hh.status;

import com.hh.enums.IEnum;
import lombok.Getter;

/**
 * @author huihui
 * @date 2024/10/31 16:15
 * @description HttpStatus
 */
@Getter
public enum ResponseStatus implements IEnum {

    SUCCESS("000000", "成功"),

    ERROR("999999", "系统异常"),

    UNAUTHORIZED("100000", "未授权"),

    ;

    private final String code;

    private final String name;

    ResponseStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
