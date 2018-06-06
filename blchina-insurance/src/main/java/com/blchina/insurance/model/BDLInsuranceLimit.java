package com.blchina.insurance.model;

public class BDLInsuranceLimit {
    private Integer insurancelimitid;

    private Integer insuranceid;

    private String limitaccount;

    private Integer insurancecompanyid;

    public Integer getInsurancelimitid() {
        return insurancelimitid;
    }

    public void setInsurancelimitid(Integer insurancelimitid) {
        this.insurancelimitid = insurancelimitid;
    }

    public Integer getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(Integer insuranceid) {
        this.insuranceid = insuranceid;
    }

    public String getLimitaccount() {
        return limitaccount;
    }

    public void setLimitaccount(String limitaccount) {
        this.limitaccount = limitaccount == null ? null : limitaccount.trim();
    }

    public Integer getInsurancecompanyid() {
        return insurancecompanyid;
    }

    public void setInsurancecompanyid(Integer insurancecompanyid) {
        this.insurancecompanyid = insurancecompanyid;
    }
}