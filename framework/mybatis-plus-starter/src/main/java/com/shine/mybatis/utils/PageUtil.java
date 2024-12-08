package com.shine.mybatis.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.mybatis.request.PageQueryRequest;

/**
 * 分页工具
 */
public class PageUtil {

    /**
     * 创建Page
     *
     * @param request
     * @return
     */
    public static <T> Page<T> buildPage(PageQueryRequest request) {
        Page<T> page = new Page<>();
        page.setSize(request.getPageSize() == null ? 10 : request.getPageSize());
        page.setCurrent(request.getCurrent() == null ? 1 : request.getCurrent());
        return page;
    }

    /**
     * 创建page
     *
     * @param request
     * @return
     */
    public static <T> Page<T> createPage(PageQueryRequest request) {
        Long current = request.getCurrent();
        Integer pageSize = request.getPageSize();
        Page<T> page = new Page<>();
        if (current == null) {
            current = 1L;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        page.setCurrent(current);
        page.setSize(pageSize);
        return page;
    }

}
