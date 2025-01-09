package com.shine.id.id;

import java.util.UUID;

/**
 * @author huihui
 * @date 2025/1/9 11:15
 * @description UUIDManager
 */
public class UUIDManager {

    /**
     * 获取原始UUID
     *
     * @return UUID
     */
    public static String getOriginUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 去掉-的UUID
     *
     * @return UUID
     */
    public static String getUUID() {
        return getOriginUUID().replaceAll("-", "");
    }

    /**
     * 多倍UUID
     *
     * @return UUID
     */
    public static String getMultipleUUID(int multiple) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < multiple; i++) {
            sb.append(getUUID());
        }
        return sb.toString();
    }

    /**
     * 多倍UUID 默认的是2倍
     *
     * @return UUID
     */
    public static String getMultipleUUID() {
        return getMultipleUUID(2);
    }

    /**
     * Long类型的UUID
     *
     * @return UUID
     */
    public static long getLongUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.getMostSignificantBits();
    }

}
