/*   
 * @(#)DepartmentInfoDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.employee.dto;

/**
 * 部门信息传输DTO
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
public class DepartmentInfoDTO {

   private Long deptId;

   private String deptName;

   private Long parentId;

   private Long deptLeader;

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

   public Long getParentId() {
      return parentId;
   }

   public void setParentId(Long parentId) {
      this.parentId = parentId;
   }

   public Long getDeptLeader() {
      return deptLeader;
   }

   public void setDeptLeader(Long deptLeader) {
      this.deptLeader = deptLeader;
   }
}
