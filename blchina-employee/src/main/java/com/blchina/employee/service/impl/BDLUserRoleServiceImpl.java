/*   
 * @(#)BDLUserRoleServiceImpl.java       2018年3月7日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.employee.dao.BDLEmployeeMapper;
import com.blchina.employee.dao.BDLRoleMapper;
import com.blchina.employee.dao.BDLUserRoleMapper;
import com.blchina.employee.datamodel.CBPConstant;
import com.blchina.employee.dto.BDLUserRoleDTO;
import com.blchina.employee.model.BDLEmployee;
import com.blchina.employee.model.BDLRole;
import com.blchina.employee.model.BDLUserRole;
import com.blchina.employee.service.interfaces.BDLUserRoleService;

/** 
 * 此类功能描述    
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@Service("/BDLUserRoleService")
public class BDLUserRoleServiceImpl implements BDLUserRoleService {

	@Autowired
	private BDLUserRoleMapper bdlUserRoleMapper;
	@Autowired
	private BDLRoleMapper bdlRoleMapper;
	@Autowired
	private BDLEmployeeMapper bdlEmployeeMapper;
	@Override
	public BDLEmployee getEmployUserIdAndEmployeeId(
			BDLUserRoleDTO bdlUserRole) {
		// TODO Auto-generated method stub
		//获取roleKey
		BDLRole bdlRole = bdlRoleMapper.selectByRoleKey(bdlUserRole.getRolekey());
		bdlUserRole.setRoleid(bdlRole.getRoleid());
		//根据roleKey和companyCode获取userid
		BDLUserRole bdlUserRole1 = bdlUserRoleMapper.getUserIdByCompanyCodeAndRoleId(bdlUserRole);
		//根据userId 获取employee对象
		BDLEmployee employee = bdlEmployeeMapper.selectByPrimaryKey(bdlUserRole1.getUserid());
		return employee;
		
	}
   @Override
   public String getRoleKeyByEmployeeId(BDLUserRoleDTO bdlUserRole) {
	 
	  BDLRole bdlRole = bdlUserRoleMapper.getRoleKeyByEmployeeId(bdlUserRole.getUserid());
	  if(bdlRole==null){
		 return null;
	  }
	  return bdlRole.getRolekey();
   }
   @Override
   public String getFinancePhoneNumberByCompanycode(
		 BDLUserRoleDTO bdlUserRole) {
	  bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_FINANCE_CONSULTANT_ROLE.getType());
	  BDLRole bdlRole = bdlRoleMapper.selectByRoleKey(bdlUserRole.getRolekey());
	  bdlUserRole.setRoleid(bdlRole.getRoleid());
	  BDLUserRole bdlUserRole1 = bdlUserRoleMapper.getUserIdByCompanyCodeAndRoleId(bdlUserRole);
		//根据userId 获取employee对象
	  BDLEmployee employee = bdlEmployeeMapper.selectByPrimaryKey(bdlUserRole1.getUserid());
	  return String.valueOf(employee.getPhonenumber());
   }
   @Override
   public String getSecondHandCarPhoneNumberByCompanycode(
		 BDLUserRoleDTO bdlUserRole) {
	  bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_SECENDCAR_CONSULTANT_ROLE.getType());
	  BDLRole bdlRole = bdlRoleMapper.selectByRoleKey(bdlUserRole.getRolekey());
	  bdlUserRole.setRoleid(bdlRole.getRoleid());
	  BDLUserRole bdlUserRole1 = bdlUserRoleMapper.getUserIdByCompanyCodeAndRoleId(bdlUserRole);
		//根据userId 获取employee对象
	  BDLEmployee employee = bdlEmployeeMapper.selectByPrimaryKey(bdlUserRole1.getUserid());
	  return String.valueOf(employee.getPhonenumber());
   }

}
