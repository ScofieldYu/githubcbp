/*
 * @(#)InsuranceDTO.java       2018年2月5日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.insurance.dto;

import com.blchina.insurance.model.BDLInsurance;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 此类功能描述
 *
 * @author ZHUCHENGLONG
 * @since JDK 1.8
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsuranceDTO extends BDLInsurance{
    private Integer insurancecompanyid;

    public Integer getInsurancecompanyid() {
        return insurancecompanyid;
    }

    public void setInsurancecompanyid(Integer insurancecompanyid) {
        this.insurancecompanyid = insurancecompanyid;
    }
}
