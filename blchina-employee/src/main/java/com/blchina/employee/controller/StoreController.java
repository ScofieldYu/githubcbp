/*   
 * @(#)StoreController.java       2017年11月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import com.blchina.employee.model.BDLStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.common.util.http.HttpUtil;
import com.blchina.employee.asyncthread.AsyncTask;
import com.blchina.employee.asyncthread.DepartmentAsync;
import com.blchina.employee.asyncthread.StoreAsync;
import com.blchina.employee.datamodel.CBPConstant;
import com.blchina.employee.datamodel.sap.SAPResponse;
import com.blchina.employee.datamodel.sap.SAPResult;
import com.blchina.employee.dto.BDLStoreDTO;
import com.blchina.employee.dto.FDDStoreDTO;
import com.blchina.employee.dto.StoreInfoDTO;
import com.blchina.employee.service.interfaces.BDLStoreService;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;


/**
 * 门店操作controller
 *
 * @author Administrator
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/store")
public class StoreController {

   @Autowired
   private BDLStoreService bdlStoreService;
   @Autowired
   protected Properties systemConfig;
   
   @Resource(name = "taskExecutor")  
   private TaskExecutor taskExecutor; 
   
   private final Logger logger = LoggerFactory.getLogger(this.getClass());


   /**
    * 门店下发接口
    * 
    * @param bdlStore
    * @return
    */
   @RequestMapping("/store")
   public SAPResult insertOrUpdateStore(@RequestBody BDLStoreDTO bdlStore) {
	  //保存到合同管理系统
	  int status = 0;
	  int size = bdlStore.getRecords().size();
	  SAPResult sapResult = new SAPResult();
	  List<SAPResponse> responseList = new ArrayList<SAPResponse>();
	  StoreAsync storeAsync = new StoreAsync();
	  storeAsync.setBdlStoreDTO(bdlStore);
	  taskExecutor.execute(new AsyncTask(storeAsync));
	  for (int i = 0; i < size; i++) {
		 BDLStoreDTO.Store store = bdlStore.getRecords().get(i);
		 SAPResponse record = new SAPResponse();
		 try {
			status = bdlStoreService.insertOrUpdateStore(store);
		 }
		 catch (Exception e) {
			record.setSAP_ID(store.getStoreid() + "");
			record.setBL_ID(store.getStoreid() + "");
			record.setStatus(CBPConstant.STATUS_ERROR);
			record.setDescription(e.getMessage().toString());
			status = 0;
			e.printStackTrace();
		 }

		 if (status != 0) {
			record.setSAP_ID(store.getStoreid() + "");
			record.setBL_ID(store.getStoreid() + "");
			record.setStatus(CBPConstant.STATUS_SUCCESS);
			record.setDescription(CBPConstant.DESCRIPTIOIN_SUCCESS);
		 }
		 responseList.add(record);
	  }
	  sapResult.setRecords(responseList);
	  return sapResult;
 }
   
   public void storeAsync(BDLStoreDTO bdlStore){
	  System.out.println("异步线程执行了");
	  
   }

	/**
	 * 获取门店signstoreid
	 * @param store
	 * @return
     */
	@RequestMapping("/getSignStoreId")
   public String getSignStoreId(@RequestBody BDLStore store){
	   BDLStore getStore= bdlStoreService.getSignStoreIdByBrandId(store.getBrandid());
	   if(getStore==null)
		   return null;
	   return getStore.getSignstoreid();
   }
	@RequestMapping("/getEmployeeBrandId")
	public BDLStore getEmployeeBrandId(@RequestBody BDLStore bdlStore){
	   BDLStore getStore = null;
	   if(bdlStore!=null){
		  getStore= bdlStoreService.getSignStoreIdByBrandId(bdlStore.getBrandid());
	   }
	   return getStore;
	}

	/**
	 * 获取门店signstoreid
	 * @param store
	 * @return
	 */
	@RequestMapping("/getPayNoticeUser")
	public String getPayNoticeUser(@RequestBody BDLStore store){
		BDLStore getStore= bdlStoreService.getSignStoreIdByBrandId(store.getBrandid());
		if(getStore==null)
			return null;
		return getStore.getZzf1();
	}

	/**
	 * 获取门店appid和secret
	 * @param store
	 * @return
	 */
	@RequestMapping("/getSecretStoreId")
	public String getSecretStoreId(@RequestBody BDLStore store){
		BDLStore getStore= bdlStoreService.getSignStoreIdByBrandId(store.getBrandid());
		if(getStore==null)
			return null;
		return getStore.getAppid()+","+getStore.getAppsecret()+","+getStore.getZzf2()+","+getStore.getZzf3()+","+getStore.getCardtemplateid();
	}
	/**
	 * 获取门店所有信息
	 * @return
	 */
	@RequestMapping("/getAllStore")
	public List<BDLStore> getAllStore(){
		List<BDLStore> list= bdlStoreService.getAllStore();
		if(list==null)
			return new ArrayList<>();
		return list;
	}
	
	
	@RequestMapping("/getstoreCnShortByStoreId")
	public String getstoreCnShortByStoreId(@RequestBody BDLStore bdlStore){
	   String string = bdlStoreService.getStoreIdByBrandId(bdlStore.getBrandid());
	   if(string==null){
		  return null;
	   }
	   return string;
	}
   
}







