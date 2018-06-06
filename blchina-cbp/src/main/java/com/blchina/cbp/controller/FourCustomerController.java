/*   
 * @(#)FourCustomerController.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.BDLFourCustomer;
import com.blchina.cbp.dto.BDLFourCustomerListDTO;
import com.blchina.cbp.dto.CardDTO;
import com.blchina.cbp.dto.ChangeAllFee;
import com.blchina.cbp.dto.FourCustomerDTO;
import com.blchina.cbp.dto.PagePojo;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.FourCustomerService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONObject;

/** 
 * 客户资料（四方信息）    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/fourCustomer")
public class FourCustomerController {

   @Autowired
   private FourCustomerService fourCustomerService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private Properties systemConfig;
   /**    
    * 维护四方信息  cbp
    * @return WeixinResult
    */ 
   @RequestMapping("/insertOrUpdateFourCustomer")
   public WeixinResult insertOrUpdateFourCustomer(@RequestBody FourCustomerDTO dto){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(dto!=null && !dto.getFourCustomerList().isEmpty()){
		 WeixinResult res1 = fourCustomerService.insertOrUpdateFourCustomer(dto);
		 return res1;
		 }
	  return res;
   }
   /**    
    * 维护四方信息  bdl
    * @return WeixinResult
    */ 
   @RequestMapping("/saveFourCustomer")
   public WeixinResult saveFourCustomer(@RequestBody BDLFourCustomerListDTO dto){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(dto!=null && !dto.getFourCustomerListDTO().isEmpty()){
		 try {
			String response = HttpUtil.postbody(
				  systemConfig.getProperty("url.customer") + "/insertOrUpdateFourCustomer",
			      JSONObject.fromObject(dto).toString());
			if("success".equals(response)){
			   res.setCode(CBPConstant.CODE_SUCCESS);
			   res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			}
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  }
	  return res;
   }
   
   
   
   /**维护价格
    * @param 
    * @return WeixinResult
    */ 
   @RequestMapping("/maintenancePrice")
   public WeixinResult maintenancePrice(@RequestBody ChangeAllFee caf){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(caf==null || caf.getBoutiqueOrder()==null || caf.getCar()==null || caf.getInsuranceOrder()==null){
		 return res;
	  }else{
		 int status = orderService.maintenancePrice(caf);
		 if(status!=0){
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 }
	  }
	  return res;
   }
   
   
   /**
    * 四方信息搜索    
    * @param cd
    * @return 
    * @since JDK 1.8 
    */ 
   @RequestMapping("/searchFourCustomer")
   public WeixinResult searchFourCustomer(@RequestBody CardDTO cd){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(cd!=null){
		 try {
			String response = HttpUtil.postbody(
				  systemConfig.getProperty("url.customer") + "/searchFourCustomer",
			      JSONObject.fromObject(cd).toString());
			List<BDLFourCustomer> list = JSONArray.parseArray(response, BDLFourCustomer.class);
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			res.setData(list);
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  }
	  return res;
   }
   
   	/**   
   	 * 查询四方信息 
   	 * @param 
   	 * @return 
   	 * @since JDK 1.8 
   	 */ 
   	@RequestMapping("/getFourCustomerList")
   	public WeixinResult getFourCustomerList(@RequestBody CBPOrder order){
   	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(order!=null && !StringUtil.isNullorEmpty(order.getCustomerid()+"")){
		 try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("customerid",order.getCustomerid());
			String response = HttpUtil.postbody(
				  systemConfig.getProperty("url.customer") + "/selectFourListByCustomerid",
				  jsonObject.toString());
			List<BDLFourCustomer> list = JSONArray.parseArray(response, BDLFourCustomer.class);
			res.setCode(CBPConstant.CODE_SUCCESS);
		    res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		    res.setData(list);
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  }
	  return res;
   	}
}
