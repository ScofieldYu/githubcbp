/*   
 * @(#)FourCustomerService.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.CardDTO;
import com.blchina.cbp.dto.FourCustomerDTO;
import com.blchina.cbp.model.CBPFourCustomer;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface FourCustomerService {

   List<CBPFourCustomer> selectFourCustomerListByOrderId(Integer orderid);

   WeixinResult insertOrUpdateFourCustomer(FourCustomerDTO dto);

   CBPFourCustomer selectFourCustomerByOrderIdAndType(Integer orderid);

   List<CBPFourCustomer> searchFourCustomer(CardDTO cd);

}
