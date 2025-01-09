package com.shine.security.http;

import com.shine.common.enums.IEnum;

/**
 * @author huihui
 * @date 2024/11/7 11:38
 * @description AuthorityStatus 200000 开头
 */
public enum SecurityStatus implements IEnum<String> {

    AUTH_MODE_ERROR("200001", "我不知道这个授权模式是什么呢"),

    NO_CACHE_PRINCIPAL("200002", "没有缓存的耶，快联系管理员！"),

    EXPIRED_TOKEN("210001", "身份认证过期了呢，重新登录一下吧"),

    NO_TOKEN("210002", "我不知道你是谁哦"),

    ENC_DATA_NULL("210003", "加密数据不能为空"),

    OFFLINE("210004", "你被人踢下线啦！快改密码！！！"),

    NOT_EXISTS_CLIENT("210005", "没有找到这个客户端呢，请先联系管理员注册客户端吧"),

    CLIENT_ID_MISMATCH("210006", "客户端Secret错误了呢，检查下吧"),

    INCORRECT_AUTHORIZATION_CODE("210007", "授权码错误了哦，检查下吧"),

    NO_AUTHORIZATION_CODE("210008", "请先获取授权码哦"),

    AUTHORIZATION_CODE_USED("210009", "授权码被用了耶，重试一下吧"),

    AUTHORIZATION_CODE_EXPIRE("210010", "授权码过期了呢，快重新搞一个"),

    AUTHORIZATION_DEFAULT_ACT("210011", "此grantType无需当前操作"),

    CREATE_USER_MISMATCH("210012", "不是你获取的用什么用"),

    NO_REFRESH_TOKEN("210013", "没找到你的续约令牌耶"),

    ;

    private final String code;

    private final String name;

    SecurityStatus(String code, String name) {
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
