package com.hh.common.response;

import com.hh.common.enums.IEnum;
import com.hh.common.status.ResponseStatus;
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

    private Boolean success;

    public Result(String code, String message, T data, Boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public static <T> Result<T> exec(String code, String message, T data, Boolean success) {
        return new Result<>(code, message, data, success);
    }

    public static <T> Result<T> exec(String code, String message, Boolean success) {
        return exec(code, message, null, success);
    }

    public static <T> Result<T> exec(IEnum<String> status, T data, Boolean success) {
        return exec(status.getCode(), status.getName(), data, success);
    }

    public static <T> Result<T> success(T data) {
        return exec(ResponseStatus.SUCCESS, data, true);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(ResponseStatus status, T data) {
        return exec(status, data, false);
    }

    public static <T> Result<T> error() {
        return exec(ResponseStatus.ERROR, null, false);
    }

    public static <T> Result<T> error(IEnum<String> status) {
        return exec(status, null, false);
    }

    public static <T> Result<T> error(String code, String message) {
        return exec(code, message, false);
    }

}
