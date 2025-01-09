package com.shine.id.code;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author huihui
 * @date 2025/1/9 11:30
 * @description CodeManager 生成Code编码
 */
public class DateManager {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String ID_DEFAULT_PATTERN = "yyyyMMddHHmmss";

    public static final String ID_DATE_TIMESTAMP_PATTERN = "yyyyMMddHHmmssSSS";

    public static String getDatetime(String pattern) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return now.format(formatter);
    }

    public static String getDatetime() {
        return getDatetime(DEFAULT_PATTERN);
    }

}
