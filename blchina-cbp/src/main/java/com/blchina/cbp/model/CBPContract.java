package com.blchina.cbp.model;

public class CBPContract {
    private Integer contractid;

    private String contractstatus;

    private String contractdate;

    private Integer orderid;

    private String vinno;

    private String totalprice;

    private String appearanceinterior;

    private String derivename;

    private String ischanged;

    private String contractUUID;

    public String getContractUUID() {
        return contractUUID;
    }

    public void setContractUUID(String contractUUID) {
        this.contractUUID = contractUUID;
    }


    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public String getContractstatus() {
        return contractstatus;
    }

    public void setContractstatus(String contractstatus) {
        this.contractstatus = contractstatus == null ? null : contractstatus.trim();
    }

    public String getContractdate() {
        return contractdate;
    }

    public void setContractdate(String contractdate) {
        this.contractdate = contractdate == null ? null : contractdate.trim();
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getVinno() {
        return vinno;
    }

    public void setVinno(String vinno) {
        this.vinno = vinno == null ? null : vinno.trim();
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice == null ? null : totalprice.trim();
    }

    public String getAppearanceinterior() {
        return appearanceinterior;
    }

    public void setAppearanceinterior(String appearanceinterior) {
        this.appearanceinterior = appearanceinterior == null ? null : appearanceinterior.trim();
    }

    public String getDerivename() {
        return derivename;
    }

    public void setDerivename(String derivename) {
        this.derivename = derivename == null ? null : derivename.trim();
    }

    public String getIschanged() {
        return ischanged;
    }

    public void setIschanged(String ischanged) {
        this.ischanged = ischanged == null ? null : ischanged.trim();
    }
}