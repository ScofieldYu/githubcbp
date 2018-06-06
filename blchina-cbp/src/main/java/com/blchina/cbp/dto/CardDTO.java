/*   
 * @(#)CardDTO.java       2018年2月5日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class CardDTO {
   
   private int currentPage; // 当前页
   
   private int pageSize;
   
   private String customerName;
   
   private String employeeId;
   
   private String cardStatus;

   private Integer employeeid;

   public Integer getEmployeeid() {
      return employeeid;
   }

   public void setEmployeeid(Integer employeeid) {
      this.employeeid = employeeid;
   }

   public String getEmployeeId() {
      return employeeId;
   }

   public void setEmployeeId(String employeeId) {
      this.employeeId = employeeId;
   }

   public String getCardStatus() {
      return cardStatus;
   }

   public void setCardStatus(String cardStatus) {
      this.cardStatus = cardStatus;
   }

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

   public String getCustomerName() {
      return customerName;
   }

   public void setCustomerName(String customerName) {
      this.customerName = customerName;
   }
   
}
