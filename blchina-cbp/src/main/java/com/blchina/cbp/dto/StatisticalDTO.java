/*   
 * @(#)StatisticalDTO.java       2018年2月8日  
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
public class StatisticalDTO {
   
   private String closetype;
   
   private String total;
   
   private String percentage;

   public String getClosetype() {
      return closetype;
   }

   public void setClosetype(String closetype) {
      this.closetype = closetype;
   }

   public String getTotal() {
      return total;
   }

   public void setTotal(String total) {
      this.total = total;
   }

   public String getPercentage() {
      return percentage;
   }

   public void setPercentage(String percentage) {
      this.percentage = percentage;
   }
   
}
