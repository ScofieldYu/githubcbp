/*
 * @(#)SapCustomerDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *sap客户DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class SapCustomerDTO {
    @JsonProperty
    private List<CustmerDTO> Records;

    public List<CustmerDTO> getRecords() {
        return Records;
    }

    public void setRecords(List<CustmerDTO> records) {
        Records = records;
    }
}
