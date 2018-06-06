/*   
 * @(#)EmployeeMapper.java       2018年2月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dao;

import org.springframework.stereotype.Service;

import com.blchina.employee.model.BDLBrandStore;

/** 
 * 此类功能描述    
 *
 * @author zhangtong 
 * @since JDK 1.7 
 */
@Service
public interface EmployeeMapper {

	Integer getTitleIdByEmployeeId(BDLBrandStore employeeId);

	String getCompanyCodeByEmployeeId(BDLBrandStore employeeId);

}
