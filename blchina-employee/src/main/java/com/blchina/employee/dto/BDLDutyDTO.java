/*   
 * @(#)BDLDutyDTO.java       2017年11月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 职位DTO    
 *
 * @author Administrator 
 * @since JDK 1.8
 */
public class BDLDutyDTO {
   @JsonProperty
   private List<Duty> Records;
   @JsonIgnore
   public List<Duty> getRecords() {
      return Records;
   }
   @JsonIgnore
   public void setRecords(List<Duty> records) {
      Records = records;
   }

   public static class Duty{
	  //岗位ID
	  @JsonProperty
	  private String PLANS;
	  //岗位名称
	  @JsonProperty
	  private String STEXT2;
	  //部门ID
	  @JsonProperty
	  private String ORGEH;
	  //部门名称
	  @JsonProperty
	  private String STEXT;
	  //职务ID
	  @JsonProperty
	  private String STELL;
	  //职务名称
	  @JsonProperty
	  private String STEXT1;
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
	  public String getPLANS() {
		 return PLANS;
	  }
	  @JsonProperty
	  public void setPLANS(String pLANS) {
		 PLANS = pLANS;
	  }
	  @JsonProperty
	  public String getSTEXT2() {
		 return STEXT2;
	  }
	  @JsonProperty
	  public void setSTEXT2(String sTEXT2) {
		 STEXT2 = sTEXT2;
	  }
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
	  public String getSTELL() {
		 return STELL;
	  }
	  @JsonProperty
	  public void setSTELL(String sTELL) {
		 STELL = sTELL;
	  }
	  @JsonProperty
	  public String getSTEXT1() {
		 return STEXT1;
	  }
	  @JsonProperty
	  public void setSTEXT1(String sTEXT1) {
		 STEXT1 = sTEXT1;
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
   }
   
}
