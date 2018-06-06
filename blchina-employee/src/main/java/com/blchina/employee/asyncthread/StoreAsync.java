/*   
 * @(#)StoreAsync.java       2017年12月15日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.asyncthread;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.employee.dto.BDLStoreDTO;
import com.blchina.employee.dto.FDDStoreDTO;
import com.blchina.employee.dto.StoreInfoDTO;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;


/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class StoreAsync implements AsyncBasic {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   public BDLStoreDTO bdlStoreDTO;
   
   public BDLStoreDTO getBdlStoreDTO() {
      return bdlStoreDTO;
   }

   public void setBdlStoreDTO(BDLStoreDTO bdlStoreDTO) {
      this.bdlStoreDTO = bdlStoreDTO;
   }

   @Override
   public void asyncTask() {
	  try {
		 if(bdlStoreDTO!=null){
			Properties systemConfig = new Properties();
			 InputStream stream = DepartmentAsync.class.getClassLoader()
				   .getResourceAsStream("config.properties");
			 systemConfig.load(stream);
			 String timestamp = HttpsUtil.getTimeStamp();
			 String temp = EncodeUtil
				   .getMD5(systemConfig.getProperty("fadada.appSecret").getBytes())
				   + timestamp + "";
			 String sign = FddEncryptTool.sha1(temp);
		 FDDStoreDTO fddStoreDTO = new FDDStoreDTO();
		 fddStoreDTO.setTimestamp(timestamp);
		 fddStoreDTO.setSign(sign);
		 int size = bdlStoreDTO.getRecords().size();
		 List<StoreInfoDTO> list = new ArrayList<StoreInfoDTO>();
			 for (int i = 0; i < size; i++) {
				try {
				  BDLStoreDTO.Store store = bdlStoreDTO.getRecords().get(i);
				  StoreInfoDTO storeInfoDTO = new StoreInfoDTO();
				  storeInfoDTO.setStoreId(Long.parseLong(store.getStoreid()));
				  storeInfoDTO.setBrandId(store.getBrandid());
				  storeInfoDTO.setStoreCnShort(store.getStorecnshort());
				  storeInfoDTO.setStoreEnShort(store.getStoreenshort());
				  storeInfoDTO.setStoreName(store.getStorename());
				  storeInfoDTO.setArea(store.getArea());
				  list.add(storeInfoDTO);
			   }
			   catch (Exception e) {
				  e.printStackTrace();
			   }
			 }
		 fddStoreDTO.setStoreInfoList(list);
		 JSONObject  getJSON=JSONObject.fromObject(fddStoreDTO);
		 String storestr = getJSON.toString();
		 String response = HttpUtil.postbody(systemConfig.getProperty("fadada.url")+"/store/save.do", storestr);
		 System.out.println(response);
		 JSONObject  getObject=JSONObject.fromObject(response);
		 boolean isSuccess = (boolean) getObject.get("isSuccess");
		 Object object = getObject.get("result");
		 if (!isSuccess) {
			logger.error((String) getObject.get("message")+object.toString());
		 }
	   }
	  }catch (Exception e) {
		e.printStackTrace();
	  }
   }

}
