/*   
 * @(#)EmployeeController.java       2018年2月25日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;

/** 
 * 此类功能描述    根据员工id查询对应的品牌和门店
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@RestController
@RequestMapping("/brandstore")
public class EmployeeController {
	@Autowired
	protected Properties systemConfig;
	@RequestMapping("/store")
	public JSONObject getStoreByBrandType(@RequestBody JSONObject jsonObject){
	WeixinResult res=new WeixinResult();
    try {
        Object employeeId = jsonObject.get("employeeId");
        if(!StringUtil.isNullorEmpty(String.valueOf(employeeId))){
        	String result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/brandstore/store",jsonObject.toString());
        	JSONObject jsonObject1=JSONObject.fromObject(result);
            return jsonObject1;
        }
        String result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/brandstore/store",jsonObject.toString());
        
    } catch (Exception e) {
        e.printStackTrace();
        res.setCode(CBPConstant.CODE_FAILURE);
        res.setMessage(CBPConstant.MESSAGE_FAILURE);
    }
	return new JSONObject();
	}
}