package com.shine.common.status;

import com.shine.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huihui
 * @date 2024/10/31 16:15
 * @description HttpStatus
 */
public enum ResponseStatus implements IEnum<String> {

    /**
     * 请求成功
     */
    SUCCESS("000000", "成功"),

    /**
     * 参数错误
     */
    PARAMS_ERROR("100000", "你的参数不对哦~"),

    /**
     * 权限不足
     */
    UNAUTHORIZED("200000", "你没有权限访问哦~"),

    /**
     * 远程调用错误
     */
    FEIGN_ERROR("300000", "远程调用失败~"),

    /**
     * 服务器未知异常
     */
    ERROR("900000", "服务器出错啦，请稍后重试~"),

    ;

    private final String code;

    private final String name;

    ResponseStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

}
