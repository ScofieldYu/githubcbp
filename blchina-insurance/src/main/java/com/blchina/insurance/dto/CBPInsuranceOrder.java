package com.blchina.insurance.dto;

public class CBPInsuranceOrder {
    private Integer insuranceorderid;

    private Integer orderid;

    private String insurancelimit;

    private Integer insuranceid;

    private String isnodeductible;

    public Integer getInsuranceorderid() {
        return insuranceorderid;
    }

    public void setInsuranceorderid(Integer insuranceorderid) {
        this.insuranceorderid = insuranceorderid;
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

    public Integer getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(Integer insuranceid) {
        this.insuranceid = insuranceid;
    }

    public String getIsnodeductible() {
        return isnodeductible;
    }

    public void setIsnodeductible(String isnodeductible) {
        this.isnodeductible = isnodeductible == null ? null : isnodeductible.trim();
    }
}