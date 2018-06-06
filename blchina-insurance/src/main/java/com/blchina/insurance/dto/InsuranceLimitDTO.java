/*
 * @(#)InsuranceLimitDTO.java       2018年2月2日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.insurance.dto;

import com.blchina.insurance.model.BDLInsuranceLimit;

import java.util.List;

/**
 * 保险额度DTO
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class InsuranceLimitDTO {
    private Integer insuranceid;

    private String insurancename;

    private String insurancetype;

    private List<BDLInsuranceLimit>  limitList;

    public Integer getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(Integer insuranceid) {
        this.insuranceid = insuranceid;
    }

    public String getInsurancename() {
        return insurancename;
    }

    public void setInsurancename(String insurancename) {
        this.insurancename = insurancename;
    }

    public String getInsurancetype() {
        return insurancetype;
    }

    public void setInsurancetype(String insurancetype) {
        this.insurancetype = insurancetype;
    }

    public List<BDLInsuranceLimit> getLimitList() {
        return limitList;
    }

    public void setLimitList(List<BDLInsuranceLimit> limitList) {
        this.limitList = limitList;
    }
}
