/*   
 * @(#)WeixinUtil.java       2017年11月25日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.util;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blchina.common.util.weixin.WXPayUtil;
import com.blchina.common.util.xml.XmlUtil;
import com.blchina.insurance.dto.WXPayDTO;
/** 
 * 公众平台通用接口工具类    
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
public class WeixinConUtil {
   
   private static Logger log = LoggerFactory.getLogger(WeixinConUtil.class);  
   /** 
    * 发起https请求并获取结果 
    *  
    * @param requestUrl 请求地址 
    * @param requestMethod 请求方式（GET、POST） 
    * @param outputStr 提交的数据 
    * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
    */  
   public static Map<String, String> httpRequest(String requestUrl, String requestMethod, String outputStr) {  
       //JSONObject jsonObject = null;  
	   Map<String, String> map = null;
       StringBuffer buffer = new StringBuffer();  
       try {  
           // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
           TrustManager[] tm = { new MyX509TrustManager() };  
           SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
           sslContext.init(null, tm, new java.security.SecureRandom());  
           // 从上述SSLContext对象中得到SSLSocketFactory对象  
           SSLSocketFactory ssf = sslContext.getSocketFactory();  
 
           URL url = new URL(requestUrl);  
           HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
           httpUrlConn.setSSLSocketFactory(ssf);  
 
           httpUrlConn.setDoOutput(true);  
           httpUrlConn.setDoInput(true);  
           httpUrlConn.setUseCaches(false);  
           // 设置请求方式（GET/POST）  
           httpUrlConn.setRequestMethod(requestMethod);  
 
           if ("POST".equalsIgnoreCase(requestMethod))  
               httpUrlConn.connect();  
 
           // 当有数据需要提交时  
           if (null != outputStr) {  
               OutputStream outputStream = httpUrlConn.getOutputStream();  
               // 注意编码格式，防止中文乱码  
               outputStream.write(outputStr.getBytes("UTF-8"));  
               outputStream.close();  
           }  
 
           // 将返回的输入流转换成字符串  
           InputStream inputStream = httpUrlConn.getInputStream();  
           InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
           BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
 
           String str = null;  
           while ((str = bufferedReader.readLine()) != null) {  
               buffer.append(str);  
           }  
           bufferedReader.close();  
           inputStreamReader.close();  
           // 释放资源  
           inputStream.close();  
           inputStream = null;  
           httpUrlConn.disconnect();  
           //jsonObject = JSONObject.fromObject(buffer.toString());  
           //返回结果转map
           map = WXPayUtil.xmlToMap(buffer.toString());
       } catch (ConnectException ce) {  
           log.error("Weixin server connection timed out.");  
       } catch (Exception e) {  
           log.error("https request error:{}", e);  
       }  
       return map;  
   }  
   
   /**
    * 获取请求参数转map
    * 
    */
   public static Map<String, String> getCallbackParams(
		 HttpServletRequest request) throws Exception {
	  InputStream inStream = request.getInputStream();
	  ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
	  byte[] buffer = new byte[1024];
	  int len = 0;
	  while ((len = inStream.read(buffer)) != -1) {
		 outSteam.write(buffer, 0, len);
	  }
	  System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
	  outSteam.close();
	  inStream.close();
	  String result = new String(outSteam.toByteArray(), "utf-8");
	  return XmlUtil.getXmlValue(result);//xml 转换成map输出
   }
   public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
      // 将解析结果存储在HashMap中  
      Map<String, String> map = new HashMap<String, String>();  

      // 从request中取得输入流  
      InputStream inputStream = request.getInputStream();  
      // 读取输入流  
      SAXReader reader = new SAXReader();  
      Document document = reader.read(inputStream);  
      // 得到xml根元素  
      Element root = document.getRootElement();  
      // 得到根元素的所有子节点  
        
      @SuppressWarnings("unchecked")  
      List<Element> elementList = root.elements();  

      // 遍历所有子节点  
      for (Element e : elementList)  
          map.put(e.getName(), e.getText());  

      // 释放资源  
      inputStream.close();  
      inputStream = null;  

      return map;  
  }  
   /**
    * 收到支付结果后返回的XML字符串
    * 
    */
   public static String getPayCallback(String returnCode, String returnMsg) {
	  Map<String, String> map = new HashMap<String, String>();
	  map.put("return_code", returnCode);
	  map.put("return_msg", returnMsg);
	  String mapToXml = "";
	  try {
		 mapToXml = WXPayUtil.mapToXml(map);
	  }
	  catch (Exception e) {
		 e.printStackTrace();
	  }
	  return mapToXml;
   }
   /**
    * 商户查询微信订单传入的XML 
    */
   public static String getPayOrderCallback(WXPayDTO wxPayDTO,String appId,String mchId,String key){
	  try {
		 Map<String,String> map = new HashMap<String,String>();
		 map.put("appid",appId);//公众账号ID
		 map.put("mch_id",mchId);//商户号
		 map.put("nonce_str",WXPayUtil.generateNonceStr());//随机字符串
		 map.put("out_trade_no",wxPayDTO.getOrderId());//商户订单号  微信订单号(二选一)
		 map.put("sign", WXPayUtil.generateSignature(map, key));//签名
		 //map转XML
		 String mapToXml = WXPayUtil.mapToXml(map);
		 return mapToXml;
	  }
	  catch (Exception e) {
		e.printStackTrace();
	  }
	  return null;
   }
   
}
