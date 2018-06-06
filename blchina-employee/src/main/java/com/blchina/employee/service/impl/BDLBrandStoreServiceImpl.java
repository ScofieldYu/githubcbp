package com.blchina.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;















import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.employee.dao.BDLBrandStoreMapper;
import com.blchina.employee.dao.EmployeeMapper;
import com.blchina.employee.datamodel.CBPConstant;
import com.blchina.employee.datamodel.CBPConstant.EmployeeTypeEnum;
import com.blchina.employee.datamodel.CBPConstant.StoreBrandEnum;
import com.blchina.employee.datamodel.weixin.WeixinResult;
import com.blchina.employee.model.BDLBrandStore;
import com.blchina.employee.model.BrandStore;
import com.blchina.employee.service.interfaces.BDLBrandStoreService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
@Service("/BDLBrandStoreService")
public class BDLBrandStoreServiceImpl implements BDLBrandStoreService{
	
	@Autowired
	private BDLBrandStoreMapper brandStoreMapper;
	@Autowired
	private EmployeeMapper employeeMapper;
	
	public List<BDLBrandStore> getAllBrandStore(BDLBrandStore employeeId) {
		
		BrandStore brandStore = new BrandStore();
		//根据员工id获取对应品牌门店信息
		 List<BDLBrandStore> allBrandStore = brandStoreMapper.getAllBrandStore(employeeId);
		 List<BDLBrandStore> allBrandStore1 = new ArrayList<BDLBrandStore>();
		 //根据员工id获取门店信息
		 String companyCode =  employeeMapper.getCompanyCodeByEmployeeId(employeeId);
		   //判断是否为集团职员
		   if (companyCode.equals("1001")) {
			   BDLBrandStore<BrandStore> bdlBrandStore;
			   HashMap<String,Object> hashMap = new HashMap<String, Object>();
			   Class<StoreBrandEnum> clz = StoreBrandEnum.class;
			   for (Object obj : clz.getEnumConstants()) {
				   bdlBrandStore= new BDLBrandStore<BrandStore>();
				   hashMap.put(((StoreBrandEnum) obj).getType(), obj );
				   String brandName = hashMap.get(String.valueOf(((StoreBrandEnum) obj).getType().toString())).toString();
				   bdlBrandStore.setBrandName(brandName);
				   List<BrandStore> getStoreCnShortByBrandType = brandStoreMapper.getBrandStore(((StoreBrandEnum) obj).getType().toString());
				   bdlBrandStore.setBrandType(Integer.valueOf(((StoreBrandEnum) obj).getType().toString()));
				   bdlBrandStore.setBrandName(brandName);
				   bdlBrandStore.setBrandStore(getStoreCnShortByBrandType);
				   bdlBrandStore.setTitleId(0);
				   allBrandStore1.add(bdlBrandStore);
			   }
			   
			   return allBrandStore1;
		   }
		   
		//根据员工id获取员工职责
		 Integer titleId =employeeMapper.getTitleIdByEmployeeId(employeeId);
		 //品牌名称和门店名称
		 for (BDLBrandStore bdlBrandStore : allBrandStore) {
			 
			 brandStore.setBrandId(bdlBrandStore.getBrandId());
			 brandStore.setStoreCnShort(bdlBrandStore.getStoreCnShort());
			 //获取品牌类型
			 Integer brandType = bdlBrandStore.getBrandType();
			 //获取品牌类型对应的品牌名称
			  HashMap<String,Object> hashMap = new HashMap<String, Object>();
			   Class<StoreBrandEnum> clz = StoreBrandEnum.class;
			   for (Object obj : clz.getEnumConstants()) {
				   hashMap.put(((StoreBrandEnum) obj).getType(), obj );
			   }
			   //根据品牌类型获取品牌名称
			   String brandName = hashMap.get(String.valueOf(brandType)).toString();
			   bdlBrandStore.setBrandType(brandType);
			   bdlBrandStore.setBrandName(brandName);
			   List<BrandStore> allBrandStores = new ArrayList<BrandStore>();
			   allBrandStores.add(brandStore);
			   
			   bdlBrandStore.setBrandStore(allBrandStores);
			   //存入品牌名称
			   bdlBrandStore.setEmployeeId(employeeId.getEmployeeId());
			   //根据员工id获取companyCode
			   if (titleId.equals(EmployeeTypeEnum.valueOf("EMPLOYEE_STOREMANAGER_CONSULTANT").getType())) {
					 bdlBrandStore.setTitleId(1); // 1--店总
				}else if(titleId.equals(EmployeeTypeEnum.valueOf("EMPLOYEE_SALES_DIRECTOR").getType())){
					bdlBrandStore.setTitleId(2);// 2--销售总监
				}else if(titleId.equals(EmployeeTypeEnum.valueOf("EMPLOYEE_SALES_MANAGER").getType())){
					bdlBrandStore.setTitleId(3);//3--销售经理
				}
				else if(titleId.equals(EmployeeTypeEnum.valueOf("EMPLOYEE_SALES_CONSULTANT").getType())){
					bdlBrandStore.setTitleId(4);//4--销售顾问
				}else {
					bdlBrandStore.setTitleId(5);//5--不属于角色
				}
		 }
		 return allBrandStore;
	}
	

}
