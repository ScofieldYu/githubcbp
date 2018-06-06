package com.blchina.insurance.model;

public class BDLInsuranceStore {
    private Integer insurancestoreid;

    private Integer insuranceid;

    private Integer storeid;

    private String insurancename;

    private String insurancetype;

    public Integer getInsurancestoreid() {
        return insurancestoreid;
    }

    public void setInsurancestoreid(Integer insurancestoreid) {
        this.insurancestoreid = insurancestoreid;
    }

    public Integer getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(Integer insuranceid) {
        this.insuranceid = insuranceid;
    }

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public String getInsurancename() {
        return insurancename;
    }

    public void setInsurancename(String insurancename) {
        this.insurancename = insurancename == null ? null : insurancename.trim();
    }

    public String getInsurancetype() {
        return insurancetype;
    }

    public void setInsurancetype(String insurancetype) {
        this.insurancetype = insurancetype == null ? null : insurancetype.trim();
    }
}