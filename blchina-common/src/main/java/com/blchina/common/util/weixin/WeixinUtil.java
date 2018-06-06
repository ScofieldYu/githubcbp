/*   
 * @(#)WeixinUtil.java       Nov 15, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.common.util.weixin;

import com.blchina.common.util.http.HttpsConnection;
import net.sf.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;


/**
 * 微信工具类，包括token校验
 *
 * @author Scofield
 * @since JDK 1.8
 */
public class WeixinUtil {
   public static boolean checkSignature(String signature, String timestamp,
		 String nonce, String token) {
	  //从请求中（也就是微信服务器传过来的）拿到的token, timestamp, nonce
	  String[] arr = new String[] { token, timestamp, nonce };
	  // 将token、timestamp、nonce三个参数进行字典序排序
	  sort(arr);
	  StringBuilder content = new StringBuilder();
	  for (int i = 0; i < arr.length; i++) {
		 content.append(arr[i]);
	  }
	  MessageDigest md = null;
	  String tmpStr = null;

	  try {
		 md = MessageDigest.getInstance("SHA-1");
		 // 将三个参数字符串拼接成一个字符串进行sha1加密
		 byte[] digest = md.digest(content.toString().getBytes());
		 //将字节数组转成字符串
		 tmpStr = byteToStr(digest);
	  }
	  catch (NoSuchAlgorithmException e) {
		 e.printStackTrace();
	  }

	  content = null;
	  // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
	  return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
   }

   //将加密后的字节数组变成字符串
   private static String byteToStr(byte[] byteArray) {
	  String strDigest = "";
	  for (int i = 0; i < byteArray.length; i++) {
		 strDigest += byteToHexStr(byteArray[i]);
	  }
	  return strDigest;
   }

   private static String byteToHexStr(byte mByte) {
	  char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
			'B', 'C', 'D', 'E', 'F' };
	  char[] tempArr = new char[2];
	  tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
	  tempArr[1] = Digit[mByte & 0X0F];

	  String s = new String(tempArr);
	  return s;
   }

   //用于字典排序
   public static void sort(String a[]) {
	  for (int i = 0; i < a.length - 1; i++) {
		 for (int j = i + 1; j < a.length; j++) {
			if (a[j].compareTo(a[i]) < 0) {
			   String temp = a[i];
			   a[i] = a[j];
			   a[j] = temp;
			}
		 }
	  }
   }
	public static Boolean pushMessage(String userid,String content,String title,String url,String tourl,String agentid){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("touser",userid);
		jsonObject.put("msgtype", "textcard");
		jsonObject.put("agentid",agentid);
		JSONObject news=new JSONObject();
		news.put("title",title);
		news.put("description",content);
		news.put("url",url);
		jsonObject.put("textcard",news.toString());
		try {
			String result=HttpsConnection.doPost(tourl,jsonObject.toString(),false);
			JSONObject getJson=JSONObject.fromObject(result);
			String errcode =String.valueOf(getJson.get("errcode"));
			if(errcode.equals("0")){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	public static Boolean pushtextMessage(String userid,String content,String url,String agentid){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("touser",userid);
		jsonObject.put("msgtype", "text");
		jsonObject.put("agentid",agentid);
		JSONObject news=new JSONObject();
		news.put("content",content);
		jsonObject.put("text",news.toString());
		try {
			HttpsConnection.doPost(url,jsonObject.toString(),false);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static Boolean pushTemplateMassage(String token,String templateId,String openid,String data,String tourl){
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("touser",openid);
		jsonObject.put("template_id",templateId);
		if(!tourl.equals("#")){
			jsonObject.put("url",tourl);
		}
	    jsonObject.put("data",data);
		try {
		String result=	HttpsConnection.doPost(url,jsonObject.toString(),false);
			JSONObject getJson=JSONObject.fromObject(result);
			String errcode =String.valueOf(getJson.get("errcode"));
			if(errcode.equals("0")){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
