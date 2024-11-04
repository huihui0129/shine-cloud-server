package com.hh.utils.status;

import lombok.Getter;

/**
 * @author huihui
 * @date 2024/10/31 16:15
 * @description HttpStatus
 */
@Getter
public enum ResponseStatus {

    SUCCESS("000000", "成功"),

    ERROR("999999", "系统异常"),

    UNAUTHORIZED("100000", "未授权"),

    ;

    private String code;

    private String message;

    ResponseStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
