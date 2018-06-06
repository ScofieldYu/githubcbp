/*   
 * @(#)FinanceService.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.finance.service.interfaces;

import java.util.List;

import com.blchina.finance.model.BDLBank;
import com.blchina.finance.model.BDLPeriod;

/** 
 * 此类功能描述  金融分期  
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
public interface FinanceService {

	List<BDLBank> selectPeriod();

	int insertPeriod(BDLPeriod bdlPeriod);

	List<BDLBank> getAllPeriodList();


	
}
