package com.hh.security.enums;

import com.hh.common.enums.IEnum;
import com.hh.common.exception.BaseException;
import com.hh.common.status.ResponseStatus;
import com.hh.security.http.AuthorityStatus;
import lombok.AllArgsConstructor;

/**
 * @author huihui
 * @date 2024/11/27 18:21
 * @description AuthorizationResponseTypeEnum
 */
@AllArgsConstructor
public enum AuthorizationResponseTypeEnum implements IEnum<String> {

    CODE("code", "授权码模式"),

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
        throw new BaseException(AuthorityStatus.AUTH_MODE_ERROR);
    }

}
