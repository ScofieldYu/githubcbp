/*   
 * @(#)InsuranceOrderService.java       2018年2月2日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.dto.InsuranceOrderInfoDTO;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPInsuranceOrder;
import com.blchina.cbp.model.CBPOrder;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface InsuranceOrderService {

   List<CBPInsuranceOrder> selectInsuranceOrderListByOrderId(Integer integer);

   void saveOrUpdateInsuranceOrder(List<CBPInsuranceOrder> list);

   Boolean insuranceConfirmReceipt(BDLCard bdlCard, CBPOrder order);

   CBPInsuranceOrder getInsuranceOrder(Integer orderid);

   void saveOrUpdateInsuranceInfo(InsuranceOrderInfoDTO infoDTO);
}
