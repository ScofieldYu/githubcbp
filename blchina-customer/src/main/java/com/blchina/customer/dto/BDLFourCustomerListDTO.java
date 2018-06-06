/*   
 * @(#)BDLFourCustomerListDTO.java       2018年3月3日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.customer.dto;

import java.util.List;

import com.blchina.customer.model.BDLFourCustomer;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class BDLFourCustomerListDTO {

   private List<BDLFourCustomer> fourCustomerListDTO;

   public List<BDLFourCustomer> getFourCustomerListDTO() {
      return fourCustomerListDTO;
   }

   public void setFourCustomerListDTO(List<BDLFourCustomer> fourCustomerListDTO) {
      this.fourCustomerListDTO = fourCustomerListDTO;
   } 
}
