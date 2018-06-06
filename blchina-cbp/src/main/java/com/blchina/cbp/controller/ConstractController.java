/*
 * @(#)ConstractController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.blchina.cbp.model.CBPUnsignReason;
import com.blchina.cbp.service.interfaces.UnsignReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.BDLEmployee;
import com.blchina.cbp.dto.CustomerMessageDTO;
import com.blchina.cbp.dto.EmployeeListDTO;
import com.blchina.cbp.dto.SapOrderDTO;
import com.blchina.cbp.dto.StatisticalDTO;
import com.blchina.cbp.model.CBPCloseCause;
import com.blchina.cbp.model.CBPContract;
import com.blchina.cbp.service.interfaces.ConstractService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONObject;
/**
 * 合同管理
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/constract")
public class ConstractController {
    @Autowired
    private ConstractService constractService;
	@Autowired
	private UnsignReasonService unsignReasonService;
    @Autowired
    private Properties systemConfig;
    @RequestMapping("/sap/autoSign")
    public SAPResult autoSign(@RequestBody SapOrderDTO saporderDTO) {
     return constractService.autoSign(saporderDTO);
    }
    /**
     * 关闭合同
     * @param cbpCloseCause
     * @return
     */
    @RequestMapping("/closeContract")
    public WeixinResult closeContract(@RequestBody CBPCloseCause cbpCloseCause){
       WeixinResult result = new WeixinResult();
       result.setCode(CBPConstant.CODE_NULL_PARAM);
       result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
       if(cbpCloseCause!=null && cbpCloseCause.getContractid()!=null){
    	  int status = constractService.updateContract(cbpCloseCause);
    	  if(status!=0){
    		 result.setCode(CBPConstant.CODE_SUCCESS);
    		 result.setMessage(CBPConstant.MESSAGE_SUCCESS);
    		 return result;
    	  }
       }
       return result;
    }

    
    
    /**    
    * {统计关闭的合同}    
    *    1.按 关闭类型
    */ 
   @RequestMapping("/statisticalCloseCause")
    public WeixinResult statisticalCloseCause(@RequestBody CBPCloseCause cbpCloseCause){
	  WeixinResult result = new WeixinResult();
	  result.setCode(CBPConstant.CODE_NULL_PARAM);
      result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
      if(cbpCloseCause!=null && !StringUtil.isNullorEmpty(cbpCloseCause.getClosetype())){
    	 StatisticalDTO statisticalDTO = constractService.statisticalCloseCause(cbpCloseCause);
    	 if(statisticalDTO!=null){
    		result.setCode(CBPConstant.CODE_SUCCESS);
    	    result.setMessage(CBPConstant.MESSAGE_SUCCESS);
    	    result.setData(statisticalDTO);
    	 }
      }
      return result;
    }
    
    @RequestMapping("/selectCloseCauseList")
    public WeixinResult selectCloseCauseList(){
       WeixinResult result = new WeixinResult();
       result.setCode(CBPConstant.CODE_SUCCESS);
       result.setMessage(CBPConstant.MESSAGE_SUCCESS);
       List<CBPCloseCause> list = new ArrayList<CBPCloseCause>();
       CBPCloseCause c1 = new CBPCloseCause();
       c1.setClosetype(CBPConstant.CloseCauseTypeEnum.TYPE1.getType());
       c1.setDescription(CBPConstant.CLOSE_CAUSE_TYPE1);
       CBPCloseCause c2 = new CBPCloseCause();
       c2.setClosetype(CBPConstant.CloseCauseTypeEnum.TYPE2.getType());
       c2.setDescription(CBPConstant.CLOSE_CAUSE_TYPE2);
       CBPCloseCause c3 = new CBPCloseCause();
       c3.setClosetype(CBPConstant.CloseCauseTypeEnum.TYPE3.getType());
       c3.setDescription(CBPConstant.CLOSE_CAUSE_TYPE3);
       list.add(c1);
       list.add(c2);
       list.add(c3);
       result.setData(list);
       return result;
    }
	@RequestMapping("/selectUnsignReasonList")
	public WeixinResult selectUnsignReasonList(){
		WeixinResult result = new WeixinResult();
		result.setCode(CBPConstant.CODE_SUCCESS);
		result.setMessage(CBPConstant.MESSAGE_SUCCESS);
		List<CBPUnsignReason> list = new ArrayList<CBPUnsignReason>();
		CBPUnsignReason c1 = new CBPUnsignReason();
		c1.setClosetype(CBPConstant.UnsignReasonEnum.TYPE1.getType());
		c1.setDescription(CBPConstant.UnsignReasonDescriptionEnum.TYPE1.getType());
		CBPUnsignReason c2 = new CBPUnsignReason();
		c2.setClosetype(CBPConstant.UnsignReasonEnum.TYPE2.getType());
		c2.setDescription(CBPConstant.UnsignReasonDescriptionEnum.TYPE2.getType());
		CBPUnsignReason c3 = new CBPUnsignReason();
		c3.setClosetype(CBPConstant.UnsignReasonEnum.TYPE3.getType());
		c3.setDescription(CBPConstant.UnsignReasonDescriptionEnum.TYPE3.getType());
		list.add(c1);
		list.add(c2);
		list.add(c3);
		result.setData(list);
		return result;
	}
	@RequestMapping("/saveOrUpdateUnsignReason")
	public  WeixinResult  saveOrUpdateUnsignReason (@RequestBody CBPUnsignReason unsignReason){
           WeixinResult  res=new WeixinResult();
		  if(unsignReason.getContractid()==null||StringUtil.isNullorEmpty(unsignReason.getClosetype())){
			  res.setCode(CBPConstant.CODE_NULL_PARAM);
			  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			  return res;
		  }
		unsignReasonService.saveOrUpdateSignReason(unsignReason);
		res.setCode(CBPConstant.CODE_SUCCESS);
		res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 return  res;
	}


	@RequestMapping("/searchEmployee")
    public WeixinResult searchEmployee(@RequestBody EmployeeListDTO employeeListDTO){
       WeixinResult result = new WeixinResult();
       result.setCode(CBPConstant.CODE_NULL_PARAM);
       result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
       if(employeeListDTO.getEmployeeList().length==0){
    	  return result;
       }else{
		 try {
			 Object[] employeeList2 = employeeListDTO.getEmployeeList();
			String customers = "";
		  	 List<String> list = new ArrayList<String>();
		  	for(Object cmd :employeeList2){
		  	   list.add(cmd.toString());
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
		  	JSONObject getJson1=new JSONObject();
			 getJson1.put("employeeId", substring);
			String response = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeListByEmployee",
				  getJson1.toString());
			List<BDLEmployee> employeeList = JSONArray.parseArray(response, BDLEmployee.class);
//			BDLEmployee b = new BDLEmployee();
//			b.setEmployeename("全部");
//			employeeList.add(0, b);
			result.setCode(CBPConstant.CODE_SUCCESS);
		    result.setMessage(CBPConstant.MESSAGE_SUCCESS);
		    result.setData(employeeList);
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
    	  
    	  return result;
       }
    }
    
    
    @RequestMapping("/searchEmployeeByBrandId")
    public WeixinResult searchEmployeeByBrandId(@RequestBody EmployeeListDTO employeeListDTO){
       WeixinResult result = new WeixinResult();
       result.setCode(CBPConstant.CODE_NULL_PARAM);
       result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
       if(employeeListDTO.getBrandIdList().length==0){
    	  return result;
       }else{
    	  try {
			Object[] brandIdList = employeeListDTO.getBrandIdList();
			 String customers = "";
			 	List<String> list = new ArrayList<String>();
			 	for(Object cmd :brandIdList){
			 	   list.add(cmd.toString());
			 	}
			 	 for(String s:list){
			 		customers+=","+s;
			 	 }
			 	 String substring = customers.substring(1);
			 	JSONObject getJson1=new JSONObject();
			 getJson1.put("companycode", substring);
			String response = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeListByBrandId",
				  getJson1.toString());
			List<BDLEmployee> employeeList = JSONArray.parseArray(response, BDLEmployee.class);
//			BDLEmployee b = new BDLEmployee();
//			b.setEmployeename("全部");
//			employeeList.add(0, b);
			result.setCode(CBPConstant.CODE_SUCCESS);
			result.setMessage(CBPConstant.MESSAGE_SUCCESS);
			result.setData(employeeList);
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
       }
       
       return result;
    }
}
