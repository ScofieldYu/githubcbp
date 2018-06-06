/*
 * @(#)CustomerInfoDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.model.BDLCustomer;
import com.blchina.cbp.model.BDLCustomerInfo;

import java.io.Serializable;
import java.util.List;

/**
 *客户详细DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class CustomerInfoDTO implements Serializable{
    private BDLCustomer customer;
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
