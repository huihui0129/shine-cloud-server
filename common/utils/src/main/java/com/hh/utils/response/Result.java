package com.hh.utils.response;

import com.hh.utils.status.HttpStatus;
import lombok.Data;

/**
 * @author huihui
 * @date 2024/10/31 16:11
 * @description Result
 */
@Data
public class Result<T> {

    private String code;

    private String message;

    private T data;

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(String code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> success(HttpStatus status, T data) {
        return success(status.getCode(), status.getMessage(), data);
    }

    public static <T> Result<T> success(T data) {
        return success(HttpStatus.SUCCESS, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

}
