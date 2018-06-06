/*   
 * @(#)InsuranceDetailsDTO.java       2018年2月5日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;

import com.blchina.cbp.model.CBPInsuranceOrder;


/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class InsuranceDetailsDTO {
   
   private String orderId;
   
   private String insuranceid;
   
   private String total;
   
   private List<BDLInsuranceCompany> insuranceCompanyList;
   
   private List<InsuranceOrderDTO> insuranceOrderDTOList;
   
   public String getTotal() {
      return total;
   }

   public void setTotal(String total) {
      this.total = total;
   }

   public List<InsuranceOrderDTO> getInsuranceOrderDTOList() {
      return insuranceOrderDTOList;
   }

   public void setInsuranceOrderDTOList(
   	  List<InsuranceOrderDTO> insuranceOrderDTOList) {
      this.insuranceOrderDTOList = insuranceOrderDTOList;
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
