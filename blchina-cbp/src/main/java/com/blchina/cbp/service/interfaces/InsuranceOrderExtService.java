/*
 * @(#)InsuranceOrderExtService.java       2018年2月2日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.dto.InsuranceOrderInfoDTO;
import com.blchina.cbp.model.CBPInsuranceExt;

import java.util.List;

/**
 * 保险扩展Service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface InsuranceOrderExtService {
    List<CBPInsuranceExt> getOrderExtInsurance(Integer orderid);

    void saveOrUpdateInsuranceExt(InsuranceOrderInfoDTO infoDTO);
}
