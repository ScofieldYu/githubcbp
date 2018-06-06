/*
 * @(#)InsuranceLimitService.java       2018年2月6日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.insurance.service.interfaces;

import com.blchina.insurance.dto.InsuranceDTO;
import com.blchina.insurance.model.BDLInsuranceLimit;

import java.util.List;

/**
 * 保额操作
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface InsuranceLimitService {
    List<BDLInsuranceLimit> getInsuranceLimitList(InsuranceDTO insuranceDTO);

    List<BDLInsuranceLimit> getAllLimit();
}
