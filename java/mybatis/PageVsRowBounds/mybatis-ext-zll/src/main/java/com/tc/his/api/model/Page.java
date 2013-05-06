package com.tc.his.api.model;

/**
 * 分页对象
 *
 * @author tianzhonghong
 *
 */
public class Page {

    // 分页查询开始记录位置
    private int begin;

    // 分页查询结束位置
    private int end;

    // 每页显示记录数
    private int pageSize;

    // 查询结果总记录数
    private int totalRecords;

    // 当前页码
    private int pageIndex;

    // 总页数
    private int totalPages;

    public Page() {
    }

    /**
     * 构造函数
     *
     * @param begin
     * @param pageSize
     */
    public Page(int begin, int pageSize) {
        this.begin = begin;
        this.pageSize = pageSize;
        this.end = this.begin + this.pageSize;
        this.pageIndex = (int) Math.floor((this.begin * 1.0d) / this.pageSize) + 1;
    }

    public Page(int begin, int pageSize, int totalRecords) {
        this(begin, pageSize);
        this.totalRecords = totalRecords;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setBegin(int begin) {
        this.begin = begin;
        if (this.pageSize != 0) {
            this.pageIndex = (int) Math.floor((this.begin * 1.0d) / this.pageSize) + 1;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        if (this.begin != 0) {
            this.pageIndex = (int) Math.floor((this.begin * 1.0d) / this.pageSize) + 1;
        }
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int count) {
        this.totalRecords = count;
        this.totalPages = (int) Math.floor((this.totalRecords * 1.0d) / this.pageSize);
        if (this.totalRecords % this.pageSize != 0) {
            this.totalPages++;
        }
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPages() {
        if (totalPages == 0) {
            return 1;
        }
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
