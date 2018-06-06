package com.blchina.cbp.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.WeixinConUtil;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.common.util.weixin.WeixinUtil;

@RestController 
@RequestMapping("/wx")
public class WeiXinController {
   @Autowired
   protected Properties systemConfig;
//   @Autowired
//   protected CBPCustomerService cbpCustomerService;
//
//	
//	@RequestMapping(value = "/testCustomer") 
//	public int testCustomer() { 
//		System.out.println("-----------testCustomer-----------------"); 
//		CBPCustomer cbpCustomer = new CBPCustomer();
//		cbpCustomer.setOpenid("weixinid");
//		cbpCustomer.setCustomerid(12344);
//		cbpCustomer.setPhonenumber(new Long("15910784374"));
//		int status = cbpCustomerService.insertCustomer(cbpCustomer);
//		return status;
//	}	
//	
//	@RequestMapping(value = "/testCustomer3") 
//	public Object testCustomer3() {  
//		System.out.println("-----------testCustomer3-----------------"); 
//
//		CBPCustomer cbpCustomer = cbpCustomerService.findCustomerByPrimaryKey(12344);
//		WeixinResult result = new WeixinResult();
//		SignatureResult Records = new SignatureResult(); 
//		Records.setData(cbpCustomer);
//        result.setCode(CBPConstant.CODE_SUCCESS);
//        result.setMessage(CBPConstant.MESSAGE_SUCCESS);
//        result.setData(cbpCustomer);
//        System.out.println(CBPConstant.CAccountTypeEnum.PRIVATE.getType());
//        System.out.println(Records); 
//        return Records;
//	}
   

//	@RequestMapping(value = "/token")  
//    public Object authToken(WXTokenDTO data) { 	
//		   String signature = data.getSignature();
//		    String timestamp = data.getTimestamp();
//		    String nonce = data.getNonce();
//		    String echostr = data.getEchostr();
//		    String systemConfigq = systemConfig.getProperty("weixin.token");   
//		    System.out.println("---authToken---"+" signature: "+signature+" timestamp: "+timestamp
//		    	  + " nonce:"+ nonce +" systemConfigq: "+systemConfigq);
//		    boolean isSuccess = WeixinUtil.checkSignature(signature, timestamp, nonce, systemConfigq);
//		    if(isSuccess){
//		       return echostr;
//		    }
//		   return "error";
//    } 
	@RequestMapping(value = "/token",method = RequestMethod.GET)  
    public void get(HttpServletRequest request, HttpServletResponse response) {  
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
        String systemConfigq = systemConfig.getProperty("weixin.token");
        System.out.println("---authToken---"+" signature: "+signature+" timestamp: "+timestamp
	    	  + " nonce:"+ nonce +" systemConfigq: "+systemConfigq);
        PrintWriter out = null;  
        try {  
            out = response.getWriter();  
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败  
            if (WeixinUtil.checkSignature(signature, timestamp, nonce,systemConfigq)) {  
                out.print(echostr);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            out.close();  
            out = null;  
        }  
    }  
	
	
	
	@RequestMapping(value = "/token",method = RequestMethod.POST)
	public String click(HttpServletRequest request, HttpServletResponse response){
	   
	   Map<String, String> requestMap = null;
	  try {
		 requestMap = WeixinConUtil.parseXml(request);
	  }
	  catch (Exception e) {
		e.printStackTrace();
	  }
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		System.out.println("获取的Openid为-----------"+fromUserName+"--------------");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		// 消息类型
		String msgType = requestMap.get("MsgType"); 
	   
	   return "test.html";
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


}
