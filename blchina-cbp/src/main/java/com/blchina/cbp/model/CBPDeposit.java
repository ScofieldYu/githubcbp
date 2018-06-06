package com.blchina.cbp.model;

public class CBPDeposit {
    private Integer depositid;

    private String saporderid;

    private Integer bukrs;

    private Integer belnr;

    private Integer gjahr;

    private String depositfirst;

    private String depositstatus;

    private String depositsum;

    public Integer getDepositid() {
        return depositid;
    }

    public void setDepositid(Integer depositid) {
        this.depositid = depositid;
    }

    public String getSaporderid() {
        return saporderid;
    }

    public void setSaporderid(String saporderid) {
        this.saporderid = saporderid == null ? null : saporderid.trim();
    }

    public Integer getBukrs() {
        return bukrs;
    }

    public void setBukrs(Integer bukrs) {
        this.bukrs = bukrs;
    }

    public Integer getBelnr() {
        return belnr;
    }

    public void setBelnr(Integer belnr) {
        this.belnr = belnr;
    }

    public Integer getGjahr() {
        return gjahr;
    }

    public void setGjahr(Integer gjahr) {
        this.gjahr = gjahr;
    }

    public String getDepositfirst() {
        return depositfirst;
    }

    public void setDepositfirst(String depositfirst) {
        this.depositfirst = depositfirst == null ? null : depositfirst.trim();
    }

    public String getDepositstatus() {
        return depositstatus;
    }

    public void setDepositstatus(String depositstatus) {
        this.depositstatus = depositstatus == null ? null : depositstatus.trim();
    }

    public String getDepositsum() {
        return depositsum;
    }

    public void setDepositsum(String depositsum) {
        this.depositsum = depositsum == null ? null : depositsum.trim();
    }
}