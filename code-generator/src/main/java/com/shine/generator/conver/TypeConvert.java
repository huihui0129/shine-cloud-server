package com.shine.generator.conver;

import com.shine.generator.entity.Column;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huihui
 * @date 2024/11/24 15:46
 * @description TypeConvert
 */
@Component
public class TypeConvert {

    public void convert(List<Column> columnList) {
        columnList.forEach(item -> {
            switch (item.getDataType()) {
                case "bigint":
                    item.setJavaType("Long");
                    break;
                case "varchar":
                    item.setJavaType("String");
                    break;
                case "tinyint":
                    item.setJavaType("Integer");
                    break;
                case "datetime":
                    item.setJavaType("LocalDateTime");
                    break;
                case "bit":
                    item.setJavaType("Boolean");
                case "int":
                    item.setJavaType("Integer");
                    break;
                default:
                    throw new NullPointerException("请维护类型：" + item.getDataType());
            }
        });
    }

}
