package com.shine.mybatis.request;

import lombok.Data;

/**
 * @author 辉辉 2023/3/2 18:02
 */
@Data
public class BaseQueryRequest extends PageQueryRequest {

    // 查询起始时间
    private String dateRangeBegin;

    // 查询结束时间
    private String dateRangeEnd;

}
