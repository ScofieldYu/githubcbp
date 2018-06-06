/*   
 * @(#)BDLStoreDTO.java       2017年11月23日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

import java.util.List;

import com.blchina.employee.dto.BDLDepartmentDTO.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 门店DTO    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class BDLStoreDTO {
   @JsonProperty
   private List<Store> Records;
   @JsonProperty
   public List<Store> getRecords() {
      return Records;
   }
   @JsonProperty
   public void setRecords(List<Store> records) {
      Records = records;
   }

   public static class Store{
	  //门店ID
	  @JsonProperty
	  private String storeid;
	  //门店号
	  @JsonProperty
	  private String brandid;
	  //门店名称
	  @JsonProperty
	  private String storename;
	  //门店英文简称
	  @JsonProperty
	  private String storeenshort;
	  //门店中文简称
	  @JsonProperty
	  private String storecnshort;
	  //门店所在区域
	  @JsonProperty
	  private String area;
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
	  public String getStoreid() {
		 return storeid;
	  }
	  @JsonProperty
	  public void setStoreid(String storeid) {
		 this.storeid = storeid;
	  }
	  @JsonProperty
	  public String getBrandid() {
		 return brandid;
	  }
	  @JsonProperty
	  public void setBrandid(String brandid) {
		 this.brandid = brandid;
	  }
	  @JsonProperty
	  public String getStorename() {
		 return storename;
	  }
	  @JsonProperty
	  public void setStorename(String storename) {
		 this.storename = storename;
	  }
	  @JsonProperty
	  public String getStoreenshort() {
		 return storeenshort;
	  }
	  @JsonProperty
	  public void setStoreenshort(String storeenshort) {
		 this.storeenshort = storeenshort;
	  }
	  @JsonProperty
	  public String getStorecnshort() {
		 return storecnshort;
	  }
	  @JsonProperty
	  public void setStoreCnShort(String storecnshort) {
		 this.storecnshort = storecnshort;
	  }
	  @JsonProperty
	  public String getArea() {
		 return area;
	  }
	  @JsonProperty
	  public void setArea(String area) {
		 this.area = area;
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
	  public Store() {
	  }
	  
	  
   }
}
