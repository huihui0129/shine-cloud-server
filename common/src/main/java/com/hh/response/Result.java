package com.hh.response;

import com.hh.status.ResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huihui
 * @date 2024/10/31 16:11
 * @description Result
 */
@Data
@NoArgsConstructor
public class Result<T> {

    private String code;

    private String message;

    private T data;

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> exec(String code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> exec(ResponseStatus status, T data) {
        return exec(status.getCode(), status.getName(), data);
    }

    public static <T> Result<T> success(T data) {
        return exec(ResponseStatus.SUCCESS, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(ResponseStatus status, T data) {
        return exec(status, data);
    }

    public static <T> Result<T> error(T data) {
        return exec(ResponseStatus.ERROR, data);
    }

    public static <T> Result<T> error(ResponseStatus status) {
        return exec(status, null);
    }

    public static <T> Result<T> error() {
        return exec(ResponseStatus.ERROR, null);
    }

}
