package com.blchina.cbp.model;

import java.util.List;

import net.sf.json.JSONArray;

public class CBPBoutiqueOrder {
    private Integer boutiqueorderid;

    private Integer orderid;

    private String createtime;

    private Integer boutiqueconsultantid;

    private String boutiqueconsultantname;

    private String boutiqueconsultantphone;

    private String status;
    
    private List<CBPBoutiqueItem> boutiqueItemList;
    
    

    public List<CBPBoutiqueItem> getBoutiqueItemList() {
		return JSONArray.fromObject( boutiqueItemList );
	}

	public void setBoutiqueItemList(List<CBPBoutiqueItem> boutiqueItemList) {
		this.boutiqueItemList = JSONArray.fromObject( boutiqueItemList );
	}

	public Integer getBoutiqueorderid() {
        return boutiqueorderid;
    }

    public void setBoutiqueorderid(Integer boutiqueorderid) {
        this.boutiqueorderid = boutiqueorderid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public Integer getBoutiqueconsultantid() {
        return boutiqueconsultantid;
    }

    public void setBoutiqueconsultantid(Integer boutiqueconsultantid) {
        this.boutiqueconsultantid = boutiqueconsultantid;
    }

    public String getBoutiqueconsultantname() {
        return boutiqueconsultantname;
    }

    public void setBoutiqueconsultantname(String boutiqueconsultantname) {
        this.boutiqueconsultantname = boutiqueconsultantname == null ? null : boutiqueconsultantname.trim();
    }

    public String getBoutiqueconsultantphone() {
        return boutiqueconsultantphone;
    }

    public void setBoutiqueconsultantphone(String boutiqueconsultantphone) {
        this.boutiqueconsultantphone = boutiqueconsultantphone == null ? null : boutiqueconsultantphone.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}