/*   
 * @(#)BDLUnEmployeeService.java       2017年11月21日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.service.interfaces;

import com.blchina.employee.dto.BDLEmployeeDTO;
import com.blchina.employee.model.BDLEmployee;
import com.blchina.employee.model.BDLUnEmployee;

/** 
 * 此类功能描述    
 *
 * @author Administrator 
 * @since JDK 1.8
 */
public interface BDLUnEmployeeService {

   public int insertOrUpdateUnEmployee(BDLEmployeeDTO.Employee employee);

   BDLUnEmployee getEmployeeById(Integer employeeid);
}
