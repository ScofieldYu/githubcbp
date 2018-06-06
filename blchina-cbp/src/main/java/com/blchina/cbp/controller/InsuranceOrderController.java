/*   
 * @(#)InsuranceOrderController.java       2018年2月6日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.BDLInsurance;
import com.blchina.cbp.dto.BDLInsuranceCompany;
import com.blchina.cbp.dto.DocumentModelDTO;
import com.blchina.cbp.dto.InsuranceDetailsDTO;
import com.blchina.cbp.dto.InsuranceOrderDTO;
import com.blchina.cbp.model.CBPInsuranceOrder;
import com.blchina.cbp.service.interfaces.InsuranceOrderService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONObject;

/** 
 * 订单 保险    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/insuranceOrder")
public class InsuranceOrderController {

   @Autowired
   private InsuranceOrderService insuranceOrderService;
   @Autowired
   private Properties systemConfig;
   @RequestMapping("/getInsuranceOrderListByOrderId")
   public List<CBPInsuranceOrder> getInsuranceOrderListByOrderId(@RequestBody DocumentModelDTO dmd){
	  return insuranceOrderService.selectInsuranceOrderListByOrderId(Integer.valueOf(dmd.getOrderId()));
   }
   
   
   /**    
    * {车险详情}    
    *    
    */ 
   @RequestMapping("/insuranceDetails")
   public WeixinResult insuranceDetails(@RequestBody InsuranceDetailsDTO insuranceDetailsDTO){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(insuranceDetailsDTO!=null && !StringUtil.isNullorEmpty(insuranceDetailsDTO.getOrderId())){
		 try {
			List<CBPInsuranceOrder> cbpInsuranceOrderList = insuranceOrderService.selectInsuranceOrderListByOrderId(Integer.valueOf(insuranceDetailsDTO.getOrderId()));
      		 if(cbpInsuranceOrderList.isEmpty()){
      			res.setCode(CBPConstant.CODE_NULL_PARAM);
      			res.setMessage(CBPConstant.ORDER_NO_INSURANCE);
      			return res;
      		 }
      		String customers = "";
      		List<BDLInsuranceCompany> bdlInsuranceCompanyList = new ArrayList<>();
   	  	 	List<String> stringlist = new ArrayList<String>();
   	  	 double totlePrice = 0;
   	  	 for(CBPInsuranceOrder cbpInsuranceOrder:cbpInsuranceOrderList){
   	  		totlePrice+=Double.valueOf(cbpInsuranceOrder.getExpense());
   	  		stringlist.add(cbpInsuranceOrder.getInsuranceid().toString());
      	  	BDLInsuranceCompany ic = new BDLInsuranceCompany();
      	  	ic.setInsurancecompanyid(cbpInsuranceOrder.getInsurancecompanyid());
      	  	ic.setInsurancecompanyname(cbpInsuranceOrder.getInsurancecompanyname());
      	  	bdlInsuranceCompanyList.add(ic);
   	  	 }
   	  	 String t1, t2;
   	  	 for (int i = 0; i < stringlist.size(); i++) {
   	  		t1 = (String) stringlist.get(i);
   	  		for (int j = stringlist.size() - 1; j > i; j--) {
   	  		   t2 = (String) stringlist.get(j);
   	  		   if (t1.equals(t2)) {
   	  			stringlist.remove(j);
   	  		   }
   	  		}
   	  	 }
   	  	 for(String s:stringlist){
   	  		customers+=","+s;
   	  	 }
   	  	 String substring = customers.substring(1);
   	  	 String response1 = null;	
		 List<BDLInsurance> insuranceList = null;
		 JSONObject getJson1=new JSONObject();
		 getJson1.put("insuranceid", substring);
			response1 = HttpUtil.postbody(
			      systemConfig.getProperty("url.insurance") + "/insurance/getInsuranceListByInsuranceId",
			      getJson1.toString());
			insuranceList = JSONArray.parseArray(response1, BDLInsurance.class);
			List<InsuranceOrderDTO> insuranceOrderDTOList = new ArrayList<>();
			for(CBPInsuranceOrder cbpInsuranceOrder:cbpInsuranceOrderList){
			   InsuranceOrderDTO dto = CBPInsuranceOrder2InsuranceOrderDTO(cbpInsuranceOrder);
			   for(BDLInsurance i:insuranceList){
				  if(i.getInsuranceid().intValue()==dto.getInsuranceid().intValue()){
					 dto.setInsurancetype(i.getInsurancetype());
					 insuranceOrderDTOList.add(dto);
				  }
			   }
			}
			DecimalFormat df = new DecimalFormat("00.00");
			String format = df.format(totlePrice);
			insuranceDetailsDTO.setTotal(format);
      		insuranceDetailsDTO.setInsuranceOrderDTOList(insuranceOrderDTOList);
      		insuranceDetailsDTO.setInsuranceCompanyList(bdlInsuranceCompanyList);
      		res.setCode(CBPConstant.CODE_SUCCESS);
      		res.setMessage(CBPConstant.MESSAGE_SUCCESS);
      		res.setData(insuranceDetailsDTO);
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  }
	  return res;
   }
   
   private static InsuranceOrderDTO CBPInsuranceOrder2InsuranceOrderDTO(CBPInsuranceOrder cio){
	  InsuranceOrderDTO iod = new InsuranceOrderDTO();
	  iod.setInsuranceorderid(cio.getInsuranceorderid());
	  iod.setInsurancecompanyid(cio.getInsurancecompanyid());
	  iod.setInsurancecompanyname(cio.getInsurancecompanyname());
	  iod.setInsuranceid(cio.getInsuranceid());
	  iod.setInsurancelimit(cio.getInsurancelimit());
	  iod.setInsurancename(cio.getInsurancename());
	  iod.setIsnodeductible(cio.getIsnodeductible());
	  iod.setOrderid(cio.getOrderid());
	  return iod;
   }
}
