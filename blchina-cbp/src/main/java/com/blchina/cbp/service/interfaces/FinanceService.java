/*   
 * @(#)FinanceService.java       2018年2月26日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPFinanceOrder;

/** 
 * 此类功能描述    
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
public interface FinanceService{

	int insertfinanceOrder(CBPFinanceOrder cbpFinanceOrder);

	CBPFinanceOrder selectFinanceOrderByOrderId(Integer orderid);

	int updateReserveTimeByOrderid(CBPFinanceOrder cbpFinanceOrder);

   List<CBPFinanceOrder> selectFianceByOrderId(Integer orderid);
	int updateFinanceOrder(CBPFinanceOrder cbpFinanceOrder);

	List<CBPFinanceOrder> getFinanceByOrderId(Integer orderid);

}
