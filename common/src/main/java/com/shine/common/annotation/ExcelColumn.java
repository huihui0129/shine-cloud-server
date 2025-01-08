package com.shine.common.annotation;

import java.lang.annotation.*;

/**
 * @author huihui
 * @date 2025/1/8 18:31
 * @description ExcelColumn
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExcelColumn {

    /**
     * 对应Excel的列名
     */
    String value();

}
