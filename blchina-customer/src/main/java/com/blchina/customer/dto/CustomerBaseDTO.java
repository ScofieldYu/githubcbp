/*
 * @(#)CustomerBaseDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.dto;

import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.model.BDLCustomerInfo;

import java.util.List;

/**
 *客户DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class CustomerBaseDTO {
    private BDLCustomer  customer;
    private List<BDLCustomerInfo> listinfo;

    public BDLCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(BDLCustomer customer) {
        this.customer = customer;
    }

    public List<BDLCustomerInfo> getListinfo() {
        return listinfo;
    }

    public void setListinfo(List<BDLCustomerInfo> listinfo) {
        this.listinfo = listinfo;
    }
}
