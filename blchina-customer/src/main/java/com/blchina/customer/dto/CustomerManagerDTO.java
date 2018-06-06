/*
 * @(#)CustomerManagerDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.dto;

import java.util.List;


/**
 * 客户同步DTO
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class CustomerManagerDTO {
    private String timestamp ;
    private List<CustomerManagerDetailDTO> userInfoList;
    private String sign;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<CustomerManagerDetailDTO> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<CustomerManagerDetailDTO> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
