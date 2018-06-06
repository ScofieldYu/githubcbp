/*   
 * @(#)FinanceServiceImpl.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.finance.dao.BDLBankMapper;
import com.blchina.finance.dao.BDLPeriodMapper;
import com.blchina.finance.dao.FinanceMapper;
import com.blchina.finance.model.BDLBank;
import com.blchina.finance.model.BDLPeriod;
import com.blchina.finance.service.interfaces.FinanceService;

/** 
 * 此类功能描述    金融分期
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@Service("/FinanceService")
public class FinanceServiceImpl implements FinanceService{
	@Autowired
	private BDLBankMapper bankMapper;
	@Autowired
	private BDLPeriodMapper periodMapper;
	
	@Override
	public List<BDLBank> selectPeriod() {
		// TODO Auto-generated method stub
		return bankMapper.selectAllBank();
	}

	@Override
	public int insertPeriod(BDLPeriod bdlPeriod) {
		// TODO Auto-generated method stub
		return periodMapper.insert(bdlPeriod);
	}

	@Override
	public List<BDLBank> getAllPeriodList() {
		// TODO Auto-generated method stub
		List<BDLBank> selectAllBank = bankMapper.selectAllBank();
		for (BDLBank bdlBank : selectAllBank) {
			Integer bankid = bdlBank.getBankid();
			List<BDLPeriod> bdlPeriodList = periodMapper.getPeriodListByBankId(bankid);
			bdlBank.setBdlPeriodList(bdlPeriodList);
		}
		return selectAllBank;
	}

}
