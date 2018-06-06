/*   
 * @(#)FileAddressDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

/** 
 * 获取文档下载地址DTO    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class FileAddressDTO {
   
   private String sign;
   
   private String timestamp;
   
   private String uuid;
   
   private String suffix;

   public String getSuffix() {
      return suffix;
   }

   public void setSuffix(String suffix) {
      this.suffix = suffix;
   }

   public String getSign() {
      return sign;
   }

   public void setSign(String sign) {
      this.sign = sign;
   }

   public String getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
   }

   public String getUuid() {
      return uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }
   
}
