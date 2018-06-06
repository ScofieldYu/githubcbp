/*
 * @(#)ReturnCarTimeDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPReturnCarTime;

import java.util.List;

/**
 *ReturnCarTimeDTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class ReturnCarTimeDTO {
    private List<CBPReturnCarTime> returnCarlist;
    private List<String> noVisibleList;

    public List<CBPReturnCarTime> getReturnCarlist() {
        return returnCarlist;
    }

    public void setReturnCarlist(List<CBPReturnCarTime> returnCarlist) {
        this.returnCarlist = returnCarlist;
    }

    public List<String> getNoVisibleList() {
        return noVisibleList;
    }

    public void setNoVisibleList(List<String> noVisibleList) {
        this.noVisibleList = noVisibleList;
    }
}
