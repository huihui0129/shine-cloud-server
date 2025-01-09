package com.shine.security.enums;

import com.shine.common.enums.IEnum;
import com.shine.common.exception.BaseException;
import com.shine.security.http.SecurityStatus;
import lombok.AllArgsConstructor;

/**
 * @author huihui
 * @date 2024/11/27 18:21
 * @description AuthorizationResponseTypeEnum
 */
@AllArgsConstructor
public enum AuthorizationResponseTypeEnum implements IEnum<String> {

    CODE("code", "授权码模式"),

    REFRESH_TOKEN("refresh_token", "刷新令牌模式"),

    PASSWORD("password", "密码模式"),

    ;

    private String code;
    private String name;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    public static AuthorizationResponseTypeEnum findByCode(String code) {
        AuthorizationResponseTypeEnum[] values = AuthorizationResponseTypeEnum.values();
        for (AuthorizationResponseTypeEnum item : values) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        throw new BaseException(SecurityStatus.AUTH_MODE_ERROR);
    }

}
