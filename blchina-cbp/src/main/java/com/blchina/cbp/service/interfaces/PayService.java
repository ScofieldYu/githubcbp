/*   
 * @(#)PayService.java       2017年11月25日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.model.CBPPay;

/** 
 * 支付结果PayService
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface PayService {

   CBPPay selectPayById(Integer orderId);

   void updatePay(CBPPay cbpPay1);

   CBPPay selectPayByOrderId(Integer orderid);

   int insertCBPPay(CBPPay cbpPay);

   int insertCBPPayWhenWXPayError(CBPPay pay);
   
}
