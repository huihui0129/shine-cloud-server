package com.hh.utils.status;

import lombok.Getter;

/**
 * @author huihui
 * @date 2024/10/31 16:15
 * @description HttpStatus
 */
@Getter
public enum HttpStatus {

    SUCCESS("000000", "成功"),

    ERROR("999999", "失败"),

    ;

    private String code;

    private String message;

    HttpStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
