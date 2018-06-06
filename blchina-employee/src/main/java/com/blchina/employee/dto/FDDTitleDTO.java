/*   
 * @(#)FDDTitleDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

import java.util.List;

/** 
 * 职务信息传输DTO    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class FDDTitleDTO {
   
   private String timestamp;
   
   private String sign;
   
   private List<TitleInfoDTO> titleInfoList;

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

   public List<TitleInfoDTO> getTitleInfoList() {
      return titleInfoList;
   }

   public void setTitleInfoList(List<TitleInfoDTO> titleInfoList) {
      this.titleInfoList = titleInfoList;
   }
   
   
}
