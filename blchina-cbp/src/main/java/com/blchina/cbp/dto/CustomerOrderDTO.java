/*
 * @(#)CustomerOrderDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPOrder;

import java.util.List;

/**
 *客户订单DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class CustomerOrderDTO {
    private String customername;
    private String phonenumber;
    private List<CBPOrder> orderlist;

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public List<CBPOrder> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<CBPOrder> orderlist) {
        this.orderlist = orderlist;
    }
}
