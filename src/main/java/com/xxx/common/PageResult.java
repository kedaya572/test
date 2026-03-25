package com.xxx.common;

import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {
    private long total;
    private int page;
    private int pageSize;
    private List<T> list;

    public PageResult(long total, int page, int pageSize, List<T> list) {
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.list = list;
    }
}