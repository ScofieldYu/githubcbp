/*
 * @(#)DepositServiceImpl.java       2017年11月28日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPDepositMapper;
import com.blchina.cbp.dao.CBPOrderMapper;
import com.blchina.cbp.dto.DepositDTO;
import com.blchina.cbp.model.CBPDeposit;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.DepositService;
import com.blchina.common.util.string.StringUtil;

/**
 * 定金操作Service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("DepositService")
public class DepositServiceImpl implements DepositService {
    @Autowired
    CBPDepositMapper cbpDepositMapper;
    @Autowired
    private CBPOrderMapper cbpOrderMapper;
    @Override
    public int saveDeposit(CBPDeposit cbpDeposit) {
        return cbpDepositMapper.insert(cbpDeposit);
    }
   
   @Override
   public int insertOrUpdateDeposit(DepositDTO.Deposit deposit) {
	  int status = 0;
	  if(!StringUtil.isNullorEmpty(deposit.getSaporderid())){
		 CBPDeposit cbpDeposit = cbpDepositMapper.selectBySapOrderId(deposit.getSaporderid());
		 CBPDeposit cbpDeposit2 = depositDTO2CBPDeposit(deposit);
		 if(cbpDeposit!=null){
			 cbpDeposit2.setDepositid(cbpDeposit.getDepositid());
			status = cbpDepositMapper.updateByPrimaryKeySelective(cbpDeposit2);
		 }else{
			status = cbpDepositMapper.insertSelective(cbpDeposit2);
		 }
	  }
	  return status;
   }
   
   private static CBPDeposit depositDTO2CBPDeposit(DepositDTO.Deposit deposit){
	  CBPDeposit cbpDeposit = new CBPDeposit();
	  cbpDeposit.setDepositstatus(deposit.getDepositstatus());
	  cbpDeposit.setDepositfirst(deposit.getDepositsum());
	  cbpDeposit.setDepositsum(null);
	  if(!StringUtil.isNullorEmpty(deposit.getBelnr())){
		 cbpDeposit.setBelnr(Integer.parseInt(deposit.getBelnr()));
	  }
	  if(!StringUtil.isNullorEmpty(deposit.getBukrs())){
		 cbpDeposit.setBukrs(Integer.parseInt(deposit.getBukrs()));
	  }
	  if(!StringUtil.isNullorEmpty(deposit.getGjahr())){
		 cbpDeposit.setGjahr(Integer.parseInt(deposit.getGjahr()));
	  }
	  cbpDeposit.setSaporderid(deposit.getSaporderid());
	  return cbpDeposit;
   }

   @Override
   public CBPDeposit selectDepositBySapOrderId(String saporderid) {
	  return cbpDepositMapper.selectBySapOrderId(saporderid);
   }

   @Override
   public CBPDeposit selectDepositByOrderId(String orderId) {
	   return cbpDepositMapper.selectByOrderId(Integer.parseInt(orderId));
   }

	@Override
	public int insertOrUpdateByOrder(String  saporderid, String earnest) {
		int i=0;
		CBPDeposit cbpDeposit = cbpDepositMapper.selectBySapOrderId(saporderid);
		if(cbpDeposit==null){
			cbpDeposit=new CBPDeposit();
			cbpDeposit.setSaporderid(saporderid);
			cbpDeposit.setDepositsum(earnest);
			i=cbpDepositMapper.insert(cbpDeposit);
		}else {
			cbpDeposit.setDepositsum(earnest);
			i=cbpDepositMapper.updateByPrimaryKeySelective(cbpDeposit);
		}
		return i;
	}
}
