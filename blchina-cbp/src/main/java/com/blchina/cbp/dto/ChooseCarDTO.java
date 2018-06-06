/*   
 * @(#)ChooseCarDTO.java       2018年2月23日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;

import com.blchina.cbp.model.CBPFourCustomer;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class ChooseCarDTO {
   
   private String carseries;

   private String cartype;

   private String carappearance;

   private String carinterior;
   
   private String realvinno;
   
   private String employeeid;
   
   private String customerid;

   private String orderid;
   
   public String getOrderid() {
      return orderid;
   }

   public void setOrderid(String orderid) {
      this.orderid = orderid;
   }

   private List<CBPFourCustomer> fourCustomerList;
   
   public String getCustomerid() {
      return customerid;
   }

   public void setCustomerid(String customerid) {
      this.customerid = customerid;
   }

   public String getEmployeeid() {
      return employeeid;
   }

   public void setEmployeeid(String employeeid) {
      this.employeeid = employeeid;
   }
   
   public List<CBPFourCustomer> getFourCustomerList() {
      return fourCustomerList;
   }

   public void setFourCustomerList(List<CBPFourCustomer> fourCustomerList) {
      this.fourCustomerList = fourCustomerList;
   }

   public String getCarseries() {
      return carseries;
   }

   public void setCarseries(String carseries) {
      this.carseries = carseries;
   }

   public String getCartype() {
      return cartype;
   }

   public void setCartype(String cartype) {
      this.cartype = cartype;
   }

   public String getCarappearance() {
      return carappearance;
   }

   public void setCarappearance(String carappearance) {
      this.carappearance = carappearance;
   }

   public String getCarinterior() {
      return carinterior;
   }

   public void setCarinterior(String carinterior) {
      this.carinterior = carinterior;
   }

   public String getRealvinno() {
      return realvinno;
   }

   public void setRealvinno(String realvinno) {
      this.realvinno = realvinno;
   }
   
}
