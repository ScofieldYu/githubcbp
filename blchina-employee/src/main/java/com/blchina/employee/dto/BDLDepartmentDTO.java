/*   
 * @(#)BDLDepartmentDTO.java       2017年11月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 部门DTO   
 *
 * @author Administrator 
 * @since JDK 1.8
 */
public class BDLDepartmentDTO {
   @JsonProperty
   private List<Department> Records;
   @JsonIgnore
   public List<Department> getRecords() {
      return Records;
   }
   @JsonIgnore
   public void setRecords(List<Department> records) {
      Records = records;
   }

   public static class Department{
	  //部门ID
	  @JsonProperty
	  private String ORGEH;
	  //部门名称
	  @JsonProperty
	  private String STEXT;
	  //上级部门
	  @JsonProperty
	  private String UP_ORGEH;
	  //部门负责人
	  @JsonProperty
	  private String LEADER;
	  //部门负责岗位
	  @JsonProperty
	  private String POSPLAN;
	  //开始日期 
	  @JsonProperty
	  private String BEGDA;
	  //结束日期
	  @JsonProperty
	  private String ENDDA;
	  //备用字段1
	  @JsonProperty
	  private String ZZF1;
	  //备用字段2
	  @JsonProperty
	  private String ZZF2;
	  //备用字段3
	  @JsonProperty
	  private String ZZF3;
	  @JsonProperty
	  public String getORGEH() {
		 return ORGEH;
	  }
	  @JsonProperty
	  public void setORGEH(String oRGEH) {
		 ORGEH = oRGEH;
	  }
	  @JsonProperty
	  public String getSTEXT() {
		 return STEXT;
	  }
	  @JsonProperty
	  public void setSTEXT(String sTEXT) {
		 STEXT = sTEXT;
	  }
	  @JsonProperty
	  public String getUP_ORGEH() {
		 return UP_ORGEH;
	  }
	  @JsonProperty
	  public void setUP_ORGEH(String uP_ORGEH) {
		 UP_ORGEH = uP_ORGEH;
	  }
	  @JsonProperty
	  public String getLEADER() {
		 return LEADER;
	  }
	  @JsonProperty
	  public void setLEADER(String lEADER) {
		 LEADER = lEADER;
	  }
	  @JsonProperty
	  public String getPOSPLAN() {
		 return POSPLAN;
	  }
	  @JsonProperty
	  public void setPOSPLAN(String pOSPLAN) {
		 POSPLAN = pOSPLAN;
	  }
	  @JsonProperty
	  public String getBEGDA() {
		 return BEGDA;
	  }
	  @JsonProperty
	  public void setBEGDA(String bEGDA) {
		 BEGDA = bEGDA;
	  }
	  @JsonProperty
	  public String getENDDA() {
		 return ENDDA;
	  }
	  @JsonProperty
	  public void setENDDA(String eNDDA) {
		 ENDDA = eNDDA;
	  }
	  @JsonProperty
	  public String getZZF1() {
		 return ZZF1;
	  }
	  @JsonProperty
	  public void setZZF1(String zZF1) {
		 ZZF1 = zZF1;
	  }
	  @JsonProperty
	  public String getZZF2() {
		 return ZZF2;
	  }
	  @JsonProperty
	  public void setZZF2(String zZF2) {
		 ZZF2 = zZF2;
	  }
	  @JsonProperty
	  public String getZZF3() {
		 return ZZF3;
	  }
	  @JsonProperty
	  public void setZZF3(String zZF3) {
		 ZZF3 = zZF3;
	  }
	  public Department() {
	  }
	  
   }
   
   
   
}
