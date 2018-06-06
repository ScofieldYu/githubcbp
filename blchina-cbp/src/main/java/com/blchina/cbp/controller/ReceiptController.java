/*   
 * @(#)ReceiptController.java       2017年11月28日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.cbp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.dto.ReceiptDTO;
import com.blchina.cbp.model.CBPReceipt;
import com.blchina.cbp.service.interfaces.ReceiptService;
import com.blchina.common.util.string.StringUtil;


/**
 * 收据操作controller
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/receipt")
public class ReceiptController {

   @Autowired
   private ReceiptService receiptService;


   /**
    * 从SAP接受收据
    * 
    * @param receiptDTO
    * @return
    */
   @RequestMapping("/getReceipt")
   public SAPResult getReceipt(@RequestBody ReceiptDTO receiptDTO) {
	  int status = 0;
	  String uuid = null;
	  int size = receiptDTO.getRecords().size();
	  SAPResult sapResult = new SAPResult();
	  List<SAPResponse> responseList = new ArrayList<SAPResponse>();
	  for (int i = 0; i < size; i++) {
		 ReceiptDTO.Receipt receipt = receiptDTO.getRecords().get(i);
			//把收据传给合同管理系统	
			try {
			   uuid = receiptService.uploadReceipt(receipt);
			}catch (Exception e1) {
			   	SAPResponse record = new SAPResponse();
			   	record.setSAP_ID(receipt.getReceipttid());
		   		record.setBL_ID(receipt.getReceipttid());
		   		record.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
		   		record.setDescription(CBPConstant.MESSAGE_NULL_PARAM+e1.getMessage());
		   		responseList.add(record);
		   		sapResult.setRecords(responseList);
		   		e1.printStackTrace();
		  	  return sapResult;
			}
			//合同管理----->cbp  成功 保存或修改CBPReceipt
			CBPReceipt cbpReceipt = new CBPReceipt();
			
			if(!StringUtil.isNullorEmpty(receipt.getSaporderrid())){
			   cbpReceipt.setSaporderid(receipt.getSaporderrid());
			}
			cbpReceipt.setReceiptuuid(uuid);
			cbpReceipt.setReceipttype(receipt.getReceipttype());
			cbpReceipt.setReceiptsum(receipt.getReceiptsum());
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String time=sdf.format(date);
			cbpReceipt.setReceiptdate(time);
			try {
			   status = receiptService.insertOrUpdateReceipt(cbpReceipt);
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
			SAPResponse record = new SAPResponse();
			if(status != 0){
			   record.setSAP_ID(receipt.getReceipttid());
			   record.setBL_ID(receipt.getReceipttid());
			   record.setStatus(CBPConstant.SAPResponseStatusEnum.SUCCESS.getType());
			   record.setDescription(CBPConstant.MESSAGE_SUCCESS);
			   status = 0;
			}else{
		   		record.setSAP_ID(receipt.getReceipttid());
		   		record.setBL_ID(receipt.getReceipttid());
		   		record.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
		   		record.setDescription(CBPConstant.MESSAGE_NULL_PARAM);
		   	}
			responseList.add(record);
		 }
	  sapResult.setRecords(responseList);
	  return sapResult;
   }



}
