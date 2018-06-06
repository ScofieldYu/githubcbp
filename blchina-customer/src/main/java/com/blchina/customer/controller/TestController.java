package com.blchina.customer.controller;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.customer.dto.OpenIdDTO;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.customer.datamodel.CBPConstant;
import com.blchina.customer.datamodel.sap.SAPResponse;
import com.blchina.customer.datamodel.sap.SAPResult;
import com.blchina.customer.datamodel.signature.SignatureResult;
import com.blchina.customer.datamodel.weixin.WeixinResult;
import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.service.interfaces.BDLCustomerService;

import java.util.List;

@RestController
@RequestMapping("/cbp")
public class TestController {
   @Autowired
   protected BDLCustomerService bdlCustomerService;

	
	@RequestMapping(value = "/testCustomer") 
	public int testCustomer() {
		System.out.println("-----------testCustomer-----------------"); 
		BDLCustomer cbpCustomer = new BDLCustomer();
		//cbpCustomer.setOpenid("weixinid");
		cbpCustomer.setCustomerid(12344);
		cbpCustomer.setPhonenumber(new Long("15910784374"));
		int status = bdlCustomerService.insertCustomer(cbpCustomer);
		return status;
	}	
	
	@RequestMapping(value = "/testCustomer3") 
	public Object testCustomer3() {  
		System.out.println("-----------testCustomer3-----------------"); 

		BDLCustomer cbpCustomer = bdlCustomerService.findCustomerByPrimaryKey(12344);
		WeixinResult result = new WeixinResult();
		SignatureResult Records = new SignatureResult(); 
		Records.setData(cbpCustomer);
        result.setCode(CBPConstant.CODE_SUCCESS);
        result.setMessage(CBPConstant.MESSAGE_SUCCESS);
        result.setData(cbpCustomer);
        System.out.println(CBPConstant.CAccountTypeEnum.PRIVATE.getType());
        System.out.println(Records); 
        return Records;
	}	

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
		sapResult.setRecords(record);
		return sapResult;
    } 
    @RequestMapping("/synOrderOpenid")
	public void synOrderOpenid(){
		List<OpenIdDTO> list=bdlCustomerService.findOpenId();
		JSONArray array=JSONArray.fromObject(list);
		try {
			HttpUtil.postbody("http://localhost:8080/cbp/order/synOrderOpenid",array.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
