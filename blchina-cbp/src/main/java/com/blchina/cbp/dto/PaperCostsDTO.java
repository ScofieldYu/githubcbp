/*   
 * @(#)PaperCostsDTO.java       2018年2月11日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import com.blchina.common.util.string.StringUtil;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class PaperCostsDTO implements Comparable{

   private String brandId;
   
   private String storeCnShort;
   
   private Integer brandType;//品牌类型
	
   private String brandName;//品牌名称 
   
   private String paperTotalCount;
   
   private String paperTotalPrice;

   public Integer getBrandType() {
      return brandType;
   }

   public void setBrandType(Integer brandType) {
      this.brandType = brandType;
   }

   public String getBrandName() {
      return brandName;
   }

   public void setBrandName(String brandName) {
      this.brandName = brandName;
   }

   public String getStoreCnShort() {
      return storeCnShort;
   }

   public void setStoreCnShort(String storeCnShort) {
      this.storeCnShort = storeCnShort;
   }

   public String getBrandId() {
      return brandId;
   }

   public void setBrandId(String brandId) {
      this.brandId = brandId;
   }

   public String getPaperTotalCount() {
      return paperTotalCount;
   }

   public void setPaperTotalCount(String paperTotalCount) {
      this.paperTotalCount = paperTotalCount;
   }

   public String getPaperTotalPrice() {
      return paperTotalPrice;
   }

   public void setPaperTotalPrice(String paperTotalPrice) {
      this.paperTotalPrice = paperTotalPrice;
   }

   @Override
   public int compareTo(Object o) {
	  PaperCostsDTO p  = (PaperCostsDTO)o;
	  if(p.getBrandType()!=null && this.getBrandType()!=null){
		 if(p.getBrandType().intValue()>=this.getBrandType().intValue()){
			return -1;
		 }else {
			return 1;
		 }
	  }
	  return 0;
   }
   
}
