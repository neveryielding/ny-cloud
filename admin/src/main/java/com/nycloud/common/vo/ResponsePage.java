package com.nycloud.common.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import java.util.List;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/9 0009
 * @modified By:
 * @version: 1.0
 **/
@Data
public class ResponsePage<T> {
    private int page;
    private int pages;
    private int size;
    private long total;
    private List<T> data;

    public ResponsePage(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        this.pages = pageInfo.getPages();
        this.page = pageInfo.getNextPage();
        this.size = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
        this.data = pageInfo.getList();
    }
}
