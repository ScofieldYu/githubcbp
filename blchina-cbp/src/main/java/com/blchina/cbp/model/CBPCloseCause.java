package com.blchina.cbp.model;

public class CBPCloseCause {
    private Integer closecauseid;

    private Integer contractid;

    private String description;

    private String closetype;

    public Integer getClosecauseid() {
        return closecauseid;
    }

    public void setClosecauseid(Integer closecauseid) {
        this.closecauseid = closecauseid;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getClosetype() {
        return closetype;
    }

    public void setClosetype(String closetype) {
        this.closetype = closetype == null ? null : closetype.trim();
    }
}