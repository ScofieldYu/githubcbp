package com.blchina.employee.controller;

import java.util.HashMap;
import java.util.List;





import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.employee.datamodel.CBPConstant;
import com.blchina.employee.datamodel.CBPConstant.StoreBrandEnum;
import com.blchina.employee.model.AllResult;
import com.blchina.employee.model.BDLBrandStore;
import com.blchina.employee.service.interfaces.BDLBrandStoreService;

@RequestMapping("/brandstore")
@RestController
public class BrandStoreController {
   @Autowired
   private BDLBrandStoreService brandStoreService;
   /**
    * <pre>getStoreByBrandType(查询所有门店)   
    * 创建人：张桐   
    * @param brandType 品牌类型
    * @return</pre>
    */
   @RequestMapping("/store")
   @ResponseBody
   public AllResult getStoreByBrandType(@RequestBody BDLBrandStore employeeId){
	   AllResult res=new AllResult();
	   List<BDLBrandStore> storecnshortList = brandStoreService.getAllBrandStore(employeeId);
	   JSONArray jsonArray=JSONArray.fromObject(storecnshortList);
	   Integer titleId = storecnshortList.get(0).getTitleId();
	   res.setCode(CBPConstant.CODE_SUCCESS);
	   res.setMessage(CBPConstant.MESSAGE_SUCCESS);
	   res.setAccountType(titleId);
	   res.setData(jsonArray);
	   return res;
	  
   }
   
}