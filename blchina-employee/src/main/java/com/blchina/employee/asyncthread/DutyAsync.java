/*   
 * @(#)DutyAsync.java       2017年12月15日  
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
import com.blchina.employee.dto.BDLDutyDTO;
import com.blchina.employee.dto.DutyInfoDTO;
import com.blchina.employee.dto.FDDDutyDTO;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class DutyAsync implements AsyncBasic{
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   public BDLDutyDTO bdlDutyDTO;
   
   public BDLDutyDTO getBdlDutyDTO() {
      return bdlDutyDTO;
   }

   public void setBdlDutyDTO(BDLDutyDTO bdlDutyDTO) {
      this.bdlDutyDTO = bdlDutyDTO;
   }

   @Override
   public void asyncTask() {
	  try {
		 if (bdlDutyDTO != null) {
			Properties systemConfig = new Properties();
			 InputStream stream = DepartmentAsync.class.getClassLoader()
				   .getResourceAsStream("config.properties");
			 systemConfig.load(stream);
			 String timestamp = HttpsUtil.getTimeStamp();
			 String temp = EncodeUtil
				   .getMD5(systemConfig.getProperty("fadada.appSecret").getBytes())
				   + timestamp + "";
			 String sign = FddEncryptTool.sha1(temp);
			int size = bdlDutyDTO.getRecords().size();
			FDDDutyDTO fddDutyDTO = new FDDDutyDTO();
			fddDutyDTO.setTimestamp(timestamp);
			fddDutyDTO.setSign(sign);
			List<DutyInfoDTO> list = new ArrayList<DutyInfoDTO>();
			for (int i = 0; i < size; i++) {
			   try {
				  BDLDutyDTO.Duty duty = bdlDutyDTO.getRecords().get(i);
				  DutyInfoDTO dutyInfoDTO = new DutyInfoDTO();
				  dutyInfoDTO.setDutyId(Long.parseLong(duty.getPLANS()));
				  dutyInfoDTO.setDutyName(duty.getSTEXT2());
				  if(!StringUtil.isNullorEmpty(duty.getORGEH())){
				    dutyInfoDTO.setDeptId(Long.parseLong(duty.getORGEH()));
				  }
				  dutyInfoDTO.setDeptName(duty.getSTEXT());
				  if(!StringUtil.isNullorEmpty(duty.getBEGDA())){
				    dutyInfoDTO.setStartDate(StringUtil.string2Date(duty.getBEGDA()));
				  }
				  if(!StringUtil.isNullorEmpty(duty.getENDDA())){
				    dutyInfoDTO.setEndDate(StringUtil.string2Date(duty.getENDDA()));
				  }
				  if(!StringUtil.isNullorEmpty(duty.getSTELL())){
				    dutyInfoDTO.setTitleId(Long.parseLong(duty.getSTELL()));
				  }
				  dutyInfoDTO.setTitleName(duty.getSTEXT1());
				  list.add(dutyInfoDTO);
			   }
			   catch (Exception e) {
				  e.printStackTrace();
			   }
			}
			fddDutyDTO.setDutyInfoList(list);
			JSONObject getJSON = JSONObject.fromObject(fddDutyDTO);
			String dutystr = getJSON.toString();
			String response = HttpUtil.postbody(
				  systemConfig.getProperty("fadada.url") + "/duty/save.do",
				  dutystr);
			System.out.println(response);
			JSONObject getObject = JSONObject.fromObject(response);
			boolean isSuccess = (boolean) getObject.get("isSuccess");
			Object object = getObject.get("result");
			 if (!isSuccess) {
				logger.error((String) getObject.get("message")+object.toString());
			 }
		 }
	  }
	  catch (Exception e) {
		 e.printStackTrace();
	  }
   }

}
