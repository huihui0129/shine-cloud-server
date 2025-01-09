package com.shine.common.utils;

import cn.hutool.poi.excel.ExcelReader;
import com.shine.common.annotation.ExcelColumn;
import com.shine.common.exception.BaseException;
import com.shine.common.status.ResponseStatus;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huihui
 * @date 2025/1/8 18:33
 * @description ExcelUtil
 */
public class ExcelUtil {

    public static <T> List<T> read(InputStream is, Class<T> modelClass) {
        ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(is);
        List<List<Object>> rowList = reader.read();
        List<T> resultList = new ArrayList<>();
        // 读取标题以及索引
        Map<String, Integer> headerMap = new HashMap<>();
        List<Object> headerList = rowList.get(0);
        Field[] fields = modelClass.getFields();
        for (Field field : fields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn != null && StringUtils.isNotBlank(excelColumn.value())) {
                for (int i = 0; i < headerList.size(); i++) {
                    Object item = headerList.get(i);
                    if (StringUtils.equals((String ) item, excelColumn.value())) {
                        headerMap.put(excelColumn.value(), i);
                        break;
                    }
                }
            }
        }
        if (headerMap.keySet().isEmpty()) {
            throw new BaseException(ResponseStatus.ERROR, "读取Excel文件请先将实体类的属性使用ExcelColumn注解");
        }
        // 读取数据  不读头
        for (int i = 1; i < rowList.size(); i++) {
            List<Object> dataList = rowList.get(i);
            try {
                T model = modelClass.getDeclaredConstructor().newInstance();
                for (Field field : fields) {
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    if (excelColumn != null) {
                        String columnName = excelColumn.value();
                        if (StringUtils.isNotBlank(columnName)) {
                            Object value = dataList.get(headerMap.get(columnName));
                            field.set(model, value);
                        }
                    }
                }
            } catch (Exception e) {
                throw new BaseException(ResponseStatus.ERROR, "读取Excel文件名出错啦");
            }
        }
        return resultList;
    }

}
