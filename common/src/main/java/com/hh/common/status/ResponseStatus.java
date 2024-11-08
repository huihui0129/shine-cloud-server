package com.hh.common.status;

import com.hh.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huihui
 * @date 2024/10/31 16:15
 * @description HttpStatus
 */
@Getter
public enum ResponseStatus implements IEnum {

    SUCCESS("000000", "成功"),

    ERROR("999999", "服务器出错啦，请稍后重试~"),

    UNAUTHORIZED("200000", "你没有权限访问哦~"),

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
