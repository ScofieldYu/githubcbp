package com.blchina.employee.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.employee.datamodel.CBPConstant;
import com.blchina.employee.datamodel.sap.SAPResponse;
import com.blchina.employee.datamodel.sap.SAPResult;
import com.blchina.employee.datamodel.signature.SignatureResult;
import com.blchina.employee.datamodel.weixin.WeixinResult;
@RestController 
@RequestMapping("/cbp")
public class TestController {

	@RequestMapping(value = "/getRecord")  
    public Object getRecord() { 
		System.out.println("-----------getRecord-----------------");
		SAPResult sapResult = new SAPResult();
		SAPResponse record = new SAPResponse();
		record.setSAP_ID("0000000370");
		record.setBL_ID("0000000370");
		record.setStatus("S");
		record.setDescription("审批状态更改成功");
        //return new Response().success(advertiserParam); 
		//return result;
		//sapResult.setRecords(record);
		return sapResult;
    } 
}
