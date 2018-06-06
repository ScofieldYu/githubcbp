/*   
 * @(#)TitleAsync.java       2017年12月15日  
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
import com.blchina.common.util.string.StringUtil;
import com.blchina.employee.dto.BDLTitleDTO;
import com.blchina.employee.dto.FDDTitleDTO;
import com.blchina.employee.dto.TitleInfoDTO;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class TitleAsync implements AsyncBasic {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   public BDLTitleDTO bdlTitleDTO;
   
   public BDLTitleDTO getBdlTitleDTO() {
      return bdlTitleDTO;
   }

   public void setBdlTitleDTO(BDLTitleDTO bdlTitleDTO) {
      this.bdlTitleDTO = bdlTitleDTO;
   }

   @Override
   public void asyncTask() {
	  try {
		 if (bdlTitleDTO != null) {
			Properties systemConfig = new Properties();
			 InputStream stream = DepartmentAsync.class.getClassLoader()
				   .getResourceAsStream("config.properties");
			 systemConfig.load(stream);
			 String timestamp = HttpsUtil.getTimeStamp();
			 String temp = EncodeUtil
				   .getMD5(systemConfig.getProperty("fadada.appSecret").getBytes())
				   + timestamp + "";
			 String sign = FddEncryptTool.sha1(temp);
			int size = bdlTitleDTO.getRecords().size();
			FDDTitleDTO fddTitleDTO = new FDDTitleDTO();
			fddTitleDTO.setTimestamp(timestamp);
			fddTitleDTO.setSign(sign);
			List<TitleInfoDTO> list = new ArrayList<TitleInfoDTO>();
			for (int i = 0; i < size; i++) {
			   try {
			   BDLTitleDTO.Title title = bdlTitleDTO.getRecords().get(i);
			   TitleInfoDTO titleInfoDTO = new TitleInfoDTO();
				  titleInfoDTO.setTitleId(Long.parseLong(title.getSTELL()));
				  titleInfoDTO.setObjectName(title.getSTEXT1());
				  titleInfoDTO
				  	 .setStartDate(StringUtil.string2Date(title.getBEGDA()));
				  titleInfoDTO
				  	 .setEndDate(StringUtil.string2Date(title.getENDDA()));
				  list.add(titleInfoDTO);
			   }
			   catch (Exception e) {
				  e.printStackTrace();
			   }
			}
			fddTitleDTO.setTitleInfoList(list);
			JSONObject getJSON = JSONObject.fromObject(fddTitleDTO);
			String titlestr = getJSON.toString();
			String response = HttpUtil.postbody(
				  systemConfig.getProperty("fadada.url") + "/title/save.do",
				  titlestr);
			System.out.println(response);
			JSONObject getObject = JSONObject.fromObject(response);
			boolean isSuccess = (boolean) getObject.get("isSuccess");
			Object object = getObject.get("result");
			 if (!isSuccess) {
				logger.error((String) getObject.get("message"));
			 }
		 }
	  }
	  catch (Exception e) {
		 e.printStackTrace();
	  }
   }

}
