/*   
 * @(#)BDLUnEmployeeServiceImpl.java       2017年11月21日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.common.util.string.StringUtil;
import com.blchina.employee.dao.BDLEmployeeMapper;
import com.blchina.employee.dao.BDLUnEmployeeMapper;
import com.blchina.employee.dto.BDLEmployeeDTO;
import com.blchina.employee.model.BDLEmployee;
import com.blchina.employee.model.BDLUnEmployee;
import com.blchina.employee.service.interfaces.BDLUnEmployeeService;


/**
 * 此类功能描述
 *
 * @author Administrator
 * @since JDK 1.8
 */
@Service("bdlUnEmployeeService")
public class BDLUnEmployeeServiceImpl implements BDLUnEmployeeService {

   @Autowired
   private BDLUnEmployeeMapper bdlUnEmployeeMapper;
   @Autowired
   private BDLEmployeeMapper bdlEmployeeMapper;

   @Override
   public int insertOrUpdateUnEmployee(BDLEmployeeDTO.Employee employee) {
	  int status = 0;
	  if (employee != null && employee.getPERNR() != null) {
		 bdlEmployeeMapper.deleteByPrimaryKey(Integer.parseInt(employee.getPERNR()));
		 BDLUnEmployee selectByPrimaryKey = bdlUnEmployeeMapper
			   .selectByPrimaryKey(Integer.parseInt(employee.getPERNR()));
		 BDLEmployee bdlEmployee = BDLUnEmployeeServiceImpl.bdlEmployeeDTO2BDLEmployee(employee);
		 if (selectByPrimaryKey != null) {
			BDLUnEmployee bdlUnEmployee = bdlEmployee2UnBDLEmployee(
				  bdlEmployee);
			status = bdlUnEmployeeMapper.updateByPrimaryKeySelective(bdlUnEmployee);
		 }
		 else {
			BDLUnEmployee bdlUnEmployee = bdlEmployee2UnBDLEmployee(
				  bdlEmployee);
			status = bdlUnEmployeeMapper.insert(bdlUnEmployee);
		 }
	  }
	  return status;
   }

	@Override
	public BDLUnEmployee getEmployeeById(Integer employeeid) {
		return bdlUnEmployeeMapper.selectByPrimaryKey(employeeid);
	}

	private static BDLEmployee bdlEmployeeDTO2BDLEmployee(BDLEmployeeDTO.Employee employee){
	   BDLEmployee bdlEmployee = new BDLEmployee();
	   bdlEmployee.setEmployeeid(Integer.parseInt(employee.getPERNR()));
	   bdlEmployee.setEmployeename(employee.getPENAM());  
	   if(!StringUtil.isNullorEmpty(employee.getORGEH())){
		  bdlEmployee.setDepartmentid(Integer.parseInt(employee.getORGEH()));
	   }
	   if(!StringUtil.isNullorEmpty(employee.getPLANS())){
		  bdlEmployee.setDutyid(Integer.parseInt(employee.getPLANS()));
	   }
	   if(!StringUtil.isNullorEmpty(employee.getSTELL())){
		  bdlEmployee.setTitleid(Integer.parseInt(employee.getSTELL()));        
	   }
	   bdlEmployee.setCompanycode(employee.getBUKRS());       
	   bdlEmployee.setStatus(employee.getZLZ_FLAG());       
	   bdlEmployee.setUpdomainaccount(employee.getCOMM_ID_LONG());       
	   bdlEmployee.setSex(employee.getGESCH());   
	   if(!StringUtil.isNullorEmpty(employee.getTEL_PHONE())){
		  bdlEmployee.setPhonenumber(Long.parseLong(employee.getTEL_PHONE()));       
	   }
	   bdlEmployee.setEmail(employee.getE_MAIL());        
	   bdlEmployee.setBirth(employee.getGBDAT());       
	   bdlEmployee.setEnterdate(employee.getBEGDA()); 
	   if(!StringUtil.isNullorEmpty(employee.getZZF1())){
		  bdlEmployee.setUpdutyid(Integer.parseInt(employee.getZZF1()));        
	   }
	   bdlEmployee.setZzf1(employee.getZZF2());
	   bdlEmployee.setZzf2(employee.getZZF3());
	   return bdlEmployee;
	}
   private static BDLUnEmployee bdlEmployee2UnBDLEmployee(
		 BDLEmployee bdlEmployee) {
	  BDLUnEmployee bdlUnEmployee = new BDLUnEmployee();
	  if(!StringUtil.isNullorEmpty(bdlEmployee.getEmployeeid()+"")){
		 bdlUnEmployee.setUnemployeeid(bdlEmployee.getEmployeeid());
	  }
	  bdlUnEmployee.setEmployeename(bdlEmployee.getEmployeename());
	  if(!StringUtil.isNullorEmpty(bdlEmployee.getDepartmentid()+"")){
		 bdlUnEmployee.setDepartmentid(bdlEmployee.getDepartmentid());
	  }
	  if(!StringUtil.isNullorEmpty(bdlEmployee.getDutyid()+"")){
		 bdlUnEmployee.setDutyid(bdlEmployee.getDutyid());
	  }
	  if(!StringUtil.isNullorEmpty(bdlEmployee.getTitleid()+"")){
		 bdlUnEmployee.setTitleid(bdlEmployee.getTitleid());
	  }
	  bdlUnEmployee.setCompanycode(bdlEmployee.getCompanycode());
	  bdlUnEmployee.setStatus(bdlEmployee.getStatus());
	  bdlUnEmployee.setUpdomainaccount(bdlEmployee.getUpdomainaccount());
	  bdlUnEmployee.setSex(bdlEmployee.getSex());
	  if(!StringUtil.isNullorEmpty(bdlEmployee.getPhonenumber()+"")){
		 bdlUnEmployee.setPhonenumber(bdlEmployee.getPhonenumber());
	  }
	  bdlUnEmployee.setEmail(bdlEmployee.getEmail());
	  bdlUnEmployee.setBirth(bdlEmployee.getBirth());
	  bdlUnEmployee.setEnterdate(bdlEmployee.getEnterdate());
	  if(!StringUtil.isNullorEmpty(bdlEmployee.getUpdutyid()+"")){
		 bdlUnEmployee.setUpdutyid(bdlEmployee.getUpdutyid());
	  }
	  bdlUnEmployee.setZzf1(bdlEmployee.getZzf1());
	  bdlUnEmployee.setZzf2(bdlEmployee.getZzf2());
	  return bdlUnEmployee;
   }

}
