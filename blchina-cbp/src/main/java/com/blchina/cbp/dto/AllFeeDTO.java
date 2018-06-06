/*   
 * @(#)AllFeeDTO.java       2018年3月7日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;

import com.blchina.cbp.model.CBPBoutiqueOrder;
import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPContract;
import com.blchina.cbp.model.CBPInsuranceOrder;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class AllFeeDTO {

   private CBPContract cbpContract;
   
   private List<CBPInsuranceOrder>  insuranceOrderList;
   
   private CBPCheckCarNumOrder cbpCheckCarNumOrder;
   
   private List<CBPBoutiqueOrder> boutiqueOrderList;

   public CBPContract getCbpContract() {
      return cbpContract;
   }

   public void setCbpContract(CBPContract cbpContract) {
      this.cbpContract = cbpContract;
   }

   public List<CBPInsuranceOrder> getInsuranceOrderList() {
      return insuranceOrderList;
   }

   public void setInsuranceOrderList(List<CBPInsuranceOrder> insuranceOrderList) {
      this.insuranceOrderList = insuranceOrderList;
   }

   public CBPCheckCarNumOrder getCbpCheckCarNumOrder() {
      return cbpCheckCarNumOrder;
   }

   public void setCbpCheckCarNumOrder(CBPCheckCarNumOrder cbpCheckCarNumOrder) {
      this.cbpCheckCarNumOrder = cbpCheckCarNumOrder;
   }

   public List<CBPBoutiqueOrder> getBoutiqueOrderList() {
      return boutiqueOrderList;
   }

   public void setBoutiqueOrderList(List<CBPBoutiqueOrder> boutiqueOrderList) {
      this.boutiqueOrderList = boutiqueOrderList;
   }
}
