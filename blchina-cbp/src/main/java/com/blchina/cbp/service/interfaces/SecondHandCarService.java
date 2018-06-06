/*   
 * @(#)SecondHandCarService.java       2018年2月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.model.CBPSecondHandCarOrder;

/** 
 * 此类功能描述    
 *
 * @author Admin 
 * @since JDK 1.7 
 */
public interface SecondHandCarService {

	int saveSecondHandCarOrder(CBPSecondHandCarOrder record);

   List<CBPSecondHandCarOrder> selectSecondHandCarByOrderId(Integer orderid);

   int updateReserveTime(CBPSecondHandCarOrder cshco);

   int updateSecondHandCarOrder(CBPSecondHandCarOrder cbpSecondHandCarOrder);

   int updatePrice(CBPSecondHandCarOrder cshco);

}
