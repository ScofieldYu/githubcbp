/*   
 * @(#)FinanceServiceImpl.java       2018年2月26日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPFinanceOrderMapper;
import com.blchina.cbp.model.CBPFinanceOrder;
import com.blchina.cbp.service.interfaces.FinanceService;

/** 
 * 此类功能描述    
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@Service("/FinanceService")
public class FinanceServiceImpl implements FinanceService{
@Autowired
private CBPFinanceOrderMapper cbpFinanceOrderMapper;
	@Override
	public int insertfinanceOrder(CBPFinanceOrder cbpFinanceOrder) {
		// TODO Auto-generated method stub
		return cbpFinanceOrderMapper.insertSelective(cbpFinanceOrder);
	}
	@Override
	public CBPFinanceOrder selectFinanceOrderByOrderId(
			Integer orderid) {
		// TODO Auto-generated method stub
		return cbpFinanceOrderMapper.selectFinanceOrderByOrderId(orderid);
	}
	@Override
	public int updateReserveTimeByOrderid(CBPFinanceOrder cbpFinanceOrder) {
		// TODO Auto-generated method stub
		return cbpFinanceOrderMapper.updateReserveTimeByOrderid(cbpFinanceOrder);
	}
   @Override
   public List<CBPFinanceOrder> selectFianceByOrderId(Integer orderid) {
	  return cbpFinanceOrderMapper.selectFianceByOrderId(orderid);
   }
	@Override
	public int updateFinanceOrder(CBPFinanceOrder cbpFinanceOrder) {
		// TODO Auto-generated method stub
		return cbpFinanceOrderMapper.updateReserveTimeByOrderid(cbpFinanceOrder);
	}
	@Override
	public List<CBPFinanceOrder> getFinanceByOrderId(Integer orderid) {
		// TODO Auto-generated method stub
		return cbpFinanceOrderMapper.getFinanceByOrderId(orderid);
	}

}
