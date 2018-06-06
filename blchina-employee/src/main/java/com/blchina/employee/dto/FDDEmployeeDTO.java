/*   
 * @(#)FDDEmployeeDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.employee.dto;

import java.util.List;


/**
 * 用户信息传输DTO
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
public class FDDEmployeeDTO {

   private String sign;

   private String timestamp;

   private List<EmployeeInfoDTO> userInfoList;

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

   public List<EmployeeInfoDTO> getUserInfoList() {
      return userInfoList;
   }

   public void setUserInfoList(List<EmployeeInfoDTO> userInfoList) {
      this.userInfoList = userInfoList;
   }
   
}
