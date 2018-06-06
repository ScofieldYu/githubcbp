/*   
 * @(#)DepartmentSync.java       Dec 14, 2017  
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
import com.blchina.employee.dto.BDLDepartmentDTO;
import com.blchina.employee.dto.DepartmentInfoDTO;
import com.blchina.employee.dto.FDDDepartmentDTO;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;


/**
 * 此类功能描述
 *
 * @author Scofield
 * @since JDK 1.8
 */
public class DepartmentAsync implements AsyncBasic {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   public BDLDepartmentDTO bdlDepartmentDTO;

   @Override
   public void asyncTask() {
	  try {
		 Properties systemConfig = new Properties();
		 InputStream stream = DepartmentAsync.class.getClassLoader()
			   .getResourceAsStream("config.properties");
		 systemConfig.load(stream);
		 String timestamp = HttpsUtil.getTimeStamp();
		 String temp = EncodeUtil
			   .getMD5(systemConfig.getProperty("fadada.appSecret").getBytes())
			   + timestamp + "";
		 String sign = FddEncryptTool.sha1(temp);
		 FDDDepartmentDTO fddDepartmentDTO = new FDDDepartmentDTO();
		 fddDepartmentDTO.setTimestamp(timestamp);
		 fddDepartmentDTO.setSign(sign);
		 int size = bdlDepartmentDTO.getRecords().size();
		 List<DepartmentInfoDTO> list = new ArrayList<DepartmentInfoDTO>();
		 for (int i = 0; i < size; i++) {
			try {
			   BDLDepartmentDTO.Department department = bdlDepartmentDTO
					 .getRecords().get(i);
			   DepartmentInfoDTO departmentInfoDTO = new DepartmentInfoDTO();
			   if (!StringUtil.isNullorEmpty(department.getORGEH())) {
			   departmentInfoDTO
					 .setDeptId(Long.parseLong(department.getORGEH()));
			}
			departmentInfoDTO.setDeptName(department.getSTEXT());
			if (!StringUtil.isNullorEmpty(department.getUP_ORGEH())) {
			   departmentInfoDTO
					 .setParentId(Long.parseLong(department.getUP_ORGEH()));
			}
			if (!StringUtil.isNullorEmpty(department.getLEADER())) {
			   departmentInfoDTO
					 .setDeptLeader(Long.parseLong(department.getLEADER()));
			}
				list.add(departmentInfoDTO);
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
		 }
		 fddDepartmentDTO.setDeptInfoList(list);
		 JSONObject getJSON = JSONObject.fromObject(fddDepartmentDTO);
		 String departmentstr = getJSON.toString();
		 String response = HttpUtil.postbody(
			   systemConfig.getProperty("fadada.url")+"/dept/save.do",
			   departmentstr);
		 System.out.println(response);
		 JSONObject getObject = JSONObject.fromObject(response);
		 boolean isSuccess = (boolean) getObject.get("isSuccess");
		 Object object = getObject.get("result");
		 if (!isSuccess) {
			logger.error((String) getObject.get("message")+object.toString());
		 }
	  }
	  catch (Exception e) {
		 e.printStackTrace();
	  }

   }

   public BDLDepartmentDTO getBdlDepartmentDTO() {
	  return bdlDepartmentDTO;
   }

   public void setBdlDepartmentDTO(BDLDepartmentDTO bdlDepartmentDTO) {
	  this.bdlDepartmentDTO = bdlDepartmentDTO;
   }

}
