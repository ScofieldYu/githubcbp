/*   
 * @(#)SecondHandCarServiceImpl.java       2018年2月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blchina.cbp.dao.CBPOrderMapper;
import com.blchina.cbp.dao.CBPSecondHandCarOrderMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPSecondHandCarOrder;
import com.blchina.cbp.service.interfaces.CardService;
import com.blchina.cbp.service.interfaces.SecondHandCarService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;

import net.sf.json.JSONObject;
/** 
 * 此类功能描述    
 *
 * @author zhangtong 
 * @since JDK 1.7 
 */
@Service("/SecondHandCarService")
public class SecondHandCarServiceImpl implements SecondHandCarService{
@Autowired
private CBPSecondHandCarOrderMapper secondHandCarmapper;
@Autowired
private CBPOrderMapper orderMapper;
@Autowired
private WxService wxService;
@Autowired
private CardService cardService;
@Autowired
protected Properties systemConfig;
	@Override
	@Transactional
	public int saveSecondHandCarOrder(CBPSecondHandCarOrder record) {
		// TODO Auto-generated method stub
		return secondHandCarmapper.insert(record);
	}
   @Override
   public List<CBPSecondHandCarOrder> selectSecondHandCarByOrderId(
		 Integer orderid) {
	  return secondHandCarmapper.selectSecondHandCarByOrderId(orderid);
   }
   @Override
   public int updateReserveTime(CBPSecondHandCarOrder cshco) {
	  CBPSecondHandCarOrder secondHandCarOrder = secondHandCarmapper.selectByPrimaryKey(cshco.getSecondcarid());
	  if(secondHandCarOrder!=null){
		 secondHandCarOrder.setReservetime(cshco.getReservetime());
		 int i = secondHandCarmapper.updateByPrimaryKeySelective(secondHandCarOrder);
		 if(i!=0){
			try {
			   CBPOrder cbpOrder = orderMapper.selectByPrimaryKey(cshco.getOrderid());
			   wxService.pushMessageToSecondCarConsultant(cbpOrder.getBrandid(), cbpOrder.getCustomername(), cshco.getOrderid(), systemConfig.getProperty("url.orderDetailsUrl"),cshco.getReservetime());
			   JSONObject jsonObjecta=new JSONObject();
		       jsonObjecta.put("customerid",cbpOrder.getCustomerid());
		       jsonObjecta.put("brandid",cbpOrder.getBrandid());
				String getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObjecta.toString());
				String[] split = getcus.split(",");
				String customername=split[0];
				String openid=split[1];
				wxService.pushCardToCustomer(cbpOrder.getBrandid(), openid, cbpOrder.getCustomername(),"已将"+cbpOrder.getCustomername()+"（订单编号:"+cshco.getOrderid()+"）的二手车评估约至"+cshco.getReservetime()+"，请携带如下文件:XXXXXX", systemConfig.getProperty("url.orderDetailsUrl"), "二手车评估");
			}
			catch (Exception e) {
			   return 0;
			}
			return i;
		 }
	  }
	  return 0;
   }
	@Override
	public int updateSecondHandCarOrder(CBPSecondHandCarOrder cbpSecondHandCarOrder) {
		// TODO Auto-generated method stub
		return secondHandCarmapper.updateByOrderId(cbpSecondHandCarOrder);
	}
	
   @Override
   public int updatePrice(CBPSecondHandCarOrder cshco) {
	  CBPSecondHandCarOrder secondHandCarOrder = secondHandCarmapper.selectByPrimaryKey(cshco.getSecondcarid());
	  if(secondHandCarOrder!=null){
		 secondHandCarOrder.setPrice(cshco.getPrice());
		 int i = secondHandCarmapper.updateByPrimaryKeySelective(secondHandCarOrder);
		 if(i!=0){
			try {
			   CBPOrder cbpOrder = orderMapper.selectByPrimaryKey(cshco.getOrderid());
			   JSONObject jsonObjecta=new JSONObject();
		       jsonObjecta.put("customerid",cbpOrder.getCustomerid());
		       jsonObjecta.put("brandid",cbpOrder.getBrandid());
				String getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObjecta.toString());
				String[] split = getcus.split(",");
				String customername=split[0];
				String openid=split[1];
			  // String openid = "o8CFDwxzkFpUoT_gGmLZc82p9z-E";
				//给客户发微信消息
				wxService.pushCardToCustomer(cbpOrder.getBrandid(), openid, cbpOrder.getCustomername(),"已为您的车辆（车架号:"+cshco.getOrderid()+"）预估了价格"+cshco.getPrice()+"元", systemConfig.getProperty("url.orderDetailsUrl"), "二手车评估");
				//给二手车财务发 确认收款消息
				String toSecondCarFinance = wxService.pushMessageToSecondCarFinance(cbpOrder.getBrandid(), cbpOrder.getCustomername(), cshco.getOrderid(), systemConfig.getProperty("url.cardDetailsUrl"));
				String string = cardService.newCard(cbpOrder, toSecondCarFinance,CBPConstant.CardTypeEnum.AFFIRMCOLLECTMONEY.getType(), CBPConstant.CardTypeDescriptionEnum.AFFIRMCOLLECTMONEY.getType());
			}
			catch (Exception e) {
			  return 0;
			}
			return i;
		 }
	  }
	  return 0;
   }

}
