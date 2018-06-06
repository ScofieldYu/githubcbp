/*   
 * @(#)BDLCardListDTO.java       2018年2月24日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class BDLCardListDTO {

   private List<BDLCard> bdlCardList;

   public List<BDLCard> getBdlCardList() {
      return bdlCardList;
   }

   public void setBdlCardList(List<BDLCard> bdlCardList) {
      this.bdlCardList = bdlCardList;
   }
   
   
}
