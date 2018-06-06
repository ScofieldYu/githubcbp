/*   
 * @(#)FDDStoreDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

import java.util.List;

/** 
 * 传输门店信息DTO    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class FDDStoreDTO {
   
   private String timestamp;
   
   private String sign;
   
   private List<StoreInfoDTO> storeInfoList;

   public String getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
   }

   public String getSign() {
      return sign;
   }

   public void setSign(String sign) {
      this.sign = sign;
   }

   public List<StoreInfoDTO> getStoreInfoList() {
      return storeInfoList;
   }

   public void setStoreInfoList(List<StoreInfoDTO> storeInfoList) {
      this.storeInfoList = storeInfoList;
   }
   
}
