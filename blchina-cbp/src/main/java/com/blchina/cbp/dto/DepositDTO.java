/*   
 * @(#)DepositDTO.java       2017年12月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 定金DTO    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class DepositDTO {
   
   @JsonProperty
   private List<Deposit> Records;
   
   @JsonIgnore
   public List<Deposit> getRecords() {
      return Records;
   }

   @JsonIgnore
   public void setRecords(List<Deposit> records) {
      Records = records;
   }


   public static class Deposit{
	  
	  private String saporderid;//订单id
	  
	  private String bukrs;//公司代码 
	  
	  private String belnr;//会计凭证码
	  
	  private String gjahr;//会计年度
	  
	  private String depositstatus;//定金状态(支付、未支付）
	  
	  private String depositfirst;//第一次定金
	  
	  private String depositsum;//定金总金额
	  
	  private String currency;//货币码
	  @JsonProperty
	  private String ZZF2;
	  @JsonProperty
	  private String ZZF3;
	  
	  public String getDepositfirst() {
	     return depositfirst;
	  }
	  public void setDepositfirst(String depositfirst) {
	     this.depositfirst = depositfirst;
	  }
	  public String getSaporderid() {
	     return saporderid;
	  }
	  public void setSaporderid(String saporderid) {
	     this.saporderid = saporderid;
	  }
	  public String getBukrs() {
	     return bukrs;
	  }
	  public void setBukrs(String bukrs) {
	     this.bukrs = bukrs;
	  }
	  public String getBelnr() {
	     return belnr;
	  }
	  public void setBelnr(String belnr) {
	     this.belnr = belnr;
	  }
	  public String getGjahr() {
	     return gjahr;
	  }
	  public void setGjahr(String gjahr) {
	     this.gjahr = gjahr;
	  }
	  public String getDepositstatus() {
	     return depositstatus;
	  }
	  public void setDepositstatus(String depositstatus) {
	     this.depositstatus = depositstatus;
	  }
	  public String getDepositsum() {
	     return depositsum;
	  }
	  public void setDepositsum(String depositsum) {
	     this.depositsum = depositsum;
	  }
	  public String getCurrency() {
	     return currency;
	  }
	  public void setCurrency(String currency) {
	     this.currency = currency;
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
