/*   
 * @(#)PayServiceImpl.java       2017年11月25日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPDepositMapper;
import com.blchina.cbp.dao.CBPPayMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.model.CBPDeposit;
import com.blchina.cbp.model.CBPPay;
import com.blchina.cbp.service.interfaces.PayService;

/** 
 * 支付结果操作PayServiceImpl    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("PayService")
public class PayServiceImpl implements PayService {
   
   @Autowired
   private CBPPayMapper cbpPayMapper;
   @Autowired
   private CBPDepositMapper cbpDepositMapper;
   @Override
   public CBPPay selectPayById(Integer orderId) {
	  return cbpPayMapper.selectPayByOrderId(orderId);
   }

   @Override
   public void updatePay(CBPPay cbpPay1) {
	  cbpPayMapper.updateByPrimaryKey(cbpPay1);	  
   }

   @Override
   public CBPPay selectPayByOrderId(Integer orderid) {
	  return cbpPayMapper.selectPayByOrderId(orderid);
   }

   @Override
   public int insertCBPPay(CBPPay cbpPay) {
	  int status = 0;
	  if(cbpPay!=null){
		 CBPDeposit cbpDeposit = cbpDepositMapper.selectByOrderId(cbpPay.getOrderid());		 
		 if(cbpDeposit!=null){
			cbpDeposit.setDepositstatus(CBPConstant.DepositStatusEnum.PAYSUCCESS.getType());
			int a = cbpDepositMapper.updateByPrimaryKeySelective(cbpDeposit);
			int i = cbpPayMapper.insertSelective(cbpPay);
			if(a!=0&&i!=0){
			   status = 1;
			}
		 }
	  }
	  return status;
   }

   @Override
   public int insertCBPPayWhenWXPayError(CBPPay pay) {
	  return cbpPayMapper.insertSelective(pay);
   }
}
