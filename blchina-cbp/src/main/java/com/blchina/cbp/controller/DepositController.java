/*   
 * @(#)DepositController.java       2017年12月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.dto.DepositDTO;
import com.blchina.cbp.dto.ReceiptDTO;
import com.blchina.cbp.model.CBPDeposit;
import com.blchina.cbp.service.interfaces.DepositService;

/** 
 * 定金操作controller    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/deposit")
public class DepositController {
   
   @Autowired
   private DepositService depositService;
   
   @RequestMapping("/insertOrUpdateDeposit")
   public SAPResult insertOrUpdateDeposit(@RequestBody DepositDTO depositDTO){
	  int status = 0;
	  DepositDTO.Deposit deposit = null;
	  CBPDeposit cbpDeposit = null;
	  int size = depositDTO.getRecords().size();
	  SAPResult sapResult = new SAPResult();
	  List<SAPResponse> responseList = new ArrayList<SAPResponse>();
	  SAPResponse record = new SAPResponse();
	  if(depositDTO!=null){
		 deposit = depositDTO.getRecords().get(0);
		 try {
			status = depositService.insertOrUpdateDeposit(deposit);
			cbpDeposit = depositService.selectDepositBySapOrderId(deposit.getSaporderid());
			if(status != 0){
			   record.setSAP_ID(deposit.getSaporderid());
			   record.setBL_ID(cbpDeposit.getDepositid()+"");
			   record.setStatus(CBPConstant.SAPResponseStatusEnum.SUCCESS.getType());
			   record.setDescription(CBPConstant.MESSAGE_SUCCESS);
			   responseList.add(record);
			  	   sapResult.setRecords(responseList);
			  	   return sapResult;
			}
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  	}
	   		record.setSAP_ID(deposit.getSaporderid());
	   		record.setBL_ID(deposit.getSaporderid());
	   		record.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
	   		record.setDescription(CBPConstant.MESSAGE_NULL_PARAM);
	   		responseList.add(record);
	   		sapResult.setRecords(responseList);
	   		return sapResult;
   }
   
   
   
   
   
}
