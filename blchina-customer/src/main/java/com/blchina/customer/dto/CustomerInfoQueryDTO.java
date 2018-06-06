/*   
 * @(#)CustomerInfoQueryDTO.java       2018年1月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.customer.dto;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class CustomerInfoQueryDTO {
   
   private String accountType;
   
   private String employeeId;
   
   private String customerId;
   
   private int currentPage; // 当前页
   private int pageSize; //每页显示记录数
   
   
   
   public int getCurrentPage() {
      return currentPage;
   }

   public void setCurrentPage(int currentPage) {
      this.currentPage = currentPage;
   }

   public int getPageSize() {
      return pageSize;
   }

   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }

   public String getCustomerId() {
      return customerId;
   }

   public void setCustomerId(String customerId) {
      this.customerId = customerId;
   }

   public String getAccountType() {
      return accountType;
   }

   public void setAccountType(String accountType) {
      this.accountType = accountType;
   }

   public String getEmployeeId() {
      return employeeId;
   }

   public void setEmployeeId(String employeeId) {
      this.employeeId = employeeId;
   }
   
   
}
