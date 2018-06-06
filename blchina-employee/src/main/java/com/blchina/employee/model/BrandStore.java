/*   
 * @(#)BrandStore.java       2018年2月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.model;

/** 
 * 此类功能描述    
 *
 * @author zhangtong 
 * @since JDK 1.7 
 */
public class BrandStore {
	private String brandId;
	
	private String storeCnShort;

	

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getStoreCnShort() {
		return storeCnShort;
	}

	public void setStoreCnShort(String storeCnShort) {
		this.storeCnShort = storeCnShort;
	}

	@Override
	public String toString() {
		return "BrandStore [brandId=" + brandId + ", storeCnShort="
				+ storeCnShort + "]";
	}
	
}
