/*   
 * @(#)FinanceMapper.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.finance.dao;

import java.util.List;

import com.blchina.finance.model.BDLBank;

/** 
 * 此类功能描述    金融分期
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
public interface FinanceMapper {

	List<BDLBank> selectPeriod();

}
