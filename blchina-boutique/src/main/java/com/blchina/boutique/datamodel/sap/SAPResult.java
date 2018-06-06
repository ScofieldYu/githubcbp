/*   
 * @(#)SAPResult.java       Nov 2, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */ 
package com.blchina.boutique.datamodel.sap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 和SAP接口交付的数据模板    
 *
 * @author Scofield 
 * @since JDK 1.8
 */
public class SAPResult<T> {
   @JsonProperty
   private String OppDate ="OppDate";
   
   @JsonProperty
   private String Descriptioin ="Descriptioin";
   
   @JsonProperty
   private T Records;
   @JsonIgnore
   public String getOppDate() {
      return OppDate;
   }
   @JsonIgnore
   public void setOppDate(String oppDate) {
      OppDate = oppDate;
   }
   @JsonIgnore
   public String getDescriptioin() {
      return Descriptioin;
   }
   @JsonIgnore
   public void setDescriptioin(String descriptioin) {
      Descriptioin = descriptioin;
   }
   @JsonIgnore
   public T getRecords() {
      return Records;
   }
   @JsonIgnore
   public void setRecords(T records) {
      Records = records;
   }
   
}