/*   
 * @(#)ContractItemServiceImpl.java       2017年12月1日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPContractItemMapper;
import com.blchina.cbp.model.CBPContractItem;
import com.blchina.cbp.service.interfaces.ContractItemService;

/** 
 * 合同item操作service    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("ContractItemService")
public class ContractItemServiceImpl implements ContractItemService {
   
   @Autowired
   private CBPContractItemMapper cbpContractItemMapper;
   
   @Override
   public String selectContractItem(String contractId) {
	  String contractUUID = "";
	  if(contractId!=null){
		 CBPContractItem cbpContractItem = cbpContractItemMapper.selectByContractId(Integer.parseInt(contractId));
		 contractUUID = cbpContractItem.getContractuuid();
	  }
	  return contractUUID;
   }

   @Override
   public int updateContractItem(CBPContractItem cbpContractItem) {

	  
	  return 0;
   }

    @Override
    public int saveItem(CBPContractItem cbpContractItem) {
        return cbpContractItemMapper.insertSelective(cbpContractItem);
    }

   @Override
   public CBPContractItem selectContractItemByContractIdAndContractStatus(
		 String contract_id) {
	  return cbpContractItemMapper.selectContractItemByContractIdAndContractStatus(Integer.valueOf(contract_id));
   }


}
