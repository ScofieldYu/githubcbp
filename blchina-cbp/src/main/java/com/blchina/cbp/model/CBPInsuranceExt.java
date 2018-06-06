package com.blchina.cbp.model;

public class CBPInsuranceExt {
    private Integer insuranceextid;

    private Integer orderid;

    private String insurancelimit;

    private String insurancetype;

    private String insurancename;

    private Integer insuranceid;

    public Integer getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(Integer insuranceid) {
        this.insuranceid = insuranceid;
    }

    public Integer getInsuranceextid() {
        return insuranceextid;
    }

    public void setInsuranceextid(Integer insuranceextid) {
        this.insuranceextid = insuranceextid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getInsurancelimit() {
        return insurancelimit;
    }

    public void setInsurancelimit(String insurancelimit) {
        this.insurancelimit = insurancelimit == null ? null : insurancelimit.trim();
    }

    public String getInsurancetype() {
        return insurancetype;
    }

    public void setInsurancetype(String insurancetype) {
        this.insurancetype = insurancetype == null ? null : insurancetype.trim();
    }

    public String getInsurancename() {
        return insurancename;
    }

    public void setInsurancename(String insurancename) {
        this.insurancename = insurancename == null ? null : insurancename.trim();
    }
}