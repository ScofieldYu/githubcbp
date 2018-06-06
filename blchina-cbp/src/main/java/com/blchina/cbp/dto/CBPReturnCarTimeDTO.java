/*
 * @(#)CBPReturnCarTimeDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPReturnCarTime;

/**
 *预约时间DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class CBPReturnCarTimeDTO extends CBPReturnCarTime {
    private String  type;
    private Integer orderid;
    private String endDay;
    private Long phonenumber;
    private String customername;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Integer getOrderid() {
        return orderid;
    }

    @Override
    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
}
