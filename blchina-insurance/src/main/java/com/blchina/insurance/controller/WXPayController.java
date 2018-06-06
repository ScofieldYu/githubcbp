/*   
 * @(#)WXPayController.java       2018年1月31日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.common.ip.IPUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.blchina.common.util.weixin.WXPayUtil;
import com.blchina.insurance.datamodel.CBPConstant;
import com.blchina.insurance.datamodel.weixin.WeixinResult;
import com.blchina.insurance.dto.WXPayDTO;
import com.blchina.insurance.util.WeChatConstant;
import com.blchina.insurance.util.WeixinConUtil;

import net.sf.json.JSONObject;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/wXPay")
public class WXPayController {

   @Autowired
   protected Properties systemConfig;
   
   /**
    * 请求生成支付订单
    *
    * @param payOrder
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
}
