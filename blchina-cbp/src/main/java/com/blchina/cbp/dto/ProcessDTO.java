/*
 * @(#)processDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

/**
 *流程DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class ProcessDTO {
    private Integer orderId;
    private Integer boutiqueId;
    private Integer secondCarId;
    private Integer insuranceId;
    private Integer checkCarNumId;
    private Integer financeId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBoutiqueId() {
        return boutiqueId;
    }

    public void setBoutiqueId(Integer boutiqueId) {
        this.boutiqueId = boutiqueId;
    }

    public Integer getSecondCarId() {
        return secondCarId;
    }

    public void setSecondCarId(Integer secondCarId) {
        this.secondCarId = secondCarId;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    public Integer getCheckCarNumId() {
        return checkCarNumId;
    }

    public void setCheckCarNumId(Integer checkCarNumId) {
        this.checkCarNumId = checkCarNumId;
    }

    public Integer getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Integer financeId) {
        this.financeId = financeId;
    }
}
