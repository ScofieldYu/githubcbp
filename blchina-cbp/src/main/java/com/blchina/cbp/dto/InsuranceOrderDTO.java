/*   
 * @(#)InsuranceOrderDTO.java       2018年2月7日  
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
public class InsuranceOrderDTO {

   private Integer insuranceorderid;

   private Integer orderid;

   private String insurancelimit;

   private Integer insuranceid;

   private String isnodeductible;

   private String insurancename;

   private Integer insurancecompanyid;

   private String insurancecompanyname;
   
   private String insurancetype;

   public Integer getInsuranceorderid() {
      return insuranceorderid;
   }

   public void setInsuranceorderid(Integer insuranceorderid) {
      this.insuranceorderid = insuranceorderid;
   }

   public Integer getOrderid() {
      return orderid;
   }

   public void setOrderid(Integer orderid) {
      this.orderid = orderid;
   }

   public String getInsurancelimit() {
      return insurancelimit;
   }

   public void setInsurancelimit(String insurancelimit) {
      this.insurancelimit = insurancelimit;
   }

   public Integer getInsuranceid() {
      return insuranceid;
   }

   public void setInsuranceid(Integer insuranceid) {
      this.insuranceid = insuranceid;
   }

   public String getIsnodeductible() {
      return isnodeductible;
   }

   public void setIsnodeductible(String isnodeductible) {
      this.isnodeductible = isnodeductible;
   }

   public String getInsurancename() {
      return insurancename;
   }

   public void setInsurancename(String insurancename) {
      this.insurancename = insurancename;
   }

   public Integer getInsurancecompanyid() {
      return insurancecompanyid;
   }

   public void setInsurancecompanyid(Integer insurancecompanyid) {
      this.insurancecompanyid = insurancecompanyid;
   }

   public String getInsurancecompanyname() {
      return insurancecompanyname;
   }

   public void setInsurancecompanyname(String insurancecompanyname) {
      this.insurancecompanyname = insurancecompanyname;
   }

   public String getInsurancetype() {
      return insurancetype;
   }

   public void setInsurancetype(String insurancetype) {
      this.insurancetype = insurancetype;
   }
   
}
