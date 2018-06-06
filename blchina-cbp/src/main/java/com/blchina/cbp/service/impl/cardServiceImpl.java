/*
 * @(#)cardServiceImpl.java       2018年2月24日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.BDLTask;
import com.blchina.cbp.dto.BatchDeleteDTO;
import com.blchina.cbp.dto.ProcessDTO;
import com.blchina.cbp.exception.WXException;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.CardService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
/**
 * 卡片创建
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("CardService")
public class cardServiceImpl implements CardService {
    @Autowired
    protected Properties systemConfig;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WxService wxService;
    @Override
    public String saveCard(BDLCard card) {
        JSONObject getCard=JSONObject.fromObject(card);
        try {
            String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard",getCard.toString());
              return result;
        } catch (Exception e) {
           throw new WXException("创建卡片失败");
        }

    }
   @Override
   public String newCard(CBPOrder cbpOrder,String employeeid,String type, String type2) {
	  BDLCard card1 = new BDLCard();
		 card1.setCardtype(type);
		 card1.setCustomerid(cbpOrder.getCustomerid());
		 card1.setCustomername(cbpOrder.getCustomername());
		 card1.setDescription(type2);
		 card1.setEmployeeid(Integer.valueOf(employeeid));
		 card1.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
		 card1.setIsdeleted("0");
		 card1.setOrderid(cbpOrder.getOrderid());
		 String saveCard = saveCard(card1);
		 return saveCard;
   }
   
   public WeixinResult updateTaskList(BatchDeleteDTO bdd){
	  WeixinResult wr = new WeixinResult();
	  wr.setCode(CBPConstant.CODE_NULL_PARAM);
	  wr.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  try {
		 JSONObject jsonObject = JSONObject.fromObject(bdd);
		 String response = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateCardTaskList",
			   jsonObject.toString());
			JSONObject resObject = JSONObject.fromObject(response);
		    wr = (WeixinResult)JSONObject.toBean(resObject, WeixinResult.class);
		    //判断所完成任务是否完成    处理重复请求
		    if(wr!=null && "1".equals(wr.getCode())){
		       wr.setCode(CBPConstant.CODE_SUCCESS);
		       wr.setMessage("该子任务已完成");
		 	   return wr;
		    }
		    if(wr!=null && "0".equals(wr.getCode())){
		       wr.setCode(CBPConstant.CODE_SUCCESS);
		       wr.setMessage(CBPConstant.MESSAGE_SUCCESS);
		       return wr;
		    }
	  }
	  catch (Exception e) {
		throw new WXException("修改子任务失败");
	  }
	  return wr;
   }
   
   
   
   @Override
   public WeixinResult updateCardTaskList(BatchDeleteDTO bdd) {
	  WeixinResult result = null;
		 try {
			   String response1 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/getCardByCardId",
		    		 "{\"cardid\":" + bdd.getTaskList().get(0).getCardid() + "}");
		       JSONObject resObject1 = JSONObject.fromObject(response1);
		       BDLCard card = (BDLCard) JSONObject.toBean(resObject1, BDLCard.class);
		       if(card==null){
		    	  result = new WeixinResult<>();
		    	  result.setCode(CBPConstant.CODE_NULL_PARAM);
		    	  result.setMessage("未查询到此任务");
		    	  return result;
		       }
		       CBPOrder cbpOrder = orderService.getOrderById(card.getOrderid());
		       if(cbpOrder==null){
		    	  result = new WeixinResult<>();
		    	  result.setCode(CBPConstant.CODE_NULL_PARAM);
		    	  result.setMessage("未查询到该订单");
		    	  return result;
		       }
		       ProcessDTO processDTO = orderService.getOrderProcess(cbpOrder.getOrderid());
		       JSONObject jsonObjecta=new JSONObject();
		       jsonObjecta.put("customerid",cbpOrder.getCustomerid());
		       jsonObjecta.put("brandid",cbpOrder.getBrandid());
				String getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObjecta.toString());
				String[] split = getcus.split(",");
				String customername=split[0];
				String openid=split[1];
					//财务完成向精品顾问发准备精品消息
			       if(card!=null && card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMCOLLECTDEPOSIT.getType())){
			    	  if(processDTO!=null && !StringUtil.isNullorEmpty(processDTO.getBoutiqueId()+"")){
			    		 result = updateTaskList(bdd);
			    		 if(result.getCode().equals(CBPConstant.CODE_SUCCESS)){
			    			wxService.pushMessageToboutique(cbpOrder.getBrandid(), cbpOrder.getCustomername(), cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"));
			    			String newCard = newCard(cbpOrder,card.getEmployeeid().toString(),CBPConstant.CardTypeEnum.PREPAREBOUTIQUE.getType(), CBPConstant.CardTypeDescriptionEnum.PREPAREBOUTIQUE.getType());
			    		 }
			    	  }
						//财务完成向销售顾问,客户,承保顾问 发办理车险消息
			       }else if(card!=null && card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMCOLLECTBALANCEPAYMENT.getType())){
			    	  result = updateTaskList(bdd);
			    	  if(result.getCode().equals(CBPConstant.CODE_SUCCESS)){
			    		 if(processDTO!=null && !StringUtil.isNullorEmpty(processDTO.getInsuranceId()+"")){
			    			wxService.pushCardToEmployee(cbpOrder.getEmployeeid()+"", systemConfig.getProperty("url.cardDetailsUrl"), "请为"+cbpOrder.getCustomername()+"（订单编号"+cbpOrder.getOrderid()+"）办理车险）", "");
			    			String newCard = newCard(cbpOrder,card.getEmployeeid().toString(),CBPConstant.CardTypeEnum.TRANSACTINSURANCE.getType(), CBPConstant.CardTypeDescriptionEnum.TRANSACTINSURANCE.getType());
			    			//客户
			    			wxService.pushCardToCustomer(cbpOrder.getBrandid(), openid, customername, "开始为您办理车险", systemConfig.getProperty("url.orderDetailsUrl"), "办理车险");
			    			//承保顾问
			    			wxService.pushMessageToInsurance(cbpOrder.getBrandid(), customername, cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"));
			    			String newCard2 = newCard(cbpOrder, card.getEmployeeid().toString(),CBPConstant.CardTypeEnum.TRANSACTINSURANCEFORINSURANCE.getType(), CBPConstant.CardTypeDescriptionEnum.TRANSACTINSURANCEFORINSURANCE.getType());
			    		 //如果客户没办理车险 则发送验车专员办理临牌消息
			    		 }else if(processDTO!=null && !StringUtil.isNullorEmpty(processDTO.getCheckCarNumId()+"")){
			    		 //发送消息
			    			wxService.pushMessageToCheckCar(cbpOrder.getBrandid(), customername, cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"),"办理临牌");
			    			String newCard = newCard(cbpOrder, card.getEmployeeid().toString(),CBPConstant.CardTypeEnum.TRANSACTTEMPORARYLICENSE.getType(), CBPConstant.CardTypeDescriptionEnum.TRANSACTTEMPORARYLICENSE.getType());
			    	  	 }
			    	  }	//承保顾问完成  给客户，销售顾问 发办理完车险， 验车专员 发缴纳购置税
			       }else if(card!=null && card.getCardtype().equals(CBPConstant.CardTypeEnum.TRANSACTINSURANCEFORINSURANCE.getType())){
			    	  result = updateTaskList(bdd);
			    	  if(result.getCode().equals(CBPConstant.CODE_SUCCESS)){
			    		 if(processDTO!=null && !StringUtil.isNullorEmpty(processDTO.getCheckCarNumId()+"")){
			    			//验车专员 办理临牌消息
			    			wxService.pushMessageToCheckCar(cbpOrder.getBrandid(), customername, cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"), "办理临牌");
			    			String newCard = newCard(cbpOrder,card.getEmployeeid().toString(), CBPConstant.CardTypeEnum.TRANSACTTEMPORARYLICENSE.getType(), CBPConstant.CardTypeDescriptionEnum.TRANSACTTEMPORARYLICENSE.getType());
			    		 }
			    		 //验车专员 发缴纳购置税
			    		 wxService.pushMessageToCheckCar(cbpOrder.getBrandid(), customername, cbpOrder.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"),"缴纳购置税");
			    		 String newCard2 = newCard(cbpOrder,card.getEmployeeid().toString(), CBPConstant.CardTypeEnum.PAYMENTPURCHASETAX.getType(), CBPConstant.CardTypeDescriptionEnum.PAYMENTPURCHASETAX.getType());
			    		 //销售顾问  办理了车险 并将 所在任务置为完成 
			    		 wxService.pushCardToEmployee(cbpOrder.getEmployeeid()+"", systemConfig.getProperty("url.cardDetailsUrl"), "已为您的客户"+cbpOrder.getCustomername()+"（订单编号"+cbpOrder.getOrderid()+"）办理了车险）", "办理车险");
//			    		 String response4 = HttpUtil.postbody(
//			    			   systemConfig.getProperty("url.card") + "/card/getCardDetailsByCardId",
//			    			   "{\"cardid\":" + card.getCardid()+ "}");
//			    		 JSONObject jsonObject4 = JSONObject.fromObject(response4);
//			    		 List<BDLTask> BDLTaskList = (List<BDLTask>) jsonObject4.get("taskList");
//			    		 if(BDLTaskList==null||BDLTaskList.isEmpty()){
//			    			wr.setCode(CBPConstant.CODE_NULL_PARAM);
//			    			wr.setMessage("此卡片下没有子任务");
//			    			return wr;
//			    		 }
//			    		 for(BDLTask task:BDLTaskList){
//			    			if(CBPConstant.TaskTypeEnum.TRANSACTINSURANCEFORCUSTROMER.getType().equals(task.getTasktype())){
//			    			   BatchDeleteDTO batchDeletedto1 = new BatchDeleteDTO();
//			    			   BDLTask bdlTask1 = new BDLTask();
//			    			   bdlTask1.setCardid(card.getCardid());
//			    			   bdlTask1.setTasktype(CBPConstant.TaskTypeEnum.TRANSACTINSURANCEFORCUSTROMER.getType());
//			    			   bdlTask1.setTaskstatus("1");
//			    			   List<BDLTask> taskList1 = new ArrayList<BDLTask>();
//			    			   taskList1.add(bdlTask1);
//			    			   batchDeletedto1.setTaskList(taskList1);
//			    			   JSONObject jsonObject3 = JSONObject.fromObject(bdd);
//			    			   String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateCardTaskList",
//			    					 jsonObject3.toString());
//			    			   JSONObject resObjectResult = JSONObject.fromObject(result);
//			    			   wr = (WeixinResult)JSONObject.toBean(resObjectResult, WeixinResult.class);
//			    			   if(wr==null || !wr.getCode().equals("0")){
//			    				  wr.setCode(CBPConstant.CODE_NULL_PARAM);
//			    				  wr.setMessage("修改销售顾问任务失败");
//			    				  return wr;
//			    			   }
//			    			}
//			    		 }
			    		 //客户
			    		 wxService.pushCardToCustomer(cbpOrder.getBrandid(), openid, customername, "已为您办理了车险，并开始为您办理缴纳购置税业务，请您准备相关资料并设置自行办理临牌的次数和选号方式", systemConfig.getProperty("url.orderDetailsUrl"), "缴纳购置税");
			    		 //验车专员 缴纳购置税任务完成
			    	  }
			       }else if(card!=null && card.getCardtype().equals(CBPConstant.CardTypeEnum.PAYMENTPURCHASETAX.getType())){
			    	  result = updateTaskList(bdd);
			    	  if(result.getCode().equals(CBPConstant.CODE_SUCCESS)){	 
			    		 //销售顾问 发缴纳了购置税
			    		 wxService.pushCardToEmployee(cbpOrder.getEmployeeid()+"", systemConfig.getProperty("url.orderDetailsUrl"), "已为您的客户"+cbpOrder.getCustomername()+"（订单编号："+cbpOrder.getOrderid()+"）缴纳了购置税，请确定客户的选号方式", "缴纳了购置税");
			    		 //客户发消息
			    		 wxService.pushCardToCustomer(cbpOrder.getBrandid(), openid, customername, "已为您办理了缴纳购置税业务，开始为您办理临牌业务，请您准备相关资料并设置您已自行办理临牌的次数和选号方式", systemConfig.getProperty("url.orderDetailsUrl"), "缴纳购置税");
			    		 //销售顾问 发验车上牌
			    		 wxService.pushCardToEmployee(cbpOrder.getEmployeeid()+"", systemConfig.getProperty("url.cardDetailsUrl"), "请为"+cbpOrder.getCustomername()+"（订单编号："+cbpOrder.getOrderid()+"）办理验车上牌","办理临牌");
			    		 String newCard = newCard(cbpOrder,card.getEmployeeid().toString(), CBPConstant.CardTypeEnum.CHECKCARBAND.getType(), CBPConstant.CardTypeDescriptionEnum.CHECKCARBAND.getType());
			    		 if(processDTO!=null && !StringUtil.isNullorEmpty(processDTO.getCheckCarNumId()+"")){
			    			String newCard1 = newCard(cbpOrder,card.getEmployeeid().toString(), CBPConstant.CardTypeEnum.CHECKCARBANDFORCHECK.getType(), CBPConstant.CardTypeDescriptionEnum.CHECKCARBANDFORCHECK.getType());
			    			//客户 发消息
			    			wxService.pushCardToCustomer(cbpOrder.getBrandid(), openid, customername, "已为您办理了临牌业务，开始为您办理验车上牌，请您准备相关资料并设置选号方式", systemConfig.getProperty("url.orderDetailsUrl"), "办理临牌");
			    			//销售顾问 发办理临牌
			    			wxService.pushCardToEmployee(cbpOrder.getEmployeeid()+"", systemConfig.getProperty("url.orderDetailsUrl"), "已为您的客户"+customername+"（订单编号："+cbpOrder.getOrderid()+"）办理了临牌，请预约验车上牌时间并确定客户的选号方式", "办理临牌");
			    		 }
			    	  }
			    		 //验车专员办理临牌完成
			       }else if(card!=null && card.getCardtype().equals(CBPConstant.CardTypeEnum.TRANSACTTEMPORARYLICENSE.getType())){
			    	  result = updateTaskList(bdd);
			    	  if(result.getCode().equals(CBPConstant.CODE_SUCCESS)){
			    		 String newCard = newCard(cbpOrder,card.getEmployeeid().toString(), CBPConstant.CardTypeEnum.CHECKCARBANDFORCHECK.getType(), CBPConstant.CardTypeDescriptionEnum.CHECKCARBANDFORCHECK.getType());
			    		 //客户 发消息
			    		 wxService.pushCardToCustomer(cbpOrder.getBrandid(), openid, customername, "已为您办理了临牌业务，开始为您办理验车上牌，请您准备相关资料并设置选号方式", systemConfig.getProperty("url.orderDetailsUrl"), "办理临牌");
			    		 //销售顾问 发办理临牌
			    		 wxService.pushCardToEmployee(cbpOrder.getEmployeeid()+"", systemConfig.getProperty("url.orderDetailsUrl"), "已为您的客户"+customername+"（订单编号："+cbpOrder.getOrderid()+"）办理了临牌，请预约验车上牌时间并确定客户的选号方式", "办理临牌");
			    	  }
			       }
		 	}catch (Exception e) {
			throw new WXException("修改子任务失败");
		 }
	  return result;
   }
}
