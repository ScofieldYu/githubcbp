/*
 * @(#)ReturnTimeSetDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPReturnCarTime;
import com.blchina.cbp.model.CBPTimeVisible;

import java.util.List;

/**
 *预约时间设置DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class ReturnTimeSetDTO {
    private List<CBPReturnCarTime>  listReturmTime;
    private List<CBPTimeVisible> listVisibleTime;
    private Integer orderid;
    private Long phonenumber;
    private String customername;

    public Long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public List<CBPReturnCarTime> getListReturmTime() {
        return listReturmTime;
    }

    public void setListReturmTime(List<CBPReturnCarTime> listReturmTime) {
        this.listReturmTime = listReturmTime;
    }

    public List<CBPTimeVisible> getListVisibleTime() {
        return listVisibleTime;
    }

    public void setListVisibleTime(List<CBPTimeVisible> listVisibleTime) {
        this.listVisibleTime = listVisibleTime;
    }
}
