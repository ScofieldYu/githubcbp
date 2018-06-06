/*   
 * @(#)ContractItemService.java       2017年12月1日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.model.CBPContractItem;

/** 
 * 合同item操作service    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface ContractItemService {

   String selectContractItem(String contractId);

   int updateContractItem(CBPContractItem cbpContractItem);
   int saveItem(CBPContractItem cbpContractItem);

   CBPContractItem selectContractItemByContractIdAndContractStatus(
		 String contract_id);
}
