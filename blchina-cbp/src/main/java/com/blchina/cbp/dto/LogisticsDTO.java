package com.blchina.cbp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** 
 * 和SAP的物流接口数据模型    
 *
 * @author Scofield 
 * @since JDK 1.8
 */

public class LogisticsDTO {
   @JsonProperty
   private List<Logistics> Records;
	@JsonIgnore
	public List<Logistics> getRecords() {
		return Records;
	}
	@JsonIgnore
	public void setRecords(List<Logistics> records) {
		Records = records;
	}

	public static class Logistics implements  Comparable{
	  @JsonProperty
	  private String PRODUCT_GUID;
	  @JsonProperty
	  private String CARTYPE;
	   @JsonProperty
	   private String VHCLE;
	  @JsonProperty
	  private String STATUS;
	  @JsonProperty
	  private String DATE;
	  @JsonProperty
	  private String ZZF2;
	  @JsonProperty
	  private String ZZF3;
	  @JsonProperty
	  private String VINNO;
		@JsonProperty
		private String SENDSTATUS;

	  public Logistics() {
	  }

	  public Logistics(String PRODUCT_GUID, String CARTYPE, String STATUS,
			String DATE, String ZZF2, String ZZF3, String VINNO,String VHCLE,String SENDSTATUS) {
		 this.PRODUCT_GUID = PRODUCT_GUID;
		 this.CARTYPE = CARTYPE;
		  this.VHCLE=VHCLE;
		 this.STATUS = STATUS;
		 this.DATE = DATE;
		 this.ZZF2 = ZZF2;
		 this.ZZF3 = ZZF3;
		 this.VINNO = VINNO;
         this.SENDSTATUS=SENDSTATUS;
	  }
	   @JsonIgnore
		public String getVHCLE() {
			return VHCLE;
		}
		@JsonIgnore
		public void setVHCLE(String VHCLE) {
			this.VHCLE = VHCLE;
		}
		@JsonIgnore
		public String getSENDSTATUS() {
			return SENDSTATUS;
		}
		@JsonIgnore
		public void setSENDSTATUS(String SENDSTATUS) {
			this.SENDSTATUS = SENDSTATUS;
		}

		@JsonIgnore
	  public String getVINNO() {
		 return VINNO;
	  }

	  @JsonIgnore
	  public void setVINNO(String VINNO) {
		 this.VINNO = VINNO;
	  }

	  @JsonIgnore
	  public String getPRODUCT_GUID() {
		 return PRODUCT_GUID;
	  }

	  @JsonIgnore
	  public void setPRODUCT_GUID(String PRODUCT_GUID) {
		 this.PRODUCT_GUID = PRODUCT_GUID;
	  }

	  @JsonIgnore
	  public String getCARTYPE() {
		 return CARTYPE;
	  }

	  @JsonIgnore
	  public void setCARTYPE(String CARTYPE) {
		 this.CARTYPE = CARTYPE;
	  }

	  @JsonIgnore
	  public String getSTATUS() {
		 return STATUS;
	  }

	  @JsonIgnore
	  public void setSTATUS(String STATUS) {
		 this.STATUS = STATUS;
	  }

	  @JsonIgnore
	  public String getDATE() {
		 return DATE;
	  }

	  @JsonIgnore
	  public void setDATE(String DATE) {
		 this.DATE = DATE;
	  }

	  @JsonIgnore
	  public String getZZF2() {
		 return ZZF2;
	  }

	  @JsonIgnore
	  public void setZZF2(String ZZF2) {
		 this.ZZF2 = ZZF2;
	  }

	  @JsonIgnore
	  public String getZZF3() {
		 return ZZF3;
	  }

	  @JsonIgnore
	  public void setZZF3(String ZZF3) {
		 this.ZZF3 = ZZF3;
	  }

		@Override
		public int compareTo(Object o) {
			Logistics logistics=(Logistics)o;
			String zzf2 = logistics.getZZF2();
			if(Integer.valueOf(zzf2)>Integer.valueOf(this.getZZF2())){
                return -1;
			}
			return 1;
		}
	}

}