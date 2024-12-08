package com.shine.mybatis.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 辉辉 2023/3/2 17:57
 */
@Data
public class PageQueryRequest implements Serializable {

    // 当前页数
    private Long current;

    // 页面条数
    private Integer pageSize;

}
