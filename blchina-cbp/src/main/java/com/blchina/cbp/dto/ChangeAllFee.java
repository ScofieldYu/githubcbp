/*   
 * @(#)ChangeAllFee.java       2018年3月6日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPBoutiqueOrder;
import com.blchina.cbp.model.CBPCar;
import com.blchina.cbp.model.CBPInsuranceOrder;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class ChangeAllFee {
   
   private CBPInsuranceOrder insuranceOrder;
   
   private CBPBoutiqueOrder boutiqueOrder;
   
   private CBPCar car;

   public CBPInsuranceOrder getInsuranceOrder() {
      return insuranceOrder;
   }

   public void setInsuranceOrder(CBPInsuranceOrder insuranceOrder) {
      this.insuranceOrder = insuranceOrder;
   }

   public CBPBoutiqueOrder getBoutiqueOrder() {
      return boutiqueOrder;
   }

   public void setBoutiqueOrder(CBPBoutiqueOrder boutiqueOrder) {
      this.boutiqueOrder = boutiqueOrder;
   }

   public CBPCar getCar() {
      return car;
   }

   public void setCar(CBPCar car) {
      this.car = car;
   }
   
}
