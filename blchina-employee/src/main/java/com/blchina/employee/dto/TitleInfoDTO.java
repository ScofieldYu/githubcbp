/*   
 * @(#)TitleInfoDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.employee.dto;

/**
 * 职务信息传输DTO
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
public class TitleInfoDTO {

   private Long titleId;

   private String objectName;

   private String startDate;

   private String endDate;

   public Long getTitleId() {
      return titleId;
   }

   public void setTitleId(Long titleId) {
      this.titleId = titleId;
   }

   public String getObjectName() {
      return objectName;
   }

   public void setObjectName(String objectName) {
      this.objectName = objectName;
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
