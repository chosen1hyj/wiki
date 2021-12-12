package com.hyj.wiki.req;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

public class PageReq {

    @NotEmpty(message = "[页码] 不能为空")
    private Integer page;

    @NotEmpty(message = "[每页条数] 不能为空")
    @Max(value = 1000, message = "[每页条数]不能超过1000")
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageReq{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}