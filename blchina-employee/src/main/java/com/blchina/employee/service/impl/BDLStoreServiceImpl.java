/*   
 * @(#)BDLStoreServiceImpl.java       2017年11月23日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.blchina.common.util.http.HttpUtil;
import com.blchina.employee.dao.BDLBrandStoreMapper;
import com.blchina.employee.dao.BDLStoreMapper;
import com.blchina.employee.datamodel.CBPConstant.StoreBrandEnum;
import com.blchina.employee.dto.BDLStoreDTO;
import com.blchina.employee.dto.FDDStoreDTO;
import com.blchina.employee.dto.StoreInfoDTO;
import com.blchina.employee.model.BDLBrandStore;
import com.blchina.employee.model.BDLStore;
import com.blchina.employee.model.BrandStore;
import com.blchina.employee.service.interfaces.BDLStoreService;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("bdlStoreService")
public class BDLStoreServiceImpl implements BDLStoreService {
   @Autowired
   private BDLBrandStoreMapper brandStoreMapper;
   @Autowired
   private BDLStoreMapper bdlStoreMapper;
   /*@Autowired//@Resource(name = "taskExecutor")  
   private TaskExecutor taskExecutor; */
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   @Autowired
   protected Properties systemConfig;
   
   @Override
   public int insertOrUpdateStore(BDLStoreDTO.Store store) {
	  int status = 0;
		if(store != null && store.getStoreid() != null){
		   BDLStore selectByPrimaryKey = bdlStoreMapper.selectByPrimaryKey(Integer.parseInt(store.getStoreid()));
		   BDLStore bdlStore2 = BDLStoreServiceImpl.BDLStoreDTO2BDLStore(store);
		   if(selectByPrimaryKey != null){
				status = bdlStoreMapper.updateByPrimaryKey(bdlStore2);
			}else{
				status = bdlStoreMapper.insert(bdlStore2);
			}
		}
		return status;
   }

	/**
	 * 根据brandid获取门店signstoreid
	 * @param brandid
	 * @return
     */
	@Override
	public String getStoreIdByBrandId(String brandid) {
	   String string = "";
	   List<BDLBrandStore> allBrandStore1 = new ArrayList<BDLBrandStore>();
	   HashMap<String,Object> hashMap = new HashMap<String, Object>();
	   Class<StoreBrandEnum> clz = StoreBrandEnum.class;
	   BDLBrandStore<BrandStore> bdlBrandStore;
	   for (Object obj : clz.getEnumConstants()) {
		   bdlBrandStore= new BDLBrandStore<BrandStore>();
		   hashMap.put(((StoreBrandEnum) obj).getType(), obj );
		   String brandName = hashMap.get(String.valueOf(((StoreBrandEnum) obj).getType().toString())).toString();
		   bdlBrandStore.setBrandName(brandName);
		   List<BrandStore> getStoreCnShortByBrandType = brandStoreMapper.getBrandStore(((StoreBrandEnum) obj).getType().toString());
		   bdlBrandStore.setBrandType(Integer.valueOf(((StoreBrandEnum) obj).getType().toString()));
		   bdlBrandStore.setBrandName(brandName);
		   for(BrandStore BrandStore:getStoreCnShortByBrandType){
			  if(brandid.equals(BrandStore.getBrandId())){
				 bdlBrandStore.setStoreCnShort(BrandStore.getStoreCnShort());
			  }
		   }
		   bdlBrandStore.setBrandStore(getStoreCnShortByBrandType);
		   bdlBrandStore.setTitleId(0);
		   allBrandStore1.add(bdlBrandStore);
	   }
	   for(BDLBrandStore BDLBrandStore:allBrandStore1){
		  BDLStore bdlStore = bdlStoreMapper.getSignStoreIdByBrandId(brandid);
		  if(Integer.valueOf(bdlStore.getBrandType()).equals(BDLBrandStore.getBrandType())){
			 string = BDLBrandStore.getStoreCnShort()+","+BDLBrandStore.getBrandName()+","+BDLBrandStore.getBrandType();
		  }
	   }
	   return string; 
	}

	@Override
	public BDLStore getStoreByBrandId(String companycode) {
		return bdlStoreMapper.getSignStoreIdByBrandId(companycode);
	}

	@Override
	public List<BDLStore> getAllStore() {
		return bdlStoreMapper.getAllStore();
	}

	public static BDLStore BDLStoreDTO2BDLStore(BDLStoreDTO.Store store){
	  BDLStore bdlStore = new BDLStore();
	  bdlStore.setStoreid(Integer.parseInt(store.getStoreid()));
	  bdlStore.setBrandid(store.getBrandid());
	  bdlStore.setStorename(store.getStorename());
	  bdlStore.setStorecnshort(store.getStorecnshort());
	  bdlStore.setStoreenshort(store.getStorecnshort());
	  bdlStore.setArea(store.getArea());
	  bdlStore.setZzf1(store.getZZF1());
	  bdlStore.setZzf2(store.getZZF2());
	  bdlStore.setZzf3(store.getZZF3());
	  return bdlStore;
   }

   @Override
   public BDLStore getSignStoreIdByBrandId(String brandid) {
	  return bdlStoreMapper.getSignStoreIdByBrandId(brandid);
   }

   
}
