package com.blchina.common.util.pdf;

import java.util.List;

public class PDFWater {
    private int pageNum;
    private List<PdfXY> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<PdfXY> getList() {
        return list;
    }

    public void setList(List<PdfXY> list) {
        this.list = list;
    }
}