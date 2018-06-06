/*   
 * @(#)WechatPayNotifyController.java       2017年11月24日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.cbp.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.PayUpload;
import com.blchina.cbp.datamodel.WeChatConstant;
import com.blchina.cbp.datamodel.WeixinConUtil;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.OrderDTO;
import com.blchina.cbp.model.CBPDeposit;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPPay;
import com.blchina.cbp.service.interfaces.ContractManageService;
import com.blchina.cbp.service.interfaces.DepositService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.PayService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.weixin.WXPayUtil;

import net.sf.json.JSONObject;


/**
 * 微信支付结果通知(统一下单参数的notify_url)
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/pay")
public class PayController {
   
   @Autowired
   private PayService payService;
   @Autowired
   private DepositService depositService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private WxService wxService;
   @Autowired
   private ContractManageService contractManageService;
   @Autowired
   protected Properties systemConfig;
   private static Logger log = Logger.getLogger(PayController.class);
   /**
    * 微信支付回调
    * 
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
	  	System.out.println("微信回调----"+map);
	  	String sign = map.get("sign"); //签名
		String totalFee = map.get("total_fee"); //订单金额
		String resultCode = map.get("result_code"); //业务结果
		String tradeType = map.get("trade_type"); //交易类型
		String signType = map.get("sign_type"); //签名类型目前支持HMAC-SHA256和MD5，默认为MD5
		String sapOrderId = map.get("out_trade_no");//商户订单号
		String openId = map.get("openid");//用户标识
		String timeEnd = map.get("time_end");//支付完成时间
		String bankType = map.get("bank_type");//银行类型
		String cashFeeType = map.get("cash_fee_type");//现金支付货币类型
		Double paysum = null;
		if(sapOrderId!=null){
		   CBPOrder cbpOrder = orderService.getOrderBySapId(sapOrderId);
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
				 PayUpload payUpload = new PayUpload();
				 payUpload.setBelnr(cbpDeposit.getBelnr()+"");
				 payUpload.setBukrs(cbpDeposit.getBukrs()+"");

				 if(cashFeeType !=null){
					payUpload.setCurrency(cashFeeType);
				 }else{
					payUpload.setCurrency("CNY");
				 }
				 payUpload.setGjahr(cbpDeposit.getGjahr()+"");
				 payUpload.setPaystatus(CBPConstant.PayStatusEnum.PAYSUCCESS.getType()+"");
				 payUpload.setPaysum((Double.parseDouble(totalFee)/100)+"");
				 payUpload.setPaytype(CBPConstant.PAY_TYPE_WX);
				 payUpload.setSaporderid(cbpDeposit.getSaporderid());
				 payUpload.setZZF2("");
				 payUpload.setZZF3("");
				 JSONObject jsonObject1 = JSONObject.fromObject(payUpload);
				 JSONObject  getJson=new JSONObject();
				 getJson.put("Records", jsonObject1.toString());
				 String response =HttpUtil.postAuth(systemConfig.getProperty("url.sap.pay.info"), 
					   getJson.toString(), systemConfig.getProperty("url.sap.username"), 
					   				systemConfig.getProperty("url.sap.password"));
					log.info("SAP返回结果:"+response);
					System.out.println("SAP返回结果:"+response);
					//发送消息给财务
					wxService.pushPayMoney(cbpOrder.getCustomerid(),cbpDeposit.getDepositfirst() , cbpOrder.getBrandid());
					//改变CBPOrder 和 法大大订单状态
					cbpOrder.setOrderstatus(CBPConstant.OrderStatusEnum.WAITINGALLPAY.getType()+"");
					 cbpOrder.setDelivertime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
					 OrderDTO orderDTO = new OrderDTO();
					 orderDTO.setDepositSum((Double.parseDouble(totalFee)/100)+"");
					 contractManageService.uploadOrderData(cbpOrder,orderDTO);
					 cbpOrder.setDelivertime(null);
					Boolean updateOrderStauts = contractManageService.updateOrderStauts(cbpOrder);
					log.info("法大大返回结果:"+updateOrderStauts);
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
					 PayUpload payUpload = new PayUpload();
					 payUpload.setBelnr(cbpDeposit.getBelnr()+"");
					 payUpload.setBukrs(cbpDeposit.getBukrs()+"");
					 if(map.get("cash_fee_type")!=null){
						payUpload.setCurrency(map.get("cash_fee_type"));
					 }else{
						payUpload.setCurrency("CNY");
					 }
					 payUpload.setGjahr(cbpDeposit.getGjahr()+"");
					 payUpload.setPaystatus(CBPConstant.PayStatusEnum.PAYSUCCESS.getType()+"");
					 payUpload.setPaysum((Double.parseDouble(map.get("total_fee"))/100)+"");
					 payUpload.setPaytype(CBPConstant.PAY_TYPE_WX);
					 payUpload.setSaporderid(cbpDeposit.getSaporderid());
					 payUpload.setZZF2("");
					 payUpload.setZZF3("");
					 JSONObject jsonObject1 = JSONObject.fromObject(payUpload);
					 JSONObject  getJson=new JSONObject();
					 getJson.put("Records", jsonObject1.toString());
					 String response =HttpUtil.postAuth(systemConfig.getProperty("url.sap.pay.info"), 
						   getJson.toString(), systemConfig.getProperty("url.sap.username"), 
						   				systemConfig.getProperty("url.sap.password"));
						log.info("SAP返回结果:"+response);
						System.out.println("SAP返回结果:"+response);
						//发送消息给财务
						wxService.pushPayMoney(cbpOrder.getCustomerid(),cbpDeposit.getDepositfirst() , cbpOrder.getBrandid());
						//改变CBPOrder 和 法大大订单状态
						cbpOrder.setOrderstatus(CBPConstant.OrderStatusEnum.WAITINGALLPAY.getType()+"");
					 cbpOrder.setDelivertime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
					 OrderDTO orderDTO = new OrderDTO();
					 orderDTO.setDepositSum((Double.parseDouble(map.get("total_fee"))/100)+"");
					 contractManageService.uploadOrderData(cbpOrder,orderDTO);
					 cbpOrder.setDelivertime(null);
						Boolean updateOrderStauts = contractManageService.updateOrderStauts(cbpOrder);
						log.info("法大大返回结果:"+updateOrderStauts);
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
}
