/*   
 * @(#)CustomerMessageDTO.java       2018年1月12日  
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
public class CustomerMessageDTO {
   
   private String buyerIdCardNum;
   
   private String buyerName;
   
   private Integer customerInfoId;
   
   private String phoneNumber;
   
   private String organizeName;
   
   private String organizeCode;
   
   private String customerName;
   
   private String idcardNum;
   
   private String employeeName;
   
   private String employeeId;
   
   public String getEmployeeId() {
      return employeeId;
   }

   public void setEmployeeId(String employeeId) {
      this.employeeId = employeeId;
   }

   public String getEmployeeName() {
      return employeeName;
   }

   public void setEmployeeName(String employeeName) {
      this.employeeName = employeeName;
   }

   public String getOrganizeName() {
      return organizeName;
   }

   public void setOrganizeName(String organizeName) {
      this.organizeName = organizeName;
   }

   public String getOrganizeCode() {
      return organizeCode;
   }

   public void setOrganizeCode(String organizeCode) {
      this.organizeCode = organizeCode;
   }

   public String getCustomerName() {
      return customerName;
   }

   public void setCustomerName(String customerName) {
      this.customerName = customerName;
   }

   public String getIdcardNum() {
      return idcardNum;
   }

   public void setIdcardNum(String idcardNum) {
      this.idcardNum = idcardNum;
   }

   public String getBuyerIdCardNum() {
      return buyerIdCardNum;
   }

   public void setBuyerIdCardNum(String buyerIdCardNum) {
      this.buyerIdCardNum = buyerIdCardNum;
   }

   public String getBuyerName() {
      return buyerName;
   }

   public void setBuyerName(String buyerName) {
      this.buyerName = buyerName;
   }

   public Integer getCustomerInfoId() {
      return customerInfoId;
   }

   public void setCustomerInfoId(Integer customerInfoId) {
      this.customerInfoId = customerInfoId;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }
   
   
}
