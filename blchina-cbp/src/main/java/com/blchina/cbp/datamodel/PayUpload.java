/*   
 * @(#)PayUpload.java       2017年12月26日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class PayUpload{
	  
	  private String saporderid;
	  
	  private String paysum;
	  
	  private String paystatus;
	  
	  private String currency;
	  
	  private String bukrs;
	  
	  private String belnr;
	  
	  private String gjahr;
	  
	  private String paytype;
	  @JsonProperty
	  private String ZZF2;
	  @JsonProperty
	  private String ZZF3;

	  public String getSaporderid() {
	     return saporderid;
	  }

	  public void setSaporderid(String saporderid) {
	     this.saporderid = saporderid;
	  }

	  public String getPaysum() {
	     return paysum;
	  }

	  public void setPaysum(String paysum) {
	     this.paysum = paysum;
	  }

	  public String getPaystatus() {
	     return paystatus;
	  }

	  public void setPaystatus(String paystatus) {
	     this.paystatus = paystatus;
	  }

	  public String getCurrency() {
	     return currency;
	  }

	  public void setCurrency(String currency) {
	     this.currency = currency;
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

	  public String getPaytype() {
	     return paytype;
	  }

	  public void setPaytype(String paytype) {
	     this.paytype = paytype;
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
