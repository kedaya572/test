package com.xxx.common.result;

import lombok.Data;

import java.util.List;

/**
 * 分页结果封装
 *
 * @param <T> 列表元素类型
 */
@Data
public class PageResult<T> {

    /** 总记录数 */
    private long total;
    /** 当前页码 */
    private int page;
    /** 每页条数 */
    private int pageSize;
    /** 当前页数据 */
    private List<T> list;

    public PageResult(long total, int page, int pageSize, List<T> list) {
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.list = list;
    }
}
