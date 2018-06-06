/*   
 * @(#)FDDDepartmentDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

import java.util.List;

/** 
 * 组织架构信息传输DTO    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class FDDDepartmentDTO {

   private String sign;
   
   private String timestamp;
   
   private List<DepartmentInfoDTO> deptInfoList;

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

   public List<DepartmentInfoDTO> getDeptInfoList() {
      return deptInfoList;
   }

   public void setDeptInfoList(List<DepartmentInfoDTO> deptInfoList) {
      this.deptInfoList = deptInfoList;
   }
   
}
