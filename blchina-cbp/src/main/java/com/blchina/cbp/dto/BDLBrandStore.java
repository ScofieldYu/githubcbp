package com.blchina.cbp.dto;

import java.util.List;

import net.sf.json.JSONArray;

public class BDLBrandStore<BrandStore> {
	
	private String brandId;//门店号
	
	private String storeCnShort;//品牌门店简称
	
	private Integer brandType;//品牌类型
	
	private String brandName;//品牌名称
	
	private Integer employeeId;//用户id
	
	private Integer titleId;//用户职务
	
	private List<PaperCostsDTO> brandStoreList;
	
	public List<PaperCostsDTO> getBrandStoreList() {
      return brandStoreList;
   }

   public void setBrandStoreList(List<PaperCostsDTO> brandStoreList) {
      this.brandStoreList = brandStoreList;
   }
	
	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public String getStoreCnShort() {
		return storeCnShort;
	}

	public void setStoreCnShort(String storeCnShort) {
		this.storeCnShort = storeCnShort;
	}

	public Integer getBrandType() {
		return brandType;
	}

	public void setBrandType(Integer brandType) {
		this.brandType = brandType;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	
}
