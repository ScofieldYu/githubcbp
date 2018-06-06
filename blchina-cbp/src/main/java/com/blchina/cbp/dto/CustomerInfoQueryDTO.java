/*   
 * @(#)CustomerInfoQueryDTO.java       2018年1月12日  
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
public class CustomerInfoQueryDTO {
   
   private String accountType;
   
   private String employeeId;
   
   private String employeeIdOwn;
   
   private String customerId;

   private String searchEmployee;
   
   private int currentPage; // 当前页
   private int pageSize; //每页显示记录数
   private String customerName;
   private String idCardNum;
   private String organizeCode;
   private String organizeName;
   private String phoneNumber;
   private Object[] employelist;

   public String getSearchEmployee() {
      return searchEmployee;
   }

   public void setSearchEmployee(String searchEmployee) {
      this.searchEmployee = searchEmployee;
   }

   public String getEmployeeIdOwn() {
      return employeeIdOwn;
   }

   public void setEmployeeIdOwn(String employeeIdOwn) {
      this.employeeIdOwn = employeeIdOwn;
   }

   public Object[] getEmployelist() {
      return employelist;
   }

   public void setEmployelist(Object[] employelist) {
      this.employelist = employelist;
   }

   public String getCustomerName() {
      return customerName;
   }

   public void setCustomerName(String customerName) {
      this.customerName = customerName;
   }

   public String getIdCardNum() {
      return idCardNum;
   }

   public void setIdCardNum(String idCardNum) {
      this.idCardNum = idCardNum;
   }

   public String getOrganizeCode() {
      return organizeCode;
   }

   public void setOrganizeCode(String organizeCode) {
      this.organizeCode = organizeCode;
   }

   public String getOrganizeName() {
      return organizeName;
   }

   public void setOrganizeName(String organizeName) {
      this.organizeName = organizeName;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   private List<BDLEmployee> list;
   
   public List<BDLEmployee> getList() {
      return list;
   }

   public void setList(List<BDLEmployee> list) {
      this.list = list;
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
