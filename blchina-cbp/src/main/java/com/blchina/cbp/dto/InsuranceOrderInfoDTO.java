/*
 * @(#)InsuranceOrderInfoDTO.java       2018年2月7日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPInsuranceExt;
import com.blchina.cbp.model.CBPInsuranceOrder;

import java.util.List;

/**
 * 订单保险DTO
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class InsuranceOrderInfoDTO extends CBPInsuranceOrder{
    List<CBPInsuranceExt> extList;

    public List<CBPInsuranceExt> getExtList() {
        return extList;
    }

    public void setExtList(List<CBPInsuranceExt> extList) {
        this.extList = extList;
    }
}
