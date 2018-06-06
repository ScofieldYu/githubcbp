/*   
 * @(#)EmployeeAsync.java       2017年12月15日  
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
import com.blchina.employee.datamodel.CBPConstant;
import com.blchina.employee.dto.BDLEmployeeDTO;
import com.blchina.employee.dto.EmployeeInfoDTO;
import com.blchina.employee.dto.FDDEmployeeDTO;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class EmployeeAsync implements AsyncBasic {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   public BDLEmployeeDTO bdlEmployeeDTO;
   
   public BDLEmployeeDTO getBdlEmployeeDTO() {
      return bdlEmployeeDTO;
   }

   public void setBdlEmployeeDTO(BDLEmployeeDTO bdlEmployeeDTO) {
      this.bdlEmployeeDTO = bdlEmployeeDTO;
   }

   @Override
   public void asyncTask() {
	  try {
		//判断邮箱是否为空
		  if(bdlEmployeeDTO!=null&&!bdlEmployeeDTO.getRecords().isEmpty()){
			 List<BDLEmployeeDTO.Employee> employeeList = new ArrayList<BDLEmployeeDTO.Employee>();
			 for(BDLEmployeeDTO.Employee employee :bdlEmployeeDTO.getRecords()){
				if(CBPConstant.EMPLOYEE_STATUS.equals(employee.getZLZ_FLAG())){
				   if(!StringUtil.isNullorEmpty(employee.getE_MAIL())){
					  employeeList.add(employee);
				   }
				}else{
				   employeeList.add(employee);
				}
			 }
			 bdlEmployeeDTO.setRecords(employeeList);
		  }
		 if (bdlEmployeeDTO != null) {
			Properties systemConfig = new Properties();
			 InputStream stream = DepartmentAsync.class.getClassLoader()
				   .getResourceAsStream("config.properties");
			 systemConfig.load(stream);
			 String timestamp = HttpsUtil.getTimeStamp();
			 String temp = EncodeUtil
				   .getMD5(systemConfig.getProperty("fadada.appSecret").getBytes())
				   + timestamp + "";
			 String sign = FddEncryptTool.sha1(temp);
			int size = bdlEmployeeDTO.getRecords().size();
			FDDEmployeeDTO fddEmployeeDTO = new FDDEmployeeDTO();
			fddEmployeeDTO.setTimestamp(timestamp);
			fddEmployeeDTO.setSign(sign);
			List<EmployeeInfoDTO> list = new ArrayList<EmployeeInfoDTO>();
			for (int i = 0; i < size; i++) {
			   try {
				  BDLEmployeeDTO.Employee employee = bdlEmployeeDTO.getRecords()
				  	 .get(i);
				  EmployeeInfoDTO employeeInfoDTO = new EmployeeInfoDTO();
				  employeeInfoDTO.setId(Long.parseLong(employee.getPERNR()));
				  employeeInfoDTO.setUpDomainAccount(employee.getCOMM_ID_LONG());
				  employeeInfoDTO
				  	 .setBirth(StringUtil.string2Date(employee.getGBDAT()));
				  employee.getGBDAT();
				  employeeInfoDTO.setCompanyCode(employee.getBUKRS());
				  if(!StringUtil.isNullorEmpty(employee.getORGEH())){
				    employeeInfoDTO.setDeptId(Long.parseLong(employee.getORGEH()));
				  }
				  if(!StringUtil.isNullorEmpty(employee.getPLANS())){
				    employeeInfoDTO.setDutyId(Long.parseLong(employee.getPLANS()));
				  }
				  employeeInfoDTO.setEmail(employee.getE_MAIL());
				  employeeInfoDTO
				  	 .setEnterDate(StringUtil.string2Date(employee.getBEGDA()));
				  employeeInfoDTO.setMobile(employee.getTEL_PHONE());
				  if (employee.getGESCH().equals(CBPConstant.SAP_SEX_MAN)) {
				    employeeInfoDTO.setSex(CBPConstant.FDD_SEX_MAN);
				  }
				  else if (employee.getGESCH().equals(CBPConstant.SAP_SEX_WOMAN)) {
				    employeeInfoDTO.setSex(CBPConstant.FDD_SEX_WOMAN);
				  }
				  else {
				    employeeInfoDTO.setSex(CBPConstant.SAP_SEX_NO_MAINTENANCE);
				  }
				  if(!StringUtil.isNullorEmpty(employee.getZLZ_FLAG())){
				    employeeInfoDTO
				    .setBdlStauts(Integer.parseInt(employee.getZLZ_FLAG()));
				  }
				  if(!StringUtil.isNullorEmpty(employee.getSTELL())){
				    employeeInfoDTO.setTitleId(Long.parseLong(employee.getSTELL()));
				  }
				  if(!StringUtil.isNullorEmpty(employee.getZZF1())){
				    employeeInfoDTO.setUpDutyId(Long.parseLong(employee.getZZF1()));
				  }
				  employeeInfoDTO.setUserName(employee.getPENAM());
				  list.add(employeeInfoDTO);
			   }
			   catch (Exception e) {
				  e.printStackTrace();
			   }
			}
			fddEmployeeDTO.setUserInfoList(list);
			JSONObject getJSON = JSONObject.fromObject(fddEmployeeDTO);
			String employeestr = getJSON.toString();
			String response = HttpUtil.postbody(
				  systemConfig.getProperty("fadada.url") + "/userInfo/save.do",
				  employeestr);
			System.out.println(response);
			JSONObject getObject = JSONObject.fromObject(response);
			boolean isSuccess = (boolean) getObject.get("isSuccess");
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
