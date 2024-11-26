package com.hh.security.util;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/4 11:03
 * @description PathMatchUtil
 */
public class PathMatchUtil {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    public static boolean match(List<String> patterns, String path) {
        return !notMatch(patterns, path);
    }

    public static boolean notMatch(List<String> patterns, String path) {
        if (CollectionUtils.isEmpty(patterns)) {
            return true;
        }
        for (String excludePath : patterns) {
            if (MATCHER.match(excludePath, path)) {
                return false;
            }
        }
        return true;
    }

}
