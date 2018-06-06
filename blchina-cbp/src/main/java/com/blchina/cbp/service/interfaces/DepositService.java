/*
 * @(#)DepositService.java       2017年11月28日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;


import java.util.List;

import com.blchina.cbp.dto.DepositDTO;
import com.blchina.cbp.model.CBPDeposit;

/**
 * 垫付证明Service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface DepositService {
    int  saveDeposit(CBPDeposit cbpDeposit);

   int insertOrUpdateDeposit(DepositDTO.Deposit deposit);

   CBPDeposit selectDepositBySapOrderId(String saporderid);

   CBPDeposit selectDepositByOrderId(String orderId);

    int insertOrUpdateByOrder(String  saporderid , String earnest);
}
