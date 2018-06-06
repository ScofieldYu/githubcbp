/*   
 * @(#)SecondHandCarController.java       2018年2月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.dao.CBPOrderMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPCar;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPSecondHandCarOrder;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.SecondHandCarService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.google.gson.JsonArray;

/** 
 * 此类功能描述    二手车置换
 *
 * @author zhangtong 
 * @since JDK 1.7 
 */
@RequestMapping("/secondHandCar")
@RestController
public class SecondHandCarController {
	@Autowired
	private SecondHandCarService secondHandCarService;
	@Autowired
	private WxService wxService;
    @Autowired
    protected Properties systemConfig;
    @Autowired
    private OrderService orderService;
    @Autowired
	private CBPOrderMapper cbpOrderMapper;
    
	/**
	 * 
	 * {方法的功能/动作描述}  添加置换二手车订单  
	 *    
	 * @param      {cbpSecondHandCarOrder}   {品牌，车系，车型，公里数，车架号，订单编号}   
	 * @return      {res}   {状态码}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong 
	 * @param cbpSecondHandCarOrder
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/saveSecondHandCarOrder")
	public WeixinResult saveSecondHandCarOrder(@RequestBody JSONObject jsonObject){
		WeixinResult res=new WeixinResult();
		String result = null;
		if (jsonObject==null||jsonObject.get("orderid")==null) {
			res.setCode(CBPConstant.CODE_NULL_PARAM);
			res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			return res;
		}else {
			Integer orderid = Integer.parseInt(jsonObject.get("orderid").toString()) ;//订单id
			//查询订单信息
			CBPOrder order = orderService.selectByPrimaryKey(orderid);
			String brandid = order.getBrandid();//门店id
			String customername = order.getCustomername();//客户姓名
			Integer employeeid = order.getEmployeeid();//精品顾问id
			Integer customerid = order.getCustomerid();
			String tourl = CBPConstant.BDLSELL_CARD;//跳转路径
			CBPSecondHandCarOrder cbpSecondHandCarOrder = new CBPSecondHandCarOrder();
			cbpSecondHandCarOrder.setOrderid(orderid);
			if (jsonObject.get("carbrand")!=null) {
				cbpSecondHandCarOrder.setCarbrand(jsonObject.get("carbrand").toString());
			} 
			if (jsonObject.get("cartype")!=null) {
				cbpSecondHandCarOrder.setCartype(jsonObject.get("cartype").toString());
			}
			if (jsonObject.get("carseries")!=null) {
				cbpSecondHandCarOrder.setCarseries(jsonObject.get("carseries").toString());
			}
			if (jsonObject.get("kilometers")!=null) {
				cbpSecondHandCarOrder.setKilometers(jsonObject.get("kilometers").toString());
			}
			if (jsonObject.get("realvinno")!=null) {
				cbpSecondHandCarOrder.setRealvinno(jsonObject.get("realvinno").toString());
			}
			if (jsonObject.get("price")!=null) {
				cbpSecondHandCarOrder.setPrice(jsonObject.get("price").toString());
			}
			if (jsonObject.get("dealprice")!=null) {
				cbpSecondHandCarOrder.setDealprice(jsonObject.get("dealprice").toString());
			}
			if (jsonObject.get("exchangemethod")!=null) {
				cbpSecondHandCarOrder.setExchangemethod(jsonObject.get("exchangemethod").toString());
			}
			String userid = "";
			try {
			    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserId", "{\"employeeid\":" + employeeid + "}");
			    JSONObject getJson = JSONObject.fromObject(result1);
			    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
			    String code = getRes.getCode();
			    if (code.equals(CBPConstant.CODE_SUCCESS)) {
			        userid = (String) getRes.getData();
			    }
			} catch (Exception e) {
			    res.setCode(CBPConstant.CODE_FAILURE);
			    res.setMessage(CBPConstant.MESSAGE_FAILURE);
			    return res;
			}
			String content = "请为"+customername+"(订单编号:"+orderid+")办理车辆置换";
			String title = "车辆置换";
			String url=CBPConstant.BDLSELL_CARD;
			//给销售顾问发送消息
			wxService.pushCardToEmployee(userid, url, content, title);//String userid, String url, String content,String title
			//评估师发送企业微信消息
			wxService.pushMessageToSecondCarConsultant1(brandid, customername, orderid, tourl);
			try {
				//获取评估师id
				String resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/userRole/getSecendCarEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
		        String[] splitOne = resultOne.split(",");
		        String insuranceEmployeeId=splitOne[0];
		        String insuranceEmployeeUserid="splitOne[1]";//splitOne[1];
				//卡片类型为客户办理车辆置换
				jsonObject.put("cardtype", CBPConstant.CardTypeEnum.CARPERMUTEFORASSESSMENT.getType());
				jsonObject.put("discription", CBPConstant.TaskTypeDescriptionEnum.CARPERMUTEFORCUSTROMER.getType());
				jsonObject.put("employeeid", insuranceEmployeeId);
	            jsonObject.put("customerid", customerid);
	            jsonObject.put("customername", customername);
			    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard", jsonObject.toString());
			    JSONObject getJson = JSONObject.fromObject(result1);
			    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
			    String code = getRes.getCode();
			    //判断是修改还是添加
			    if (jsonObject.get("secondcarid")!=null) {
			    	//修改状态为已完成
			    	cbpSecondHandCarOrder.setStatus(String.valueOf(CBPConstant.ModuleStatusEnum.PROCESSING.getType()));
			    	//二手车修改信息
			    	int a = secondHandCarService.updateSecondHandCarOrder(cbpSecondHandCarOrder);
					res.setCode(CBPConstant.CODE_SUCCESS);
		            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
					return res;
				}
				//判断是否为反复提交
				List<CBPSecondHandCarOrder> selectSecondHandCarByOrderId = secondHandCarService.selectSecondHandCarByOrderId(orderid);
				if (selectSecondHandCarByOrderId.size()>0) {
					res.setCode(CBPConstant.CODE_NULL_PARAM);
					res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
					return res;
				}
				cbpSecondHandCarOrder.setStatus(String.valueOf(CBPConstant.ModuleStatusEnum.PROCESSING.getType()));
				//二手车添加订单
				int a = secondHandCarService.saveSecondHandCarOrder(cbpSecondHandCarOrder);
			} catch (Exception e) {
			    res.setCode(CBPConstant.CODE_FAILURE);
			    res.setMessage(CBPConstant.MESSAGE_FAILURE);
			    return res;
			}
			
			res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			return res;
		}
	}
	
	/**
	 * 
	 * {方法的功能/动作描述}    根据订单id获取二手车信息
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param secondHandCarOrder
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/getSecondHandCarOrder")
	public WeixinResult getSecondHandCarOrder(@RequestBody JSONObject jsonObject){
		 WeixinResult res =new WeixinResult();
		 if (jsonObject.get("orderid")==null) {
			 res.setCode(CBPConstant.CODE_NULL_PARAM);
			 res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			 return res;
		 }
		
		 try {
				//卡片类型为车辆置换
			 	Integer orderid = Integer.parseInt(jsonObject.get("orderid").toString());
			 	/*jsonObject.put("cardtype", CBPConstant.CardTypeEnum.CARPERMUTEFORASSESSMENT.getType());
			    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/getCardByOrderIdAndCardType", jsonObject.toString());
			    if (result1==null) {
					res.setData(new JSONArray());
					return res;
				}
			    JSONObject getJson = JSONObject.fromObject(result1);
			    String cardstatus = getJson.get("cardstatus").toString();
			    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
			    String code = getRes.getCode();*/
			    List<CBPSecondHandCarOrder> cbpSecondHandCarOrderList = secondHandCarService.selectSecondHandCarByOrderId(orderid);
			    if (cbpSecondHandCarOrderList.size()==0) {
					res.setData(new JSONArray());
					res.setCode(CBPConstant.CODE_SUCCESS);
			        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			        return res;
				}
			    /*cbpSecondHandCarOrderList.get(0).setType(cardstatus);*/
			    JSONArray fromObject = JSONArray.fromObject(cbpSecondHandCarOrderList);
				 res.setCode(CBPConstant.CODE_SUCCESS);
		         res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		         res.setData(fromObject);
		         return res;
			} catch (Exception e) {
			    res.setCode(CBPConstant.CODE_FAILURE);
			    res.setMessage(CBPConstant.MESSAGE_FAILURE);
			    return res;
			}
		 
	}
	
	
	/**    
	 * 预约二手车评估完成时间   
	 *
	 */ 
	@RequestMapping("/appointmentSecondHandCar")
	public WeixinResult appointmentSecondHandCar(@RequestBody CBPSecondHandCarOrder cshco){
	   WeixinResult res=new WeixinResult();
	   res.setCode(CBPConstant.CODE_NULL_PARAM);
	   res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	   if(cshco!=null && !StringUtil.isNullorEmpty(cshco.getSecondcarid()+"")
			 && !StringUtil.isNullorEmpty(cshco.getOrderid()+"")){
		  //给评估师发消息
		  int status = secondHandCarService.updateReserveTime(cshco);
		  if(status!=0){
			 res.setCode(CBPConstant.CODE_SUCCESS);
			 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		  }
	   }
	   return res;
	}
	
	
	/**    
	 * 设置二手车评估价格   
	 *
	 */ 
	@RequestMapping("/setSecondHandCarPrice")
	public WeixinResult setSecondHandCarPrice(@RequestBody CBPSecondHandCarOrder cshco){
	   WeixinResult res=new WeixinResult();
	   res.setCode(CBPConstant.CODE_NULL_PARAM);
	   res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	   if(cshco!=null && !StringUtil.isNullorEmpty(cshco.getSecondcarid()+"")
			 && !StringUtil.isNullorEmpty(cshco.getOrderid()+"")){
		  //给评估师发消息
		  int status = secondHandCarService.updatePrice(cshco);
		  if(status!=0){
			 res.setCode(CBPConstant.CODE_SUCCESS);
			 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		  }
	   }
	   return res;
	}
}
