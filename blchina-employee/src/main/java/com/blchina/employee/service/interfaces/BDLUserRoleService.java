/*   
 * @(#)BDLUserRoleService.java       2018年3月7日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.service.interfaces;

import java.util.List;

import com.blchina.employee.dto.BDLUserRoleDTO;
import com.blchina.employee.model.BDLEmployee;
import com.blchina.employee.model.BDLUserRole;

/** 
 * 此类功能描述    
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
public interface BDLUserRoleService {

	BDLEmployee getEmployUserIdAndEmployeeId(BDLUserRoleDTO bdlUserRole);

   String getRoleKeyByEmployeeId(BDLUserRoleDTO bdlUserRole);

   String getFinancePhoneNumberByCompanycode(BDLUserRoleDTO bdlUserRole);

   String getSecondHandCarPhoneNumberByCompanycode(BDLUserRoleDTO bdlUserRole);

}
