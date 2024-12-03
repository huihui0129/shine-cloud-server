package com.hh.security.http;

import com.hh.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/11/7 11:38
 * @description AuthorityStatus 200000 开头
 */
public enum AuthorityStatus implements IEnum<String> {

    AUTH_MODE_ERROR("200001", "我不知道这个授权模式是什么呢"),

    NO_CACHE_PRINCIPAL("200002", "没有缓存的耶，快联系管理员！"),

    EXPIRED_TOKEN("210001", "身份认证过期了呢，重新登录一下吧"),

    NO_TOKEN("210002", "我不知道你是谁哦"),

    ENC_DATA_NULL("210003", "加密数据不能为空"),

    OFFLINE("210004", "你被人踢下线啦！快改密码！！！"),

    NOT_EXISTS_CLIENT("210005", "没有找到这个客户端呢，请先联系管理员注册客户端吧"),

    CLIENT_ID_MISMATCH("210006", "客户端Secret错误了呢，检查下吧"),

    ;

    private final String code;

    private final String name;

    AuthorityStatus(String code, String name) {
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
