/*   
 * @(#)ReceiptDTO.java       2017年11月28日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 收据DTO    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class ReceiptDTO {
   @JsonProperty
   private List<Receipt> Records;
   @JsonProperty
   public List<Receipt> getRecords() {
      return Records;
   }
   @JsonProperty
   public void setRecords(List<Receipt> records) {
      Records = records;
   }

   public static class Receipt{
	  
	  private String saporderrid;//订单ID
	  
	  private String receipttid;//收据ID
	  
	  private String receiptsum;//收据金额
	  
	  private String receipttype;//收据类型
	  
	  private String receiptfile;//收据合同文件
	  @JsonProperty
	  private String ZZF2;//备用字段2
	  @JsonProperty
	  private String ZZF3;//备用字段3
	  
	  public String getSaporderrid() {
		 return saporderrid;
	  }
	  
	  public void setSaporderrid(String saporderrid) {
		 this.saporderrid = saporderrid;
	  }
	  
	  public String getReceipttid() {
		 return receipttid;
	  }
	  
	  public void setReceipttid(String receipttid) {
		 this.receipttid = receipttid;
	  }
	  
	  public String getReceiptsum() {
		 return receiptsum;
	  }
	  
	  public void setReceiptsum(String receiptsum) {
		 this.receiptsum = receiptsum;
	  }
	  
	  public String getReceipttype() {
		 return receipttype;
	  }
	  
	  public void setReceipttype(String receipttype) {
		 this.receipttype = receipttype;
	  }
	  
	  public String getReceiptfile() {
		 return receiptfile;
	  }
	  
	  public void setReceiptfile(String receiptfile) {
		 this.receiptfile = receiptfile;
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

	  public Receipt() {
	  }
	  
   }
   
   
}
