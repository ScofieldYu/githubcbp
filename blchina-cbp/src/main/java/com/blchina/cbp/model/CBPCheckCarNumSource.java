package com.blchina.cbp.model;

public class CBPCheckCarNumSource {
    private Integer checkcarnumsourceid;

    private Integer checkcarnumid;

    private String sourcetype;

    private String ischoiced;

    private String belongtype;

    private String sourcename;
    
    private Integer orderid;
    
    

    public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getCheckcarnumsourceid() {
        return checkcarnumsourceid;
    }

    public void setCheckcarnumsourceid(Integer checkcarnumsourceid) {
        this.checkcarnumsourceid = checkcarnumsourceid;
    }

    public Integer getCheckcarnumid() {
        return checkcarnumid;
    }

    public void setCheckcarnumid(Integer checkcarnumid) {
        this.checkcarnumid = checkcarnumid;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
    }

    public String getIschoiced() {
        return ischoiced;
    }

    public void setIschoiced(String ischoiced) {
        this.ischoiced = ischoiced == null ? null : ischoiced.trim();
    }

    public String getBelongtype() {
        return belongtype;
    }

    public void setBelongtype(String belongtype) {
        this.belongtype = belongtype == null ? null : belongtype.trim();
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename == null ? null : sourcename.trim();
    }
}