/*
 * @(#)InsuranceInfoDTO.java       2018年2月5日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.insurance.dto;

import com.blchina.insurance.model.BDLInsurance;
import com.blchina.insurance.model.BDLInsuranceCompany;

import java.util.List;

/**
 * 此类功能描述
 *
 * @author ZHUCHENGLONG
 * @since JDK 1.8
 */
public class InsuranceInfoDTO {
    private List<BDLInsuranceCompany> listCompany;
    private InsuranceLimitDTO   threeInsuranceList;
    private InsuranceLimitDTO   scratchList;
    private InsuranceLimitDTO   carUserList;
    private List<BDLInsurance>   otherList;

    public List<BDLInsuranceCompany> getListCompany() {
        return listCompany;
    }

    public void setListCompany(List<BDLInsuranceCompany> listCompany) {
        this.listCompany = listCompany;
    }

    public InsuranceLimitDTO getScratchList() {
        return scratchList;
    }

    public InsuranceLimitDTO getCarUserList() {
        return carUserList;
    }

    public void setCarUserList(InsuranceLimitDTO carUserList) {
        this.carUserList = carUserList;
    }

    public void setScratchList(InsuranceLimitDTO scratchList) {
        this.scratchList = scratchList;
    }

    public InsuranceLimitDTO getThreeInsuranceList() {
        return threeInsuranceList;
    }

    public void setThreeInsuranceList(InsuranceLimitDTO threeInsuranceList) {
        this.threeInsuranceList = threeInsuranceList;
    }

    public List<BDLInsurance> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<BDLInsurance> otherList) {
        this.otherList = otherList;
    }
}
