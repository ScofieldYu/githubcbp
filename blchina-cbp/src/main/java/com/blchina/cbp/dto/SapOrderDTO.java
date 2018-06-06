/*
 * @(#)SapOrderDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
*订单DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class SapOrderDTO {
    @JsonProperty
    private  OrderDTO Records;
    @JsonIgnore
    public OrderDTO getRecords() {
        return Records;
    }
    @JsonIgnore
    public void setRecords(OrderDTO records) {
        Records = records;
    }
}
