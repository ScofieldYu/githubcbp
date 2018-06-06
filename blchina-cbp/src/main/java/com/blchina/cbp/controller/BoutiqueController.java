/*   
 * @(#)BDLBoutiqueController.java       2018年2月24日  
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.CBPBoutiqueItemDTO;
import com.blchina.cbp.dto.CardDTO;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPBoutiqueItem;
import com.blchina.cbp.model.CBPBoutiqueOrder;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.BoutiqueItemService;
import com.blchina.cbp.service.interfaces.BoutiqueOrderService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;

/** 
 * 此类功能描述    
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@RequestMapping("/boutique")
@RestController
public class BoutiqueController {
	  @Autowired
	  protected Properties systemConfig;
	  @Autowired
	  private OrderService orderService;
	  @Autowired
	  private WxService wxService;
	  @Autowired 
	  private BoutiqueOrderService boutiqueOrderService;
	  @Autowired
	  private BoutiqueItemService boutiqueItemService;
	  /**
	   * 
	   * {方法的功能/动作描述}    根据boutiqueid查询精品信息
	   *    
	   * @param      {引入参数名}   {引入参数说明}   
	   * @return      {返回参数名}   {返回参数说明}    
	   * @exception   {说明在某情况下,将发生什么异常}   
	   *
	   * @author zhangtong
	   * @param jsonObject
	   * @return 
	   * @since JDK 1.7
	   */
	  @RequestMapping("/getBoutiqueListBySerialNumber")
	  public WeixinResult getBoutiqueListByBoutiqueId(@RequestBody JSONObject jsonObject){
		  WeixinResult res=new WeixinResult();
		  if (jsonObject.get("serialnumber")==null) {
			  res.setCode(CBPConstant.CODE_NULL_PARAM);
              res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
              return res;
		  }
		  try {
			  	String serialnumber = jsonObject.get("serialnumber").toString();
			    String result = HttpUtil.postbody(systemConfig.getProperty("url.boutique") + "/boutique/getBoutiqueListBySerialNumber", "{\"serialnumber\":" + serialnumber + "}");
			    JSONObject getJson = JSONObject.fromObject(result);
			    res.setCode(CBPConstant.CODE_SUCCESS);
		        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		        res.setData(getJson);
			} catch (Exception e) {
			    res.setCode(CBPConstant.CODE_FAILURE);
			    res.setMessage(CBPConstant.MESSAGE_FAILURE);
			    return res;
			}
		return res;
	  }
  	  /**
  	   * 
  	   * {方法的功能/动作描述}    根据订单id获取精品信息
  	   *    
  	   * @param      {引入参数名}   {引入参数说明}   
  	   * @return      {返回参数名}   {返回参数说明}    
  	   * @exception   {说明在某情况下,将发生什么异常}   
  	   *
  	   * @author zhangtong
  	   * @param jsonObject
  	   * @return
  	   * @throws Exception 
  	   * @since JDK 1.7
  	   */
      @RequestMapping("/getBoutiqueListByOrderId")
      public WeixinResult getBoutiqueListByOrderId(@RequestBody CBPBoutiqueOrder boutiqueOrder) throws Exception {
    	  WeixinResult res=new WeixinResult();
    	  if (boutiqueOrder.getOrderid()==null) {
    		  res.setCode(CBPConstant.CODE_NULL_PARAM);
              res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
              return res;
    	  }
          List<CBPBoutiqueOrder> boutiqueOrderList = boutiqueOrderService.selectBoutiqueByOrderId(boutiqueOrder.getOrderid());
          for (CBPBoutiqueOrder cbpBoutiqueOrder : boutiqueOrderList) {
        	  cbpBoutiqueOrder.setBoutiqueItemList(boutiqueItemService.selectBoutiqueItemList(cbpBoutiqueOrder));  
          }
          JSONArray fromObject = JSONArray.fromObject(boutiqueOrderList);
          res.setCode(CBPConstant.CODE_SUCCESS);
          res.setMessage(CBPConstant.MESSAGE_SUCCESS);
          res.setData(fromObject);
          return res;
      }
      /**
       * 
       * {方法的功能/动作描述}    添加精品信息
       *    
       * @param      {引入参数名}   {引入参数说明}   
       * @return      {返回参数名}   {返回参数说明}    
       * @exception   {说明在某情况下,将发生什么异常}   
       *
       * @author zhangtong
       * @param jsonObject
       * @return 
       * @since JDK 1.7
       */
      @Transactional
      @RequestMapping("/insertBoutiqueOrder")
      public WeixinResult insertBoutiqueOrder(@RequestBody CBPBoutiqueItemDTO boutiqueItem){
    	  WeixinResult res = new WeixinResult();
          int i = boutiqueOrderService.insertBoutiqueOrder(boutiqueItem);
          
          res.setCode(CBPConstant.CODE_SUCCESS);
          res.setMessage(CBPConstant.MESSAGE_SUCCESS);
          return res;
      }
      /**
       * 
       * {方法的功能/动作描述}    精品流程里验车专员点击完成后的发送消息和自动创建卡片
       *    
       * @param      {引入参数名}   {引入参数说明}   
       * @return      {返回参数名}   {返回参数说明}    
       * @exception   {说明在某情况下,将发生什么异常}   
       *
       * @author zhangtong
       * @param jsonObject
       * @return 
       * @since JDK 1.7
       */
      @RequestMapping("/saveCardAndSendMess")
      public WeixinResult saveCardAndSendMess(@RequestBody JSONObject jsonObject){
    	  WeixinResult res=new WeixinResult();
          try {
        	  Integer orderid = Integer.parseInt(jsonObject.get("orderid").toString()) ;//获取订单id
              CBPOrder order = orderService.selectByPrimaryKey(orderid);
              Integer employeeid = order.getEmployeeid();
      		  String brandid = order.getBrandid();
      		  String customername = order.getCustomername();
      		  Integer customerid = order.getCustomerid();
              String tourl = CBPConstant.BDLSELL_CARD;
              String userid = "";
              String cardtype = CBPConstant.CardTypeDescriptionEnum.INSTALLBOUTIQUE.getType();
             //给销售顾问创建任务
              jsonObject.put("employeeid", employeeid);
              jsonObject.put("customerid", customerid);
              jsonObject.put("cardtype", cardtype);
              jsonObject.put("customername", customername);
              String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard",jsonObject.toString());
             //获取门店的精品顾问id
  			 String resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/userRole/getboutiqueEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
  	         String[] splitOne = resultOne.split(",");
  	         String insuranceEmployeeId=splitOne[0];
  	         String insuranceEmployeeUserid="splitOne[1]";//splitOne[1];
  	         //给精品顾问创建任务
  	         jsonObject.put("employeeid", insuranceEmployeeId);
             jsonObject.put("customerid", customerid);
             jsonObject.put("cardtype", cardtype);
             jsonObject.put("customername", customername);
  	         String result2 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard",jsonObject.toString());
  	         
				try {
				    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserId", "{\"employeeid\":" + employeeid + "}");
				    JSONObject getJson = JSONObject.fromObject(result1);
				    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
				    String code = getRes.getCode();
				    if (code.equals(CBPConstant.CODE_SUCCESS)) {
				        userid = (String) getRes.getData();
				    }else {
				    	res.setCode(CBPConstant.CODE_FAILURE);
					    res.setMessage(CBPConstant.MESSAGE_FAILURE);
					    return res;
					}
				} catch (Exception e) {
				    res.setCode(CBPConstant.CODE_FAILURE);
				    res.setMessage(CBPConstant.MESSAGE_FAILURE);
				    return res;
				}
				String content = "请为"+customername+"(订单编号:"+orderid+")加装精品";
				String title = "加装精品";
				String url=CBPConstant.BDLSELL_CARD;
				//给销售顾问发送企业微信消息
				wxService.pushCardToEmployee(userid, url, content, title);//String userid, String url, String content,String title
				//给精品顾问发送企业微信消息
      		    wxService.pushMessageToboutique(brandid, customername, orderid, tourl);
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                return res;
          } catch (Exception e) {
              e.printStackTrace();
              res.setCode(CBPConstant.CODE_FAILURE);
              res.setMessage(CBPConstant.MESSAGE_FAILURE);
          }
          return res;
      }
      /**
       * 
       * {方法的功能/动作描述}    精品顾问点击精品完成按钮后将子任务设置为完成
       *    					发送消息给销售顾问进行预约交车,发送消息客户
       * @param      {引入参数名}   {引入参数说明}   
       * @return      {返回参数名}   {返回参数说明}    
       * @exception   {说明在某情况下,将发生什么异常}   
       *
       * @author zhangtong
       * @param jsonObject
       * @return 
       * @since JDK 1.7
       */
      @RequestMapping("/completeBoutique")
      public WeixinResult completeBoutique(@RequestBody JSONObject jsonObject){
    	  WeixinResult res=new WeixinResult();
    	  if (jsonObject.get("orderid")==null) {
           	  res.setCode(CBPConstant.CODE_NULL_PARAM);
              res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
              return res;
          }
          Integer orderid = (Integer) jsonObject.get("orderid");
          CBPOrder order = orderService.selectByPrimaryKey(orderid);
  	   	  String brandid = order.getBrandid();
  		  String customername = order.getCustomername();
  		  Integer employeeid = order.getEmployeeid();
  		  Integer customerid = order.getCustomerid();
          String tourl = CBPConstant.BDLSELL_CARD;
          
		try {
			//获取精品顾问id
			String resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/userRole/getboutiqueEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
	        String[] splitOne = resultOne.split(",");
	        String insuranceEmployeeId=splitOne[0];
	        String insuranceEmployeeUserid="splitOne[1]";//splitOne[1];
	        //将精品顾问的子任务置为完成
			jsonObject.put("tasktype", CBPConstant.TaskTypeEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
			jsonObject.put("employeeid", insuranceEmployeeId);
            String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateTaskStatusComplete",jsonObject.toString());
            //将销售顾问的子任务置为完成
            jsonObject.put("tasktype", CBPConstant.TaskTypeEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
			jsonObject.put("employeeid", employeeid);
            String result1 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateTaskStatusComplete",jsonObject.toString());
            JSONObject jsonArray=JSONObject.fromObject(result);
           //获取userid
			String userid = "";
		    String result2 = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserId", "{\"employeeid\":" + employeeid + "}");
		    JSONObject getJson = JSONObject.fromObject(result1);
		    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
		    String code = getRes.getCode();
		    if (code.equals(CBPConstant.CODE_SUCCESS)) {
		        userid = (String) getRes.getData();
		    }
		    String content = "以完成为客户"+customername+"(订单编号:"+orderid+")加装精品,请联系客户进行预约交车";
			String title = "加装精品";
			String url=CBPConstant.BDLSELL_CARD;
			//给销售顾问发送企业微信
			wxService.pushCardToEmployee(userid, url, content, title);//String userid, String url, String content,String title
//            //给精品顾问发送企业微信消息
//    		wxService.pushMessageToboutique(brandid, customername, orderid, tourl);
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
		} catch (Exception e) {
		    res.setCode(CBPConstant.CODE_FAILURE);
		    res.setMessage(CBPConstant.MESSAGE_FAILURE);
		    return res;
		}
      }
      /**
       * 
       * {方法的功能/动作描述}    预约加装精品时间
       *    					
       * @param      {引入参数名}   {引入参数说明}   
       * @return      {返回参数名}   {返回参数说明}    
       * @exception   {说明在某情况下,将发生什么异常}   
       *
       * @author zhangtong
       * @param jsonObject
       * @return 
       * @since JDK 1.7
       */
      @RequestMapping("/appointmentBoutiqueTime")
      public WeixinResult appointmentBoutiqueTime(@RequestBody JSONObject jsonObject){
    	  WeixinResult res=new WeixinResult();
          try {
        	  if (jsonObject.get("orderid")==null||jsonObject.get("createtime")==null) {
        		  res.setCode(CBPConstant.CODE_NULL_PARAM);
     			  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
     			  return res;
        	  }
        	  String createtime = jsonObject.get("createtime").toString();//获取createTime
        	  Integer orderid = (Integer) jsonObject.get("orderid");//获取orderid
        	  CBPOrder order = orderService.selectByPrimaryKey(orderid);
      		  String brandid = order.getBrandid();
      		  String customername = order.getCustomername();
      		  Integer customerid = order.getCustomerid();
      		  Integer employeeid = order.getEmployeeid();
              String tourl = CBPConstant.ORDER_DETAILS;
        	  CBPBoutiqueOrder cbpBoutiqueOrder = new CBPBoutiqueOrder();
        	  cbpBoutiqueOrder.setOrderid(orderid);
        	  cbpBoutiqueOrder.setCreatetime(createtime);
        	  //将预约加装精品时间存入精品订单中
        	  int i = boutiqueOrderService.updateCreateTime(cbpBoutiqueOrder);
        	  //将子任务置为完成
        	  jsonObject.put("tasktype", CBPConstant.TaskTypeEnum.APPOINTMENTINSTALLBOUTIQUETIME.getType());
        	  jsonObject.put("cardtype", CBPConstant.CardTypeEnum.TRANSACTFINANCIALSTAGE.getType());
			  jsonObject.put("discription", CBPConstant.CardTypeDescriptionEnum.TRANSACTFINANCIALSTAGE.getType());
			  jsonObject.put("employeeid", employeeid);
              jsonObject.put("customerid", customerid);
              jsonObject.put("customername", customername);
              String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateTaskStatusComplete",jsonObject.toString());
              jsonObject.put("tasktype", CBPConstant.TaskTypeEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
	          //给精品顾问发送企业微信消息
	  		  wxService.pushMessageToboutique1(brandid, customername, orderid, tourl);
	          res.setCode(CBPConstant.CODE_SUCCESS);
	          res.setMessage(CBPConstant.MESSAGE_SUCCESS);
	          return res;
          } catch (Exception e) {
              e.printStackTrace();
              res.setCode(CBPConstant.CODE_FAILURE);
              res.setMessage(CBPConstant.MESSAGE_FAILURE);
          }
          return res;
      }
      /**
       * 
       * {方法的功能/动作描述}    删除选中精品
       *    
       * @param      {引入参数名}   {引入参数说明}   
       * @return      {返回参数名}   {返回参数说明}    
       * @exception   {说明在某情况下,将发生什么异常}   
       *
       * @author zhangtong
       * @param boutiqueOrder
       * @return 
       * @since JDK 1.7
       */
      @RequestMapping("/deleteBoutiqueByOrderIdAndBoutiqueName")
      public WeixinResult deleteBoutiqueByOrderIdAndBoutiqueName(@RequestBody CBPBoutiqueOrder boutiqueOrder){
    	  WeixinResult res=new WeixinResult();
    	  int i = boutiqueOrderService.deleteBoutiqueOrderByBoutiqueId(boutiqueOrder);
    	  res.setCode(CBPConstant.CODE_SUCCESS);
          res.setMessage(CBPConstant.MESSAGE_SUCCESS);
    	  return res;
      }
      
      
}
