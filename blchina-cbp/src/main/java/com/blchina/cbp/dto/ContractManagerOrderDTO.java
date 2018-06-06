/*
 * @(#)ContractManagerOrderDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import java.util.List;

/**
 * 订单上传DTO
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class ContractManagerOrderDTO {
    private String timestamp ;
    private List<ContractManagerOrderDetailDTO> orderInfoList;
    private String sign;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List orderInfoList) {
        this.orderInfoList = orderInfoList;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
