/*
 * @(#)LogisticsController.java       Nov 24, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.LogistDTO;
import com.blchina.cbp.dto.LogisticsDTO;
import com.blchina.cbp.dto.LogisticsDTO.Logistics;
import com.blchina.cbp.dto.LogisticsStatusEnumDTO;
import com.blchina.cbp.model.CBPLogistics;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPTimeVisible;
import com.blchina.cbp.service.interfaces.ContractManageService;
import com.blchina.cbp.service.interfaces.LogisticsService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.mysql.jdbc.log.Log;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * 物流信息controller
 *
 * @author sunzhenzhen
 * @since JDK 1.8
 */

@RestController
@RequestMapping("/logistics")
public class LogisticsController {

   @Autowired
   LogisticsService logisticsService;
	@Autowired
	private ContractManageService contractManageService;
   @Autowired
   OrderService orderService;
   @Autowired
   WxService wxService;
   @Autowired
   private Properties systemConfig;


   @RequestMapping("/sap/addLogistics")
   public SAPResult addLogistics(@RequestBody LogisticsDTO logisticsDTO) {
	  CBPLogistics record = new CBPLogistics();
	  List<Logistics> logisticsList = logisticsDTO.getRecords();
	  SAPResult result = new SAPResult();
	  SAPResponse sapResponse = new SAPResponse();
	  sapResponse.setSAP_ID(logisticsList.get(0).getPRODUCT_GUID());
	   sapResponse.setBL_ID(logisticsList.get(0).getPRODUCT_GUID());
	   int logisticsID = 0;
	   //先给物流信息排序给客户推送最后一条
	   Collections.sort(logisticsList);
	   Logistics  logistics=logisticsList.get(logisticsList.size()-1);
	  //通过内部车辆码查询订单号
	  try {
		 List<CBPOrder> orderList = orderService
			   .getOrderByVinNo(logistics.getVHCLE());
		 if (orderList == null || orderList.size() < 1) {
			sapResponse.setStatus(
				  CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
			sapResponse.setDescription(CBPConstant.ORDER_NOEXIST);
		 }
		 else {
			try {
			   int ordersize = orderList.size();
			   CBPOrder getOrder = null;
			   for (int i = 0; i < ordersize; i++) {
				  String orderType = orderList.get(i).getOrdertype();
				  if ("350x".equalsIgnoreCase(orderType)
						|| "Q005".equalsIgnoreCase(orderType)) {
					 getOrder = orderList.get(i);
				  }
			   }
			   String vinno = logistics.getVINNO();
			   if (vinno != null) {
				   getOrder.setRealvinno(vinno);
				   //更新订单车架号
				   orderService.updateOrderBySapId(getOrder);
				   //上传订单车架号给合同管理系统
				   contractManageService.uploadOrderData(getOrder,null);
			   }
				String seq = logistics.getZZF2();
				if(!seq.equals("0000")){
					CBPLogistics cbpLogistics = new CBPLogistics();
					cbpLogistics.setSequence(seq);
					cbpLogistics.setOrderid(getOrder.getOrderid());
					cbpLogistics.setStatus(logistics.getSTATUS());
					CBPLogistics getLogistics = logisticsService.findLogisticsID(cbpLogistics);
					if(getLogistics==null){
						try{
							JSONObject jsonObject=new JSONObject();
							jsonObject.put("customerid",getOrder.getCustomerid());
							jsonObject.put("brandid",getOrder.getBrandid());
							String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObject.toString());
							String[] split = getcus.split(",");
							String customername = split[0]+",";
							String openid=split[1];
							String status = logistics.getSTATUS();
							if(status.equals("车辆已出库")){
								status="车辆已交付";
							}
							if(status.equals(CBPConstant.LogisticsStatusEnum.ARRIVED.getType())){
								customername=customername+systemConfig.getProperty("logistics.message.import.arrived");
							}else if(status.equals(CBPConstant.LogisticsStatusEnum.ROADING.getType())){
								customername=customername+systemConfig.getProperty("logistics.message.import.roading");
							}else if(status.equals(CBPConstant.LogisticsStatusEnum.PORTED.getType())){
								customername=customername+systemConfig.getProperty("logistics.message.import.ported");
							}else if(status.equals(CBPConstant.LogisticsStatusEnum.SENT.getType())){
								customername=customername+systemConfig.getProperty("logistics.message.import.sent");
							}else if(status.equals(CBPConstant.LogisticsStatusEnum.SCHEDULED.getType())){
								customername=customername+systemConfig.getProperty("logistics.message.import.scheduled");
							}else if(status.equals(CBPConstant.LogisticsStatusEnum.CONFIRMED.getType())){
								customername=customername+systemConfig.getProperty("logistics.message.import.confirmed");
							}else if(status.equals(CBPConstant.LogisticsStatusEnum.DELIVERY.getType())){
								customername=customername+systemConfig.getProperty("logistics.message.import.delivered");
							}
							Boolean flag = wxService.pushLogisticsStart(
									getOrder.getBrandid(), getOrder.getSaporderid(), openid,
									customername, getOrder.getOrderid(),
									getOrder.getCartype(), logistics.getSTATUS());
							if(flag){
								logistics.setSENDSTATUS("1");
							}else {
								 logistics.setSENDSTATUS("0");
							}
						}catch (Exception e){
							System.out.print("未获取到用户ipenid");
						}

					}
				}
				for(int i=0;i<logisticsList.size();i++){
					   Logistics getlogistics = logisticsList.get(i);
					   CBPLogistics newLogistics=new CBPLogistics();
					   newLogistics.setOrderid(getOrder.getOrderid());
					   newLogistics.setSenddate(getlogistics.getDATE());
					   newLogistics.setStatus(getlogistics.getSTATUS());
					   newLogistics.setCartype(getlogistics.getCARTYPE());
					   newLogistics.setSequence(getlogistics.getZZF2());
					    logisticsService.insertOrUpdateLogistic(newLogistics);
				   }
					 sapResponse.setStatus(
						   CBPConstant.SAPResponseStatusEnum.SUCCESS.getType());
					  sapResponse.setDescription(CBPConstant.MESSAGE_SUCCESS);


				   result.setRecords(sapResponse);
				   return result;

			}
			catch (Exception e) {
			   e.printStackTrace();
			   sapResponse.setStatus(
					 CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
			   sapResponse.setDescription(CBPConstant.MESSAGE_FAILURE);
			   result.setRecords(sapResponse);
			   return result;
			}

		 }
	  }
	  catch (Exception e) {
		 sapResponse
			   .setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
		 sapResponse.setDescription(e.getMessage().toString());
	  }
	  sapResponse.setBL_ID(logisticsID + "");
	  result.setRecords(sapResponse);
	  return result;
	  /*
	   * String urlPath = "http://ics.blchina.com/api/WebChat/sendTMsg"; JSONObject jsonObject = new JSONObject();
	   * jsonObject.put("",""); jsonObject.put("",""); jsonObject.put("",""); DefaultHttpClient httpClient = new
	   * DefaultHttpClient();
	   */
   }

   @RequestMapping(value = "/findbyorderid", method = RequestMethod.POST)
   public WeixinResult findByOrderID(@RequestBody CBPOrder order) {
	  WeixinResult weixinResult = new WeixinResult();
	   Integer orderid = order.getOrderid();
	   if(orderid==null){
		   weixinResult.setCode(CBPConstant.CODE_NULL_PARAM);
		   weixinResult.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
		   return weixinResult;
	   }
	   List<CBPLogistics> cbpLogistics = logisticsService
			.findCBPLogistics(orderid);
	   CBPOrder getOrder = orderService.getOrderById(orderid);
	   LogistDTO logistDTO=new LogistDTO();
	   logistDTO.setCartype(getOrder.getCartype());
	   logistDTO.setSaporderid(getOrder.getSaporderid());
	   logistDTO.setCbpLogistics(cbpLogistics);
	   if(cbpLogistics.size()==0){
		   weixinResult.setCode(CBPConstant.CODE_SUCCESS);
		   weixinResult.setMessage("暂无物流消息");
		   weixinResult.setData(logistDTO);
	   }else {
		 weixinResult.setCode(CBPConstant.CODE_SUCCESS);
		 weixinResult.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 weixinResult.setData(logistDTO);

	   }
	   return weixinResult;

	  }

	@RequestMapping("/test")
	public String test(){
     wxService.pushPayMoney(3,"1","2028");
		return " 232";
	}

}
