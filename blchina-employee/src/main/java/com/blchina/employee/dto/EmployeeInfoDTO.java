/*   
 * @(#)EmployeeInfoDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

/** 
 * 用户信息传输DTO    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class EmployeeInfoDTO {
   
   private String upDomainAccount;
   
   private Long id;
   
   private String userName;
   
   private Long deptId;
   
   private Long dutyId;
   
   private Long titleId;
   
   private String companyCode;
   
   private int bdlStauts;
   
   private String mobile;
   
   private String email;
   
   private String birth;
   
   private String sex;
   
   private String enterDate;
   
   private Long upDutyId;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUpDomainAccount() {
      return upDomainAccount;
   }

   public void setUpDomainAccount(String upDomainAccount) {
      this.upDomainAccount = upDomainAccount;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public Long getDeptId() {
      return deptId;
   }

   public void setDeptId(Long deptId) {
      this.deptId = deptId;
   }

   public Long getDutyId() {
      return dutyId;
   }

   public void setDutyId(Long dutyId) {
      this.dutyId = dutyId;
   }

   public Long getTitleId() {
      return titleId;
   }

   public void setTitleId(Long titleId) {
      this.titleId = titleId;
   }

   public String getCompanyCode() {
      return companyCode;
   }

   public void setCompanyCode(String companyCode) {
      this.companyCode = companyCode;
   }

   public int getBdlStauts() {
      return bdlStauts;
   }

   public void setBdlStauts(int bdlStauts) {
      this.bdlStauts = bdlStauts;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getBirth() {
      return birth;
   }

   public void setBirth(String birth) {
      this.birth = birth;
   }

   public String getSex() {
      return sex;
   }

   public void setSex(String sex) {
      this.sex = sex;
   }

   public String getEnterDate() {
      return enterDate;
   }

   public void setEnterDate(String enterDate) {
      this.enterDate = enterDate;
   }

   public Long getUpDutyId() {
      return upDutyId;
   }

   public void setUpDutyId(Long upDutyId) {
      this.upDutyId = upDutyId;
   }
   
}
