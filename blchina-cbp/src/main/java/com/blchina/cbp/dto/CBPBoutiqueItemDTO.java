/*   
 * @(#)CBPBoutiqueItemDTO.java       2018年3月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPBoutiqueOrder;

/** 
 * 此类功能描述    
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
public class CBPBoutiqueItemDTO {
	 private Integer boutiqueitemid;

	    private Integer boutiqueorderid;

	    private String boutiquename;

	    private String boutiqueprice;

	    private String serialnumber;
	    
	    private Integer orderid;
	    

		public Integer getOrderid() {
			return orderid;
		}

		public void setOrderid(Integer orderid) {
			this.orderid = orderid;
		}

		public Integer getBoutiqueitemid() {
	        return boutiqueitemid;
	    }

	    public void setBoutiqueitemid(Integer boutiqueitemid) {
	        this.boutiqueitemid = boutiqueitemid;
	    }

	    public Integer getBoutiqueorderid() {
	        return boutiqueorderid;
	    }

	    public void setBoutiqueorderid(Integer boutiqueorderid) {
	        this.boutiqueorderid = boutiqueorderid;
	    }

	    public String getBoutiquename() {
	        return boutiquename;
	    }

	    public void setBoutiquename(String boutiquename) {
	        this.boutiquename = boutiquename == null ? null : boutiquename.trim();
	    }

	    public String getBoutiqueprice() {
	        return boutiqueprice;
	    }

	    public void setBoutiqueprice(String boutiqueprice) {
	        this.boutiqueprice = boutiqueprice == null ? null : boutiqueprice.trim();
	    }

	    public String getSerialnumber() {
	        return serialnumber;
	    }

	    public void setSerialnumber(String serialnumber) {
	        this.serialnumber = serialnumber == null ? null : serialnumber.trim();
	    }
}
