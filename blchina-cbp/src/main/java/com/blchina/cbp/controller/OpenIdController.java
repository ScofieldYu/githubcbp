/*   
 * @(#)OpenIdController.java       2017年12月28日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.CommonUtil;
import com.blchina.cbp.datamodel.WeChatConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.CodeDTO;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONObject;

/** 
 * 获取openId操作controller    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/openId")
public class OpenIdController {
   	
   
   
   @RequestMapping("/setOpenId")
   public WeixinResult setOpenId(@RequestBody CodeDTO codeDTO) throws UnsupportedEncodingException {
	  WeixinResult weixinResult = new WeixinResult();
	  HashMap map = new HashMap();
	  weixinResult.setCode(CBPConstant.CODE_NULL_PARAM);
	  weixinResult.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  String code = codeDTO.getCode();
	  if(!StringUtil.isNullorEmpty(code)){
		 String oauth_url = WeChatConstant.OAUTH_URL.replace("APPID", "wx05ac254179f84ae1").replace("SECRET", "520638186296721f33212081b43e6ce3").replace("CODE",code);
		 System.out.println("oauth_url:"+oauth_url);
		 JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(oauth_url, "POST", null);
		 System.out.println("微信返回--jsonObject:"+jsonObject);
		 Object errorCode = jsonObject.get("errcode");
		 if(errorCode != null) {
			System.out.println("code不合法");
		 }else{
			String openId = jsonObject.getString("openid");
			String access_token = jsonObject.getString("access_token");
			String userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openId+"&lang=zh_CN";
			JSONObject jsonObject1 = CommonUtil.httpsRequestToJsonObject(userinfo, "GET", null);
			System.out.println("微信返回--jsonObject1:"+jsonObject1);
			String openId1 = jsonObject1.getString("openid");
			String nickname = jsonObject1.getString("nickname");
			String province = jsonObject1.getString("province");
			String city = jsonObject1.getString("city");
			String headimgurl = jsonObject1.getString("headimgurl");
			String sex = jsonObject1.getString("sex");
			String country = jsonObject1.getString("country");
			String privilege = jsonObject1.getString("privilege");
			String nicknameencode = URLEncoder.encode(nickname, "utf-8");
			System.out.println("获取的openId为=======:"+openId1+" nickname: "+nickname+" nicknameencode:"+ nicknameencode);
			map.put("openid", openId1);
			map.put("nickname", nickname);
			map.put("province", province);
			map.put("city", city);
			map.put("headimgurl", headimgurl);
			map.put("sex", sex);
			map.put("country", country);
			map.put("privilege", privilege);
			weixinResult.setCode(CBPConstant.CODE_SUCCESS);
			weixinResult.setMessage(CBPConstant.MESSAGE_SUCCESS);
			weixinResult.setData(map);
		 }
	  }
	  		return weixinResult;
	 }
   
}
