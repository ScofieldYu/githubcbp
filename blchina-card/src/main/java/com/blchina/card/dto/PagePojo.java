/*
 * @(#)PagePojo.java       2018年1月12日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.card.dto;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class PagePojo<T> implements Serializable{

    public PagePojo(List<T> list){
        PageInfo<T> pageInfo = new PageInfo(list);
        this.currentPage = pageInfo.getPageNum();
        this.totalPage = pageInfo.getPages();
        this.datas = pageInfo.getList();
        this.totalRecord = pageInfo.getTotal();
    }

    private int currentPage;

    private int pageSize;

    private int totalPage;
    private long totalRecord;

    private List<T> datas;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
