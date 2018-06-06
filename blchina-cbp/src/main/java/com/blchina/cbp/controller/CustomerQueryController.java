/*   
 * @(#)CustomerQueryController.java       2018年1月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

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
import com.blchina.cbp.dto.BDLEmployee;
import com.blchina.cbp.dto.CustomerInfoQueryDTO;
import com.blchina.cbp.dto.CustomerMessageDTO;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.page.Page;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONObject;

/** 
 * 用户信息查询操作controller   
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/customerQuery")
public class CustomerQueryController {
   	
   @Autowired
   protected Properties systemConfig;
   @Autowired
   private OrderService orderService;
   
   @RequestMapping("/getCustomerInfo")
   public WeixinResult getCustomerInfo(@RequestBody CustomerInfoQueryDTO customerInfoQueryDTO){
	  WeixinResult res = new WeixinResult();
	  	res.setCode(CBPConstant.CODE_NULL_PARAM);
	  	res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  	List<BDLEmployee> employee = null;
	  	String employeeIdOwn = customerInfoQueryDTO.getEmployeeId();
	  	if(customerInfoQueryDTO!=null&&!StringUtil.isNullorEmpty(customerInfoQueryDTO.getEmployeeId())
	  		&&!StringUtil.isNullorEmpty(customerInfoQueryDTO.getAccountType())){
	  	 try {
			Integer employee1 = Integer.parseInt(customerInfoQueryDTO.getEmployeeId());
			String response = HttpUtil.postbody(
				  systemConfig.getProperty("url.employee") + "/ee/getEmployeeListByEmployeeId",
				  "{\"employeeid\":" + employee1 + "}");
			System.out.println("ES系统返回"+response);
			employee = JSONArray.parseArray(response, BDLEmployee.class);
		 if(employee.isEmpty() || employee.size()==1){
			customerInfoQueryDTO.setEmployeeIdOwn(employeeIdOwn);
			Page page1 = orderService.getOrderByEmployeeId(customerInfoQueryDTO);
		  	List<CBPOrder> datas1 = page1.getDatas();
		  	   	res.setCode(CBPConstant.CODE_SUCCESS);
				res.setMessage(CBPConstant.MESSAGE_SUCCESS);
				List<CustomerMessageDTO> cusList = new ArrayList<CustomerMessageDTO>();
				for(CBPOrder cbpOrder:datas1){
				   cusList.add(CBPOrder2CustomerMessageDTO(cbpOrder));
				}
				page1.setDatas(cusList);
				res.setData(page1);
				return res;
		 }
	  	 String customers = "";
	  	 List<String> list = new ArrayList<String>();
	  	 for(BDLEmployee cbpOrder:employee){
	  		list.add(cbpOrder.getEmployeeid().toString());
	  	 }
	  	 String t1, t2;
	  	 for (int i = 0; i < list.size(); i++) {
	  		t1 = (String) list.get(i);
	  		for (int j = list.size() - 1; j > i; j--) {
	  		   t2 = (String) list.get(j);
	  		   if (t1.equals(t2)) {
	  			  list.remove(j);
	  		   }
	  		}
	  	 }
	  	 for(String s:list){
	  		customers+=","+s;
	  	 }
	  	 String substring = customers.substring(1);
	  	 System.out.println("------------------------"+substring);
	  	customerInfoQueryDTO.setEmployeeId(substring);
	  	customerInfoQueryDTO.setEmployeeIdOwn(employeeIdOwn);
	  	Page page = orderService.getOrderByEmployeeId(customerInfoQueryDTO);
	  	List<CBPOrder> datas = page.getDatas();
	  	   List<CustomerMessageDTO> cusList = new ArrayList<CustomerMessageDTO>();
	  		  for(CBPOrder order: datas){
	  			 for(BDLEmployee bdlEmployee:employee){
	  			 if(order.getEmployeeid().intValue()==bdlEmployee.getEmployeeid().intValue()){
	  				   CustomerMessageDTO dto = CBPOrder2CustomerMessageDTO(order);
	  				   dto.setEmployeeId(bdlEmployee.getEmployeeid().toString());
	  				   dto.setEmployeeName(bdlEmployee.getEmployeename());
	  				   cusList.add(dto);
	  		  }
	  	   }
	  	}
	  		  page.setDatas(cusList);
	  		  res.setCode(CBPConstant.CODE_SUCCESS);
	  		  res.setMessage(CBPConstant.MESSAGE_SUCCESS);
	  		  res.setData(page); 
	  		return res;
	  	 }
		  catch (Exception e) {
			e.printStackTrace();
		  }
	  }
	  	return res;
   }
   
   public CustomerMessageDTO CBPOrder2CustomerMessageDTO(CBPOrder cbpOrder){
	  CustomerMessageDTO customerMessageDTO = new CustomerMessageDTO();
	  if(!StringUtil.isNullorEmpty(cbpOrder.getCustomername())){
		 customerMessageDTO.setCustomerName(cbpOrder.getCustomername());
	  }
	  if(!StringUtil.isNullorEmpty(cbpOrder.getIdcardnum())){
		 customerMessageDTO.setIdcardNum(cbpOrder.getIdcardnum());
	  }
	  if(!StringUtil.isNullorEmpty(cbpOrder.getPhonenumber())){
		 customerMessageDTO.setPhoneNumber(cbpOrder.getPhonenumber());
	  }
	  if(!StringUtil.isNullorEmpty(cbpOrder.getOrganizecode())){
		 customerMessageDTO.setOrganizeCode(cbpOrder.getOrganizecode());
	  }
	  if(!StringUtil.isNullorEmpty(cbpOrder.getOrganizename())){
		 customerMessageDTO.setOrganizeName(cbpOrder.getOrganizename());
	  }
	  return customerMessageDTO;
   }
   
}
