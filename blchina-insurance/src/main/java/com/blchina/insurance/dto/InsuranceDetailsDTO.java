/*   
 * @(#)InsuranceDetailsDTO.java       2018年2月5日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.dto;

import java.util.List;

import com.blchina.insurance.model.BDLInsuranceCompany;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class InsuranceDetailsDTO {
   
   private String orderId;
   
   private String insuranceid;
   
   private List<BDLInsuranceCompany> insuranceCompanyList;
   
   private List<CBPInsuranceOrder> cbpInsuranceOrderList;
   
   public List<CBPInsuranceOrder> getCbpInsuranceOrderList() {
      return cbpInsuranceOrderList;
   }

   public void setCbpInsuranceOrderList(
   	  List<CBPInsuranceOrder> cbpInsuranceOrderList) {
      this.cbpInsuranceOrderList = cbpInsuranceOrderList;
   }

   public List<BDLInsuranceCompany> getInsuranceCompanyList() {
      return insuranceCompanyList;
   }

   public void setInsuranceCompanyList(
   	  List<BDLInsuranceCompany> insuranceCompanyList) {
      this.insuranceCompanyList = insuranceCompanyList;
   }

   public String getInsuranceid() {
      return insuranceid;
   }

   public void setInsuranceid(String insuranceid) {
      this.insuranceid = insuranceid;
   }

   public String getOrderId() {
      return orderId;
   }

   public void setOrderId(String orderId) {
      this.orderId = orderId;
   }
   
}
