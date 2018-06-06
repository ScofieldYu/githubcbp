/*   
 * @(#)EmployeeListDTO.java       2018年2月9日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class EmployeeListDTO {
   
   private Object[] employeeList;

   private Object[] brandIdList;
   
   public Object[] getBrandIdList() {
      return brandIdList;
   }

   public void setBrandIdList(Object[] brandIdList) {
      this.brandIdList = brandIdList;
   }

   public Object[] getEmployeeList() {
      return employeeList;
   }

   public void setEmployeeList(Object[] employeeList) {
      this.employeeList = employeeList;
   }
   
}
