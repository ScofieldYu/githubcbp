/*   
 * @(#)UserRoleController.java       2018年3月7日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.employee.datamodel.CBPConstant;
import com.blchina.employee.dto.BDLUserRoleDTO;
import com.blchina.employee.model.BDLEmployee;
import com.blchina.employee.service.interfaces.BDLUserRoleService;

/** 
 * 此类功能描述    
 *
 * @author Zhangtong 获取门店的承保顾问
 * @since JDK 1.7 
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {
	@Autowired
	private BDLUserRoleService bdlUserRoleService;
	/**
	 * 获取门店的承保顾问
	 * @param employee
	 * @return
     */
	@RequestMapping("/getEmployUserIdAndEmployeeId")
	public String getEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_FINANCE_CONSULTANT_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}
	/**
	 * 获取门店的精品顾问
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getboutiqueEmployUserIdAndEmployeeId")
	public String getboutiqueEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_BOUTIQUE_CONSULTANT_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}
	
	/**
	 * 获取门店的二手车验车专员
	 * @param employees
	 * @return
	 */
	@RequestMapping("/getSecendCarEmployUserIdAndEmployeeId")
	public String getSecendCarEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_SECONDCAR_CHECK_CONSULTANT_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}
	
	/**
	 * 获取门店的二手车评估师
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getSecendCarFinanceEmployUserIdAndEmployeeId")
	public String getSecendCarFinanceEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_SECENDCAR_CONSULTANT_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}
	
	/**
	 * 获取门店的验车上牌专员
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getCheckCarEmployUserIdAndEmployeeId")
	public String getCheckCarEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_CHECKCARPERSON_CONSULTANT_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}
	
	/**
	 * 获取门店的金融顾问
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getFinanceEmployUserIdAndEmployeeId")
	public String getFinanceEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_SALES_MANAGER_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}

	/**
	 * 获取门店的销售经理
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getSalesManagerEmployUserIdAndEmployeeId")
	public String getSalesManagerEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_SALES_MANAGER_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}

	/**
	 * 获取门店的销售总监
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getSalesDirecorEmployUserIdAndEmployeeId")
	public String getSalesDirectorEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_SALES_DIRECTOR_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}
	/**
	 * 获取门店的店长
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getStoreManagerEmployUserIdAndEmployeeId")
	public String getStoreManagerEmployUserIdAndEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_STOREMANAGER_CONSULTANT_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}
	/**
	 * 获取门店财务
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getPayNoticeUser")
	public String getPayNoticeUser(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_STOREFINANCE_COUNSULTANT_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}
	/**
	 * 获取二手车财务
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getSecondHandCarPayNoticeUser")
	public String getSecondHandCarPayNoticeUser(@RequestBody BDLUserRoleDTO bdlUserRole){
		bdlUserRole.setRolekey(CBPConstant.EmployeeTypeEnumRole.EMPLOYEE_STOREMANAGER_CONSULTANT_ROLE.getType());
		BDLEmployee bdlEmployee=bdlUserRoleService.getEmployUserIdAndEmployeeId(bdlUserRole);
		if(bdlEmployee==null){
			return null;
		}
		return bdlEmployee.getEmployeeid()+","+bdlEmployee.getEmail();
	}

	@RequestMapping("/getRoleKeyByEmployeeId")
	public String getRoleKeyByEmployeeId(@RequestBody BDLUserRoleDTO bdlUserRole){
	   String string = bdlUserRoleService.getRoleKeyByEmployeeId(bdlUserRole);
	   return string;
	}
	
	@RequestMapping("/getFinancePhoneNumberByCompanycode")
	public String getFinancePhoneNumberByCompanycode(@RequestBody BDLUserRoleDTO bdlUserRole){
	   String string = bdlUserRoleService.getFinancePhoneNumberByCompanycode(bdlUserRole);
	   return string;
	}
	
	@RequestMapping("/getSecondHandCarPhoneNumberByCompanycode")
	public String getSecondHandCarPhoneNumberByCompanycode(@RequestBody BDLUserRoleDTO bdlUserRole){
	   String string = bdlUserRoleService.getSecondHandCarPhoneNumberByCompanycode(bdlUserRole);
	   return string;
	}
}
