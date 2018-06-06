/*   
 * @(#)BDLEmployeeDTO.java       2017年11月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 员工DTO    
 *
 * @author Administrator 
 * @since JDK 1.8
 */
public class BDLEmployeeDTO {
   @JsonProperty
   private List<Employee> Records;
   @JsonIgnore
   public List<Employee> getRecords() {
      return Records;
   }
   @JsonIgnore
   public void setRecords(List<Employee> records) {
      Records = records;
   }
   public static class Employee{
	  //人员编号
	  @JsonProperty
	  private String PERNR;
	  //员工或申请人的格式姓名 
	  @JsonProperty
	  private String PENAM;
	  //组织单位
	  @JsonProperty
	  private String ORGEH;
	  //职位
	  @JsonProperty
	  private String PLANS;
	  //职务
	  @JsonProperty
	  private String STELL;
	  //公司代码
	  @JsonProperty
	  private String BUKRS;
	  //人员状态
	  @JsonProperty
	  private String ZLZ_FLAG;
	  //上级域账号
	  @JsonProperty
	  private String COMM_ID_LONG;
	  //性别代码
	  @JsonProperty
	  private String GESCH;
	  //手机号码
	  @JsonProperty
	  private String TEL_PHONE;
	  //电子邮件
	  @JsonProperty
	  private String E_MAIL;
	  //出生日期
	  @JsonProperty
	  private String GBDAT;
	  //入职日期
	  @JsonProperty
	  private String BEGDA;
	  //上级职位ID
	  @JsonProperty
	  private String ZZF1;
	  //备用字段2
	  @JsonProperty
	  private String ZZF2;
	  //备用字段3
	  @JsonProperty
	  private String ZZF3;
	  @JsonProperty
	  public String getPERNR() {
		 return PERNR;
	  }
	  @JsonProperty
	  public void setPERNR(String pERNR) {
		 PERNR = pERNR;
	  }
	  @JsonProperty
	  public String getPENAM() {
		 return PENAM;
	  }
	  @JsonProperty
	  public void setPENAM(String pENAM) {
		 PENAM = pENAM;
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
	  public String getPLANS() {
		 return PLANS;
	  }
	  @JsonProperty
	  public void setPLANS(String pLANS) {
		 PLANS = pLANS;
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
	  public String getBUKRS() {
		 return BUKRS;
	  }
	  @JsonProperty
	  public void setBUKRS(String bUKRS) {
		 BUKRS = bUKRS;
	  }
	  @JsonProperty
	  public String getZLZ_FLAG() {
		 return ZLZ_FLAG;
	  }
	  @JsonProperty
	  public void setZLZ_FLAG(String zLZ_FLAG) {
		 ZLZ_FLAG = zLZ_FLAG;
	  }
	  @JsonProperty
	  public String getCOMM_ID_LONG() {
		 return COMM_ID_LONG;
	  }
	  @JsonProperty
	  public void setCOMM_ID_LONG(String cOMM_ID_LONG) {
		 COMM_ID_LONG = cOMM_ID_LONG;
	  }
	  @JsonProperty
	  public String getGESCH() {
		 return GESCH;
	  }
	  @JsonProperty
	  public void setGESCH(String gESCH) {
		 GESCH = gESCH;
	  }
	  @JsonProperty
	  public String getTEL_PHONE() {
		 return TEL_PHONE;
	  }
	  @JsonProperty
	  public void setTEL_PHONE(String tEL_PHONE) {
		 TEL_PHONE = tEL_PHONE;
	  }
	  @JsonProperty
	  public String getE_MAIL() {
		 return E_MAIL;
	  }
	  @JsonProperty
	  public void setE_MAIL(String e_MAIL) {
		 E_MAIL = e_MAIL;
	  }
	  @JsonProperty
	  public String getGBDAT() {
		 return GBDAT;
	  }
	  @JsonProperty
	  public void setGBDAT(String gBDAT) {
		 GBDAT = gBDAT;
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
