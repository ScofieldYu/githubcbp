/*   
 * @(#)WXPayController.java       2018年1月30日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.PayUpload;
import com.blchina.cbp.datamodel.WeChatConstant;
import com.blchina.cbp.datamodel.WeixinConUtil;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.DocumentModelDTO;
import com.blchina.cbp.dto.OrderDTO;
import com.blchina.cbp.dto.ProcessDTO;
import com.blchina.cbp.dto.WXPayDTO;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPDeposit;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPPay;
import com.blchina.cbp.service.interfaces.CardService;
import com.blchina.cbp.service.interfaces.ContractManageService;
import com.blchina.cbp.service.interfaces.DepositService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.PayService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.ip.IPUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.blchina.common.util.weixin.WXPayUtil;

import net.sf.json.JSONObject;

/** 
 * 微信支付通用接口    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/wXPay")
public class WXPayController {
   
   
   @Autowired
   protected Properties systemConfig;
   @Autowired
   private PayService payService;
   @Autowired
   private DepositService depositService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private WxService wxService;
   @Autowired
   private CardService cardService;
   @Autowired
   private ContractManageService contractManageService;
   private static Logger log = Logger.getLogger(PayController.class);
   /**
    * 请求生成支付订单
    *
    * @param wxPayDTO
    * @return
    */
   @RequestMapping("/getPayOrder")
   public WeixinResult getPayOrder(@RequestBody WXPayDTO wxPayDTO, HttpServletRequest request) {
       WeixinResult res = new WeixinResult();
       System.out.println("服务器IP"+IPUtil.getIpAddr(request));
       res.setCode(CBPConstant.CODE_NULL_PARAM);
       res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
       Map<String, String> map = null;
       try {
       if(wxPayDTO != null){
    	  //处理参数
    	  if(StringUtil.isNullorEmpty(wxPayDTO.getBrandId())
    			|| StringUtil.isNullorEmpty(wxPayDTO.getNotifyUrl())
    			|| StringUtil.isNullorEmpty(wxPayDTO.getOpenId())
    			|| StringUtil.isNullorEmpty(wxPayDTO.getOrderId())
    			|| StringUtil.isNullorEmpty(wxPayDTO.getPaySum())
    			|| StringUtil.isNullorEmpty(wxPayDTO.getUniqueCode())){
    		 res.setCode(CBPConstant.CODE_NULL_PARAM);
    		 res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
    		 return  res;
    	  }
 		 String response = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getEmployeeBrandId", "{\"brandid\":" + wxPayDTO.getBrandId() + "}");
 		 System.out.println(response);
 		 JSONObject jsonObject1 = JSONObject.fromObject(response);
 		 String appid = (String) jsonObject1.get("appid");
 		 String appsecret = (String) jsonObject1.get("appsecret");
 		 String merchantnum = (String) jsonObject1.get("merchantnum");
 		 String merchantapikey = (String) jsonObject1.get("merchantapikey");
 		 System.out.println("appid = "+appid+"---appsecret = "+appsecret+"---merchantnum = "+merchantnum+"----merchantapikey"+merchantapikey);
    	  String outputStr = getOutTrade(wxPayDTO, request,appid,appsecret,merchantnum,merchantapikey);
    	  map = WeixinConUtil.httpRequest(WeChatConstant.UNIFIED_ORDER_URL, "POST", outputStr);
    	  System.out.println("微信返回(微信支付通用接口)----"+map);
    	  if(WeChatConstant.CALLBACK_FAIL.equalsIgnoreCase(map.get("return_code"))){
    			res.setCode(CBPConstant.CODE_NULL_PARAM);
    			res.setMessage(map.get("return_msg"));
    			return  res;
    	  }
 		 if(map.get("err_code")!=null){
 			res.setCode(CBPConstant.CODE_NULL_PARAM);
 			res.setMessage(map.get("err_code_des"));
 			return  res;
 		 }
    	  Map<String, String> map1 = new HashMap<String, String>();
    	  long timeMillis = System.currentTimeMillis();
    	  if(map!=null){
    		 map1.put("appId", map.get("appid"));
    		 map1.put("nonceStr", map.get("nonce_str"));
    		 map1.put("package", "prepay_id="+map.get("prepay_id"));
    		 map1.put("signType","MD5");
    		 map1.put("timeStamp", timeMillis+"");
    	  }
    	  if(map.get("return_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS)
    			&& WXPayUtil.generateSignature(map, merchantapikey).equals(map.get("sign"))
    			&& map.get("result_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS)){
    		 res.setCode(CBPConstant.CODE_SUCCESS);
    		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
    		 map.put("timeMillis", timeMillis+"");
    		 map.put("paySign", WXPayUtil.generateSignature(map1, merchantapikey));
    		 res.setData(map);
    		 return  res;
    	  }
       }
	  }catch (Exception e) {
		e.printStackTrace();
	  }
       res.setData(map);
       return res;
   }
   
   
   /**
    * 生成商户订单
    *
    * @param 	
    * @return
    */
   public String getOutTrade(WXPayDTO wxPayDTO,HttpServletRequest request,
		 String appid,String appsecret,String merchantnum,String merchantapikey) {
	  Map<String, String> map = new HashMap<String, String>();
	  String mapToXml = "";
	  try {
		 map.put("appid", appid);//公众账号ID
		 map.put("mch_id", merchantnum);//商户号
		 map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
		 map.put("body", "商家名称-销售商品类目");//商品描述
		 map.put("out_trade_no",wxPayDTO.getUniqueCode()+
			   wxPayDTO.getOrderId());//商户订单号
		 map.put("total_fee", wxPayDTO.getPaySum());//标价金额
		 map.put("spbill_create_ip", IPUtil.getIpAddr(request));//终端IP
		 map.put("notify_url", wxPayDTO.getNotifyUrl());//通知地址
		 map.put("trade_type", WeChatConstant.TRADE_TYPE_JSAPI);//交易类型
		 map.put("openid", wxPayDTO.getOpenId());
		 map.put("sign", WXPayUtil.generateSignature(map, merchantapikey));//签名
		 mapToXml = WXPayUtil.mapToXml(map);
	  }
	  catch (Exception e) {
		 e.printStackTrace();
	  }
	  return mapToXml;

   }
   
   
   
   /**    
    * {获取请求预支付参数}    
    *    
    * @param documentModelDTO 
    */ 
   @RequestMapping("/getPayOrderParam")
   public WeixinResult getPayOrderParam(@RequestBody DocumentModelDTO documentModelDTO){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
      res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
      if(documentModelDTO!=null && !StringUtil.isNullorEmpty(documentModelDTO.getOrderId())){
    	 try {
    		WXPayDTO wxPayDTO = new WXPayDTO();
			CBPOrder order1 = orderService.getOrderById(Integer.parseInt(documentModelDTO.getOrderId()));
			CBPDeposit cbpDeposit = depositService.selectDepositBySapOrderId(order1.getSaporderid());
			Integer paysum = null;
			if(cbpDeposit!=null){
			paysum = (int)(Double.parseDouble(cbpDeposit.getDepositfirst())*100);
			}
			//根据订单号获取OpenId
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("customerid",order1.getCustomerid());
			jsonObject.put("brandid",order1.getBrandid());
			String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObject.toString());
			String[] split = getcus.split(",");
			String customername=split[0];
			String openid=split[1];
			wxPayDTO.setBrandId(order1.getBrandid());
			wxPayDTO.setNotifyUrl(systemConfig.getProperty("url.notifyUrl"));
			wxPayDTO.setOpenId(openid);
			wxPayDTO.setOrderId(documentModelDTO.getOrderId());
			wxPayDTO.setPaySum(paysum.toString());
			wxPayDTO.setUniqueCode(systemConfig.getProperty("url.uniqueCode"));
			System.out.println("请求预支付参数-----"+order1.getBrandid()+"\n"
			+systemConfig.getProperty("url.notifyUrl")+"\n"+openid+"\n"
				  +documentModelDTO.getOrderId()+""+paysum.toString()+"\n"+systemConfig.getProperty("url.uniqueCode"));
			res.setCode(CBPConstant.CODE_SUCCESS);
   		 	res.setMessage(CBPConstant.MESSAGE_SUCCESS);
   		 	res.setData(wxPayDTO);
		 }
		 catch (NumberFormatException e) {
			e.printStackTrace();
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
    	 
    	 
      }
      
      
      return res;
   }
   
   
   
   
   /**  
    * 定金支付微信回调  
    * @author yangyuchao 
    * @param request
    * @return String
    */ 
   @RequestMapping("/wechatPayNotify")
   public String wechatPayNotify(HttpServletRequest request) {
	  try {
	  //处理重复的微信请求当收到通知进行处理时，首先检查对应业务数据的状态，
	  //判断该通知是否已经处理过，如果没有处理过再进行处理，
	  //如果处理过直接返回结果成功。在对业务数据进行状态检查和处理之前，
	  	Map<String, String> map = WeixinConUtil.getCallbackParams(request);
	  	if(map == null || WeChatConstant.CALLBACK_FAIL.equalsIgnoreCase(map.get("return_code"))){
	  	 return WeixinConUtil.getPayCallback(WeChatConstant.CALLBACK_FAIL,WeChatConstant.CALLBACK_FAIL_MESSAGE);
	  	}
	  	System.out.println("定金支付微信回调----"+map);
		String totalFee = map.get("total_fee"); //订单金额
		String tradeType = map.get("trade_type"); //交易类型
		String sapOrderIds = map.get("out_trade_no");//商户订单号
		String sapOrderId = sapOrderIds.split("q")[1];
		String openId = map.get("openid");//用户标识
		String timeEnd = map.get("time_end");//支付完成时间
		String bankType = map.get("bank_type");//银行类型
		Double paysum = null;
		if(sapOrderId!=null){
		   CBPOrder cbpOrder = orderService.getOrderById(Integer.valueOf(sapOrderId));
		   Integer orderId = cbpOrder.getOrderid();
		   	CBPPay cbpPay = payService.selectPayById(orderId);
		   	String brandid = cbpOrder.getBrandid();
			 String response11 = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getEmployeeBrandId", "{\"brandid\":" + brandid + "}");
			 System.out.println(response11);
			 JSONObject jsonObject11 = JSONObject.fromObject(response11);
			 String appid = (String) jsonObject11.get("appid");
			 String appsecret = (String) jsonObject11.get("appsecret");
			 String merchantnum = (String) jsonObject11.get("merchantnum");
			 String merchantapikey = (String) jsonObject11.get("merchantapikey");
			 System.out.println("appid = "+appid+"---appsecret = "+appsecret+"---merchantnum = "+merchantnum+"----merchantapikey"+merchantapikey);
		   if(map.get("return_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS)){
			  if(cbpPay != null && CBPConstant.ISRECNOTIIFY_YES.equals(cbpPay.getIsrecnotiify())){
				 System.out.println("通知已经处理过");
				 return WeixinConUtil.getPayCallback(WeChatConstant.CALLBACK_SUCESS,WeChatConstant.CALLBACK_SUCESS_MESSAGE);
			  }
		   }
		   CBPDeposit cbpDeposit = depositService.selectDepositByOrderId(orderId.toString());
		   if(cbpDeposit!=null){
			  System.out.println("查询的金额为    "+cbpDeposit.getDepositfirst());
			  paysum = Double.parseDouble(cbpDeposit.getDepositfirst())*100;
		   }else{
			  System.out.println("未查询到该定金");
			  return WeixinConUtil.getPayCallback(WeChatConstant.CALLBACK_FAIL,WeChatConstant.CALLBACK_FAIL_MESSAGE);
		   }
		   if (map.get("result_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS)) {
			  //还需判断校验返回的订单金额是否与商户侧的订单金额一致
			  //判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名
			 System.out.println("微信返回的sign   "+ map.get("sign"));
			 System.out.println("后端拼接的sign   "+ WXPayUtil.generateSignature(map, merchantapikey));
			 System.out.println("微信返回的金额    "+totalFee);
			  if (map.get("sign").equals(WXPayUtil.generateSignature(map, merchantapikey)) && paysum != null && paysum.equals(Double.parseDouble(totalFee))) {
				 //成功后的业务逻辑
				 log.info("通知未处理过");
				 CBPPay pay = new CBPPay();
				 pay.setOpenid(openId);
				 pay.setBanktype(bankType);
				 pay.setIsrecnotiify(CBPConstant.ISRECNOTIIFY_YES);
				 pay.setPayclass(CBPConstant.PAY_CLASS_DEPOSIT);
				 pay.setPaystatus(CBPConstant.PayStatusEnum.PAYSUCCESS.getType()+"");
				 pay.setPaysum((Double.parseDouble(totalFee)/100)+"");
				 pay.setPaytime(timeEnd);
				 pay.setPaytype(CBPConstant.PAY_TYPE_WX);
				 pay.setTradetype(tradeType);
				 pay.setOrderid(orderId);
				 //修改定金状态和添加支付Pay
				 int status = payService.insertCBPPay(pay);
				 //支付结果上传给SAP
				 if(status != 0){
					//发送消息给财务
					String employeeid = wxService.pushMessageToFinance(brandid, cbpOrder.getCustomername(), cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"));
					String newCard = cardService.newCard(cbpOrder,employeeid,CBPConstant.CardTypeEnum.AFFIRMCOLLECTDEPOSIT.getType(), CBPConstant.CardTypeDescriptionEnum.AFFIRMCOLLECTDEPOSIT.getType());
					//精品顾问
//					ProcessDTO processDTO = orderService.getOrderProcess(cbpOrder.getOrderid());
//					if(processDTO.getBoutiqueId()!=0 && processDTO.getBoutiqueId()!=null){
//					   String toboutique = wxService.pushMessageToboutique(brandid, cbpOrder.getCustomername(), cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"));
//					   String newCard1 = cardService.newCard(cbpOrder,toboutique,CBPConstant.CardTypeEnum.PREPAREBOUTIQUE.getType(), CBPConstant.CardTypeDescriptionEnum.PREPAREBOUTIQUE.getType());
//					}
//					//改变CBPOrder 和 法大大订单状态
//					cbpOrder.setOrderstatus(CBPConstant.OrderStatusEnum.WAITINGALLPAY.getType()+"");
//					 cbpOrder.setDelivertime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
//					 OrderDTO orderDTO = new OrderDTO();
//					 orderDTO.setDepositSum((Double.parseDouble(totalFee)/100)+"");
//					 contractManageService.uploadOrderData(cbpOrder,orderDTO);
//					 cbpOrder.setDelivertime(null);
//					Boolean updateOrderStauts = contractManageService.updateOrderStauts(cbpOrder);
//					log.info("法大大返回结果:"+updateOrderStauts);
					return WeixinConUtil.getPayCallback(WeChatConstant.CALLBACK_SUCESS, WeChatConstant.CALLBACK_SUCESS_MESSAGE);
				 }
			  }
		   }
		   	CBPPay pay = new CBPPay();
			 pay.setOpenid(openId);
			 pay.setBanktype(bankType);
			 pay.setIsrecnotiify(CBPConstant.ISRECNOTIIFY_NO);
			 pay.setPayclass(CBPConstant.PAY_CLASS_DEPOSIT);
			 pay.setPaystatus(CBPConstant.PayStatusEnum.PAYERROR.getType()+"");
			 pay.setPaysum(cbpDeposit.getDepositfirst());
			 pay.setPaytime(timeEnd);
			 pay.setPaytype(CBPConstant.PAY_TYPE_WX);
			 pay.setTradetype(tradeType);
			 pay.setOrderid(orderId);
			 int status = payService.insertCBPPayWhenWXPayError(pay);
			 log.info("微信支付失败添加失败Pay:"+status);
		}
	  	
	  }catch (Exception e) {
		 e.printStackTrace();
	  }
	  return WeixinConUtil.getPayCallback(WeChatConstant.CALLBACK_FAIL,WeChatConstant.CALLBACK_FAIL_MESSAGE);
   }

   
   
   /**
    * 微信服务号请求CBP后台查询支付结果
    * 
    */
   @RequestMapping("/selectPay")
   public WeixinResult selectPay(@RequestBody CBPPay cbpPay) {
	  WeixinResult weixinResult = new WeixinResult();
	  weixinResult.setCode(CBPConstant.CODE_NULL_PARAM);
	  weixinResult.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  CBPDeposit cbpDeposit = null;
	  try {
	  if (cbpPay != null) {
		 CBPPay cbpPay2 = payService.selectPayById(cbpPay.getOrderid());
		 CBPOrder cbpOrder = orderService.getOrderById(cbpPay.getOrderid());
		   	String brandid = cbpOrder.getBrandid();
			 String response11 = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getEmployeeBrandId", "{\"brandid\":" + brandid + "}");
			 System.out.println(response11);
			 JSONObject jsonObject11 = JSONObject.fromObject(response11);
			 String appid = (String) jsonObject11.get("appid");
			 String appsecret = (String) jsonObject11.get("appsecret");
			 String merchantnum = (String) jsonObject11.get("merchantnum");
			 String merchantapikey = (String) jsonObject11.get("merchantapikey");
			 System.out.println("appid = "+appid+"---appsecret = "+appsecret+"---merchantnum = "+merchantnum+"----merchantapikey"+merchantapikey);
		 if (cbpPay2==null || CBPConstant.ISRECNOTIIFY_NO.equals(cbpPay2.getIsrecnotiify())) {
			Map<String, String> map = WeixinConUtil.httpRequest(WeChatConstant.SELECT_ORDER_URL, "POST",
				  WeixinConUtil.getPayOrderCallback(cbpPay, appid, merchantnum,merchantapikey));
			if(map==null || map.get("return_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_FAIL)
				  || map.get("result_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_FAIL)){
			   weixinResult.setCode(CBPConstant.CODE_NULL_PARAM);
			   weixinResult.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			   //return weixinResult;
			}
			//以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回
			if(map.get("return_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS) &&
				  WXPayUtil.isSignatureValid(map, merchantapikey) &&
				  map.get("result_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS) &&
				  	map.get("trade_state").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS)){ 
			   weixinResult.setCode(CBPConstant.CODE_SUCCESS);
			   weixinResult.setMessage(CBPConstant.MESSAGE_SUCCESS);
			   weixinResult.setData(map);
			   //修改定金状态和添加支付Pay
			   CBPPay pay = new CBPPay();
			   	 pay.setOpenid(map.get("openid"));
				 pay.setBanktype(map.get("bank_type"));
				 pay.setIsrecnotiify(CBPConstant.ISRECNOTIIFY_YES);
				 pay.setPayclass(CBPConstant.PAY_CLASS_DEPOSIT);
				 pay.setPaystatus(CBPConstant.PayStatusEnum.PAYSUCCESS.getType()+"");
				 pay.setPaysum((Double.parseDouble(map.get("total_fee"))/100)+"");
				 pay.setPaytime(map.get("time_end"));
				 pay.setPaytype(CBPConstant.PAY_TYPE_WX);
				 pay.setTradetype(map.get("trade_type"));
				 pay.setOrderid(cbpPay.getOrderid());
				 //修改定金状态和添加支付Pay
				 int status = payService.insertCBPPay(pay);
				 cbpDeposit = depositService.selectDepositByOrderId(cbpPay.getOrderid().toString());
				 if(status != 0){
					//发送消息给财务
					String employeeid = wxService.pushMessageToFinance(brandid, cbpOrder.getCustomername(), cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"));
					String newCard = cardService.newCard(cbpOrder,employeeid,CBPConstant.CardTypeEnum.AFFIRMCOLLECTDEPOSIT.getType(), CBPConstant.CardTypeDescriptionEnum.AFFIRMCOLLECTDEPOSIT.getType());
					//精品顾问
					ProcessDTO processDTO = orderService.getOrderProcess(cbpOrder.getOrderid());
					if(processDTO.getBoutiqueId()!=0 && processDTO.getBoutiqueId()!=null){
					   String toboutique = wxService.pushMessageToboutique(brandid, cbpOrder.getCustomername(), cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"));
					   String newCard1 = cardService.newCard(cbpOrder,toboutique,CBPConstant.CardTypeEnum.PREPAREBOUTIQUE.getType(), CBPConstant.CardTypeDescriptionEnum.PREPAREBOUTIQUE.getType());
					}
//					//改变CBPOrder 和 法大大订单状态
//						cbpOrder.setOrderstatus(CBPConstant.OrderStatusEnum.WAITINGALLPAY.getType()+"");
//					 cbpOrder.setDelivertime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
//					 OrderDTO orderDTO = new OrderDTO();
//					 orderDTO.setDepositSum((Double.parseDouble(map.get("total_fee"))/100)+"");
//					 contractManageService.uploadOrderData(cbpOrder,orderDTO);
//					 cbpOrder.setDelivertime(null);
//						Boolean updateOrderStauts = contractManageService.updateOrderStauts(cbpOrder);
//						log.info("法大大返回结果:"+updateOrderStauts);
				 }
			   return weixinResult;
			}else if(map.get("return_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS) &&
				  WXPayUtil.isSignatureValid(map, merchantapikey) &&
				  map.get("result_code").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS) &&
				  !map.get("trade_state").toString().equalsIgnoreCase(WeChatConstant.CALLBACK_SUCESS)){
			   weixinResult.setCode(CBPConstant.CODE_SUCCESS);
			   weixinResult.setMessage(CBPConstant.MESSAGE_SUCCESS);
			   //weixinResult.setData(map);
			   //return weixinResult;
			}
			CBPPay pay = new CBPPay();
			pay.setOpenid(map.get("openid"));
			 pay.setBanktype(map.get("bank_type"));
			 pay.setIsrecnotiify(CBPConstant.ISRECNOTIIFY_NO);
			 pay.setPayclass(CBPConstant.PAY_CLASS_DEPOSIT);
			 pay.setPaystatus(CBPConstant.PayStatusEnum.PAYERROR.getType()+"");
			 if(map.get("total_fee")!=null){
				pay.setPaysum((Double.parseDouble(map.get("total_fee"))/100)+"");
			 }
			 pay.setPaytime(map.get("time_end"));
			 pay.setPaytype(CBPConstant.PAY_TYPE_WX);
			 pay.setTradetype(map.get("trade_type"));
			 pay.setOrderid(cbpPay.getOrderid());
			 int status = payService.insertCBPPayWhenWXPayError(pay);
			 log.info("微信支付失败添加失败Pay:"+status);
			weixinResult.setData(map);
			return weixinResult;
		 }else {
			weixinResult.setCode(CBPConstant.CODE_SUCCESS);
			weixinResult.setMessage(CBPConstant.MESSAGE_SUCCESS);
			weixinResult.setData(cbpPay2);
			return weixinResult;
		 }
	  }
		 
	  }
	  catch (Exception e) {
		e.printStackTrace();
	  }
	  return weixinResult;
   }
   
   /**    
    *客户尾款支付后下发消息给财务顾问
    *
    */ 
   @RequestMapping("/balancePaymentPay")
   public void balancePaymentPay(@RequestBody CBPOrder order){
	  
	  if(order!=null && !StringUtil.isNullorEmpty(order.getOrderid()+"")){
	  	//发送消息给财务
	  	CBPOrder cbpOrder = orderService.getOrderById(order.getOrderid());
		String finance = wxService.pushMessageToFinance(cbpOrder.getBrandid(), cbpOrder.getCustomername(), cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl")+"?employeeid="+cbpOrder.getEmployeeid());
		String newCard = cardService.newCard(cbpOrder,finance, CBPConstant.CardTypeEnum.AFFIRMCOLLECTBALANCEPAYMENT.getType(), CBPConstant.CardTypeDescriptionEnum.AFFIRMCOLLECTBALANCEPAYMENT.getType()); 
	  }
   }
   
   @RequestMapping("/test")
   public void test(@RequestBody CBPOrder order){
	  CBPOrder cbpOrder = orderService.getOrderById(order.getOrderid());
	  String finance = wxService.pushMessageToFinance(cbpOrder.getBrandid(), cbpOrder.getCustomername(), cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"));
		String saveCard = cardService.newCard(cbpOrder, finance,CBPConstant.CardTypeEnum.AFFIRMCOLLECTDEPOSIT.getType(), CBPConstant.CardTypeDescriptionEnum.AFFIRMCOLLECTDEPOSIT.getType());
		System.out.println("财务顾问创建任务返回"+saveCard);
   }
}
