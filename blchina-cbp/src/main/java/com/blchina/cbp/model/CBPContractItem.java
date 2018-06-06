package com.blchina.cbp.model;

public class CBPContractItem {
    private Integer itemid;

    private String itemname;

    private Integer contractid;

    private String contractuuid;

    private String contractresult;

    private String requesttime;

    private String contractdes;

    private String contractstatus;

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname == null ? null : itemname.trim();
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public String getContractuuid() {
        return contractuuid;
    }

    public void setContractuuid(String contractuuid) {
        this.contractuuid = contractuuid == null ? null : contractuuid.trim();
    }

    public String getContractresult() {
        return contractresult;
    }

    public void setContractresult(String contractresult) {
        this.contractresult = contractresult == null ? null : contractresult.trim();
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime == null ? null : requesttime.trim();
    }

    public String getContractdes() {
        return contractdes;
    }

    public void setContractdes(String contractdes) {
        this.contractdes = contractdes == null ? null : contractdes.trim();
    }

    public String getContractstatus() {
        return contractstatus;
    }

    public void setContractstatus(String contractstatus) {
        this.contractstatus = contractstatus == null ? null : contractstatus.trim();
    }
}