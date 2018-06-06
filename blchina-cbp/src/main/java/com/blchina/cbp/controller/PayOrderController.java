/*   
 * @(#)PayOrderController.java       2017年11月28日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.CommonUtil;
import com.blchina.cbp.datamodel.WeChatConstant;
import com.blchina.cbp.datamodel.WeixinConUtil;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.DocumentModelDTO;
import com.blchina.cbp.model.CBPDeposit;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.DepositService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.PayService;
import com.blchina.common.ip.IPUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.weixin.WXPayUtil;
import net.sf.json.JSONObject;

/** 
 * 支付订单操作controller     
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/payOrder")
public class PayOrderController {
   
   @Autowired
   private OrderService orderService;
   @Autowired
   private PayService payService;
   @Autowired
   private DepositService depositService;
   @Autowired
   protected Properties systemConfig;
   /**
    * 请求生成支付订单
    *
    * @param payOrder
    * @return
    */
   @RequestMapping("/getPayOrder")
   public WeixinResult getPayOrder(@RequestBody DocumentModelDTO documentModelDTO,HttpServletRequest request) {
       WeixinResult res = new WeixinResult();
       System.out.println("服务器IP"+IPUtil.getIpAddr(request));
       res.setCode(CBPConstant.CODE_NULL_PARAM);
       res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
       Map<String, String> map = null;
       try {
       if(documentModelDTO != null){
    	 CBPOrder order1 = orderService.getOrderById(Integer.parseInt(documentModelDTO.getOrderId()));
    	 String brandid = order1.getBrandid();
 		 String response = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getEmployeeBrandId", "{\"brandid\":" + brandid + "}");
 		 System.out.println(response);
 		 JSONObject jsonObject1 = JSONObject.fromObject(response);
 		 String appid = (String) jsonObject1.get("appid");
 		 String appsecret = (String) jsonObject1.get("appsecret");
 		 String merchantnum = (String) jsonObject1.get("merchantnum");
 		 String merchantapikey = (String) jsonObject1.get("merchantapikey");
 		 System.out.println("appid = "+appid+"---appsecret = "+appsecret+"---merchantnum = "+merchantnum+"----merchantapikey"+merchantapikey);
    	  String outputStr = getOutTrade(order1, request);
    	  map = WeixinConUtil.httpRequest(WeChatConstant.UNIFIED_ORDER_URL, "POST", outputStr);
    	  System.out.println("微信返回----"+map);
    	  if(map.get("err_code")!=null){
    		 res.setCode(CBPConstant.CODE_NULL_PARAM);
    		 res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
    		 res.setData(map.get("err_code_des"));
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
    * @param outTrade	
    * @return
    */
   public String getOutTrade(CBPOrder order, HttpServletRequest request) {
	  Map<String, String> map = new HashMap<String, String>();
	  String mapToXml = "";
	  Integer paysum = null;
	  try {
		 String brandid = order.getBrandid();
		 String response = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getEmployeeBrandId", "{\"brandid\":" + brandid + "}");
		 System.out.println(response);
		 JSONObject jsonObject1 = JSONObject.fromObject(response);
		 String appid = (String) jsonObject1.get("appid");
		 String appsecret = (String) jsonObject1.get("appsecret");
		 String merchantnum = (String) jsonObject1.get("merchantnum");
		 String merchantapikey = (String) jsonObject1.get("merchantapikey");
		 System.out.println("appid = "+appid+"---appsecret = "+appsecret+"---merchantnum = "+merchantnum+"----merchantapikey"+merchantapikey);
		 map.put("appid", appid);//公众账号ID
		 map.put("mch_id", merchantnum);//商户号
		 map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
		 map.put("body", "商家名称-销售商品类目");//商品描述
		 //重新发起一笔支付要使用原订单号，避免重复支付；
		 //已支付过或已调用关单、撤销（请见后文的API列表）的订单号不能重新发起支付。
		 map.put("out_trade_no", order.getSaporderid() + "");//商户订单号
		 //根据订单号查定金
		 CBPDeposit cbpDeposit = depositService.selectDepositBySapOrderId(order.getSaporderid());
		 if(cbpDeposit!=null){
			paysum = (int)(Double.parseDouble(cbpDeposit.getDepositfirst())*100);
		 }
		 //根据订单号获取OpenId
		 JSONObject jsonObject=new JSONObject();
		 jsonObject.put("customerid",order.getCustomerid());
		 jsonObject.put("brandid",order.getBrandid());
		 String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObject.toString());
		 String[] split = getcus.split(",");
		 String customername=split[0];
		 String openid=split[1];
		 //订单总金额，单位为分，详见支付金额
		 //交易金额默认为人民币交易，接口中参数支付金额单位为【分】，
		 //参数值不能带小数。对账单中的交易金额单位为【元】。
		 //外币交易的支付金额精确到币种的最小单位，参数值不能带小数点。
		 map.put("total_fee", paysum+"");//标价金额
		 //map.put("total_fee", "1");//标价金额
		 map.put("spbill_create_ip", IPUtil.getIpAddr(request));//终端IP
		 map.put("notify_url", systemConfig.getProperty("url.NotifyUrl"));//通知地址
		 map.put("trade_type", WeChatConstant.TRADE_TYPE_JSAPI);//交易类型
		 map.put("openid", openid);
		// map.put("openid", "o8CFDwxzkFpUoT_gGmLZc82p9z-E");
		 map.put("sign", WXPayUtil.generateSignature(map, merchantapikey));//签名
		 mapToXml = WXPayUtil.mapToXml(map);
	  }
	  catch (Exception e) {
		 e.printStackTrace();
	  }
	  return mapToXml;

   }
   
   	
   	
}
