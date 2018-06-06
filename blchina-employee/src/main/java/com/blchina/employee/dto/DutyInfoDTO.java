/*   
 * @(#)DutyInfoDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

/** 
 * 职位信息传输DTO  
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class DutyInfoDTO {
   
   private Long dutyId;
   
   private String dutyName;
   
   private Long deptId;
   
   private String deptName;
   
   private Long titleId;
   
   private String titleName;
   
   private String startDate;
   
   private String endDate;

   public Long getDutyId() {
      return dutyId;
   }

   public void setDutyId(Long dutyId) {
      this.dutyId = dutyId;
   }

   public String getDutyName() {
      return dutyName;
   }

   public void setDutyName(String dutyName) {
      this.dutyName = dutyName;
   }

   public Long getDeptId() {
      return deptId;
   }

   public void setDeptId(Long deptId) {
      this.deptId = deptId;
   }

   public String getDeptName() {
      return deptName;
   }

   public void setDeptName(String deptName) {
      this.deptName = deptName;
   }

   public Long getTitleId() {
      return titleId;
   }

   public void setTitleId(Long titleId) {
      this.titleId = titleId;
   }

   public String getTitleName() {
      return titleName;
   }

   public void setTitleName(String titleName) {
      this.titleName = titleName;
   }

   public String getStartDate() {
      return startDate;
   }

   public void setStartDate(String startDate) {
      this.startDate = startDate;
   }

   public String getEndDate() {
      return endDate;
   }

   public void setEndDate(String endDate) {
      this.endDate = endDate;
   }
   
}
