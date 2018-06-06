/*   
 * @(#)FDDDutyDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

import java.util.List;

/** 
 * 职位信息传输DTO     
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class FDDDutyDTO {
   
   private String timestamp;
   
   private String sign;
   
   private List<DutyInfoDTO> dutyInfoList;

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

   public List<DutyInfoDTO> getDutyInfoList() {
      return dutyInfoList;
   }

   public void setDutyInfoList(List<DutyInfoDTO> dutyInfoList) {
      this.dutyInfoList = dutyInfoList;
   }
   
   
}
