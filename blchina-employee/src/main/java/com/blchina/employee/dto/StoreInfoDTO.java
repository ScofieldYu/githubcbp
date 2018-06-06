/*   
 * @(#)StoreInfoDTO.java       2017年12月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.employee.dto;

/**
 * 门店传输DTO
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
public class StoreInfoDTO {
   private Long storeId;

   private String brandId;

   private String storeName;

   private String storeEnShort;

   private String storeCnShort;

   private String area;

   public Long getStoreId() {
	  return storeId;
   }

   public void setStoreId(Long storeId) {
	  this.storeId = storeId;
   }

   public String getBrandId() {
	  return brandId;
   }

   public void setBrandId(String brandId) {
	  this.brandId = brandId;
   }

   public String getStoreName() {
	  return storeName;
   }

   public void setStoreName(String storeName) {
	  this.storeName = storeName;
   }

   public String getStoreEnShort() {
	  return storeEnShort;
   }

   public void setStoreEnShort(String storeEnShort) {
	  this.storeEnShort = storeEnShort;
   }

   public String getStoreCnShort() {
	  return storeCnShort;
   }

   public void setStoreCnShort(String storeCnShort) {
	  this.storeCnShort = storeCnShort;
   }

   public String getArea() {
	  return area;
   }

   public void setArea(String area) {
	  this.area = area;
   }
}
