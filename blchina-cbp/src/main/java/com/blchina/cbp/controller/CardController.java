/*   
 * @(#)CardController.java       2018年2月2日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.cbp.controller;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.*;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.CardService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.page.Page;
import com.blchina.common.util.string.StringUtil;
import com.github.pagehelper.PageHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/** 
 * 卡片操作controller    
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/card")
public class CardController {
   
   @Autowired
   protected Properties systemConfig;
   @Autowired
   private OrderService orderService;
	@Autowired
	private CardService cardService;
	@Autowired
	private WxService wxService;
   @RequestMapping("/saveCard")
   public  WeixinResult saveCard(@RequestBody BDLCard card){
      WeixinResult res=new WeixinResult();
      if(card.getCustomerid()==null||card.getEmployeeid()==null||card.getCardtype()==null){
         res.setCode(CBPConstant.CODE_NULL_EMP);
         res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
         return res;
      }
      if(card.getOrderid()==null){//订单id为空创建订单
         CBPOrder order=new CBPOrder();
         order.setCustomerid(card.getCustomerid());
         order.setEmployeeid(card.getEmployeeid());
         orderService.insertOrder(order);
         card.setOrderid(order.getOrderid());
      }
      //获取员工所在门店
      try {
         String result=cardService.saveCard(card);
         JSONObject jsonObject=JSONObject.fromObject(result);
         res=(WeixinResult) JSONObject.toBean(jsonObject,WeixinResult.class);
         return res;
      } catch (Exception e) {
         e.printStackTrace();
         res.setCode(CBPConstant.CODE_FAILURE);
         res.setMessage(CBPConstant.MESSAGE_FAILURE);
         return res;
      }
   }

    @RequestMapping("/queryCardByEmployee")
    public  String queryCardByEmployee(@RequestBody CardDTO card){
        WeixinResult res=new WeixinResult();
        if(card.getEmployeeid()==null){
            res.setCode(CBPConstant.CODE_NULL_EMP);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return JSONObject.fromObject(res).toString();
        }
        //获取员工所在门店
        try {
            String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/queryCardByEmployee", JSONObject.fromObject(card).toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
            return JSONObject.fromObject(res).toString();
        }
    }

    @RequestMapping("/updateCardStatusDoing")
    public  String updateCardStatusDoing(@RequestBody JSONObject jsonObject) throws Exception {
        try {
            String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateCardStatusDoing",jsonObject.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("更新失败") ;
        }
    }
   @RequestMapping("/queryOrderByCustomerid")
   public  WeixinResult queryOrderByCustomerid(@RequestBody BDLCard card){
      WeixinResult res=new WeixinResult();
      if(card.getCustomerid()==null){
         res.setCode(CBPConstant.CODE_NULL_EMP);
         res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
         return res;
      }
       CBPOrder  order=new CBPOrder();
       order.setCustomerid(card.getCustomerid());
       List<CBPOrder> orderList = orderService.getOrderListByCustomerIdAndStatus(order);
       res.setCode(CBPConstant.CODE_SUCCESS);
       res.setMessage(CBPConstant.MESSAGE_SUCCESS);
       res.setData(orderList);
      return res;
   }
   
   
   
   /**    
    * {卡片搜索}    
    *    
    */ 
   @RequestMapping("/searchCardList")
   public WeixinResult searchCardList(@RequestBody CardDTO cardDTO){
	  WeixinResult wr = new WeixinResult();
	  wr.setCode(CBPConstant.CODE_NULL_PARAM);
	  wr.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(cardDTO!=null && !StringUtil.isNullorEmpty(cardDTO.getEmployeeId())){
		 try {
			//卡片搜索需模糊匹配 orderId，sapOrderId，phoneNumber，customerName
			if(StringUtil.isNullorEmpty(cardDTO.getCustomerName())){
			   	wr.setCode(CBPConstant.CODE_SUCCESS);
				wr.setMessage(CBPConstant.MESSAGE_SUCCESS);
				return wr;
			}
			List<CBPOrder> orderList = orderService.getOrderListBySearchCard(cardDTO);
			if(orderList.isEmpty()){
			   	wr.setCode(CBPConstant.CODE_SUCCESS);
				wr.setMessage(CBPConstant.MESSAGE_SUCCESS);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("datas", new ArrayList<>());
				wr.setData(jsonObject);
				return wr;
			}
			String customers = "";
		 	List<String> list = new ArrayList<String>();
		 	for(CBPOrder cmd :orderList){
		 	   list.add(cmd.getOrderid().toString());
		 	}
		 	 for(String s:list){
		 		customers+=","+s;
		 	 }
		 	 String substring = customers.substring(1);
		 	cardDTO.setCustomerName(substring);
			String response = HttpUtil.postbody(
				  systemConfig.getProperty("url.card") + "/card/getCardListByCardDTO",
			      JSONObject.fromObject(cardDTO).toString());
			JSONObject jsonObject = JSONObject.fromObject(response);
			List<BDLCard> cardList = (List<BDLCard>) jsonObject.get("datas");
			PagePojo pojo = new PagePojo();
			pojo.setCurrentPage((int)jsonObject.get("currentPage"));
			pojo.setPageSize((int)jsonObject.get("pageSize"));
			pojo.setTotalPage((int)jsonObject.get("totalPage"));
			pojo.setTotalRecord((int)jsonObject.get("totalRecord"));
			pojo.setDatas(cardList);
			wr.setCode(CBPConstant.CODE_SUCCESS);
			wr.setMessage(CBPConstant.MESSAGE_SUCCESS);
			wr.setData(pojo);
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  }
	  return wr;
   }
   
   
   /**    
    * {查询卡片详情}    
    */ 
   @RequestMapping("/selectCardDetails")
   public WeixinResult selectCardDetails(@RequestBody BDLCard bdlCard){
	  WeixinResult wr = new WeixinResult();
	  wr.setCode(CBPConstant.CODE_NULL_PARAM);
	  wr.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(bdlCard!=null && !StringUtil.isNullorEmpty(bdlCard.getCardid()+"")){
		 CardDetailsDTO cdd = new CardDetailsDTO();
		 try {
		 String response = HttpUtil.postbody(
			      systemConfig.getProperty("url.card") + "/card/getCardDetailsByCardId",
			      "{\"cardid\":" + bdlCard.getCardid()+ "}");
		 JSONObject jsonObject = JSONObject.fromObject(response);
		 	List<BDLTask> list = (List<BDLTask>) jsonObject.get("taskList");
		 	String response2 = HttpUtil.postbody(
			      systemConfig.getProperty("url.card") + "/card/getCardByCardId",
			      "{\"cardid\":" + bdlCard.getCardid()+ "}");
		 	JSONObject jsonObject3 = JSONObject.fromObject(response2);
		 	com.blchina.cbp.dto.BDLCard bdlCard1 = (com.blchina.cbp.dto.BDLCard) JSONObject.toBean(jsonObject3, com.blchina.cbp.dto.BDLCard.class);
		 	String response1 = null;
			Long phonenumber = null;
			   response1 = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerListByCustomerId",
			      "{\"customerId\":" + bdlCard1.getCustomerid().toString() + "}");
			   JSONObject jsonObject1 = JSONObject.fromObject(response1);
			   phonenumber = (Long) jsonObject1.get("phonenumber");
			   cdd.setBdlCard(bdlCard1);
			   cdd.setTaskList(list);
			   cdd.setPhoneNumber(phonenumber.toString());
			   wr.setCode(CBPConstant.CODE_SUCCESS);
			   wr.setMessage(CBPConstant.MESSAGE_SUCCESS);
			   wr.setData(cdd);
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
	  }
	  return wr;
   }
   
   /**    
    * {修改卡片预计完成时间}    
    */ 
   @RequestMapping("/updateCardTime")
   public WeixinResult updateCardTime(@RequestBody BDLCard bdlCard){
	  WeixinResult wr = new WeixinResult();
	  wr.setCode(CBPConstant.CODE_NULL_PARAM);
	  wr.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(bdlCard!=null && !StringUtil.isNullorEmpty(bdlCard.getCardid()+"")
			&& !StringUtil.isNullorEmpty(bdlCard.getEnddate())){
		 try {
			String response = HttpUtil.postbody(
			      systemConfig.getProperty("url.card") + "/card/updateCardTime",
			      JSONObject.fromObject(bdlCard).toString());
			if(response.equals("success")){
			   wr.setCode(CBPConstant.CODE_SUCCESS);
			   wr.setMessage(CBPConstant.MESSAGE_SUCCESS);
			}
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  }
	  return wr;
   }
   
   
   
   /**  
    * 更新卡片状态2.0(验车专员批量)  
    * @param bcld
    * @return 
    */ 
   @RequestMapping("/batchForCheckCarPerson")
   public WeixinResult batchForCheckCarPerson(@RequestBody BDLCardListDTO bcld){
	  WeixinResult wr = new WeixinResult();
	  wr.setCode(CBPConstant.CODE_NULL_PARAM);
	  wr.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  try {
		 String string = JSONObject.fromObject(bcld).toString();
         String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/batchForCheckCarPerson",string);
         JSONObject resObject = JSONObject.fromObject(result);
         wr = (WeixinResult)JSONObject.toBean(resObject, WeixinResult.class);
         return wr;
     } catch (Exception e) {
    	return wr;
     }
   }
   
   /**    
    * 
    * 批量删除（验车专员批量）
    * @param bdd
    * @return 
    * @since JDK 1.8 
    */ 
   @RequestMapping("/batchDelete")
   public WeixinResult batchDelete(@RequestBody BatchDeleteDTO bdd){
	  WeixinResult wr = new WeixinResult();
	  wr.setCode(CBPConstant.CODE_NULL_PARAM);
	  wr.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  Object[] cardIdList = bdd.getCardIdList();
	  String customers = "";
	 	List<String> list = new ArrayList<String>();
	 	for(Object cmd :cardIdList){
	 	   list.add(cmd.toString());
	 	}
	 	 for(String s:list){
	 		customers+=","+s;
	 	 }
	 	 String substring = customers.substring(1);
	 	JSONObject getJson1=new JSONObject();
	 getJson1.put("cardid", substring);
	 try {
	  String response = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/batchDeleteCard",
	  		  getJson1.toString());
	  JSONObject resObject = JSONObject.fromObject(response);
      wr = (WeixinResult)JSONObject.toBean(resObject, WeixinResult.class);
   }
   catch (Exception e) {
	  e.printStackTrace();
   }
	  return wr;
   }
   
   
   /**    
    * 修改子任务状态
    * @param bdd
    */ 
   @RequestMapping("/updateCardTaskList")
   public WeixinResult updateCardTaskList(@RequestBody BatchDeleteDTO bdd){
	  WeixinResult wr = new WeixinResult();
	  wr.setCode(CBPConstant.CODE_NULL_PARAM);
	  wr.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(bdd!=null && !bdd.getTaskList().isEmpty()){
		 WeixinResult weixinResult = cardService.updateCardTaskList(bdd);
		 return weixinResult;
	  }
	  return wr;
   }
   
   
   
}
