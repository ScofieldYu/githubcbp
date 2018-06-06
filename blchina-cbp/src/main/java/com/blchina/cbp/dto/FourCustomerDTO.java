/*   
 * @(#)FourCustomerDTO.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;

import com.blchina.cbp.model.CBPFourCustomer;

/** 
 * 四方信息维护dto    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class FourCustomerDTO {

   private List<CBPFourCustomer> fourCustomerList;

   public List<CBPFourCustomer> getFourCustomerList() {
      return fourCustomerList;
   }

   public void setFourCustomerList(List<CBPFourCustomer> fourCustomerList) {
      this.fourCustomerList = fourCustomerList;
   }
}
