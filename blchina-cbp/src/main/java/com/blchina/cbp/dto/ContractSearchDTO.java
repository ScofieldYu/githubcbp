/*   
 * @(#)ContractSearchDTO.java       Dec 14, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.cbp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 合同搜索DTO
 *
 * @author Scofield
 * @since JDK 1.8
 */
public class ContractSearchDTO {
   @JsonProperty
   private String saporderid;
   @JsonProperty
   private String customerName;
   @JsonProperty
   private String idCardNum;
   @JsonProperty
   private String phoneNumber;
   @JsonProperty
   private Integer date;
   @JsonProperty
   private String contractstatus; 
   @JsonProperty
   private String ischanged;
   @JsonProperty
   private Integer  employeeId;
   private Object[] employelist;
   private String searchEmployee;

   public Object[] getEmployelist() {
      return employelist;
   }

   public void setEmployelist(Object[] employelist) {
      this.employelist = employelist;
   }

   public String getSearchEmployee() {
      return searchEmployee;
   }

   public void setSearchEmployee(String searchEmployee) {
      this.searchEmployee = searchEmployee;
   }

   public String getSaporderid() {
      return saporderid;
   }

   public void setSaporderid(String saporderid) {
      this.saporderid = saporderid;
   }

   @JsonIgnore
   public String getCustomerName() {
	  return customerName;
   }

   @JsonIgnore
   public void setCustomerName(String customerName) {
	  this.customerName = customerName;
   }

   @JsonIgnore
   public String getIdCardNum() {
	  return idCardNum;
   }

   @JsonIgnore
   public void setIdCardNum(String idCardNum) {
	  this.idCardNum = idCardNum;
   }

   @JsonIgnore
   public String getPhoneNumber() {
	  return phoneNumber;
   }

   @JsonIgnore
   public void setPhoneNumber(String phoneNumber) {
	  this.phoneNumber = phoneNumber;
   }

   @JsonIgnore
   public Integer getDate() {
	  return date;
   }

   @JsonIgnore
   public void setDate(Integer date) {
	  this.date = date;
   }
   
   @JsonIgnore
   public String getContractstatus() {
      return contractstatus;
   }

   @JsonIgnore
   public void setContractstatus(String contractstatus) {
      this.contractstatus = contractstatus;
   }

   @JsonIgnore
   public String getIschanged() {
      return ischanged;
   }

   @JsonIgnore
   public void setIschanged(String ischanged) {
      this.ischanged = ischanged;
   }

   @JsonIgnore
   public Integer getEmployeeId() {
      return employeeId;
   }

   @JsonIgnore
   public void setEmployeeId(Integer employeeId) {
      this.employeeId = employeeId;
   }
   
   

}
