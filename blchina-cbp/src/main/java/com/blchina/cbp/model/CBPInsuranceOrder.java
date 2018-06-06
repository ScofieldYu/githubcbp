package com.blchina.cbp.model;

public class CBPInsuranceOrder {
    private Integer insuranceorderid;

    private Integer orderid;

    private String insurancelimit;

    private Integer insuranceid;

    private String expense;

    private String isnodeductible;

    private String insurancetype;

    private String insurancename;

    private Integer insurancecompanyid;

    private String insurancecompanyname;

    private String insurancebeneficiary;

    private String insuranceinvoice;

    private String status;

    private String giveinsurance;

    public String getGiveinsurance() {
        return giveinsurance;
    }

    public void setGiveinsurance(String giveinsurance) {
        this.giveinsurance = giveinsurance;
    }

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

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense == null ? null : expense.trim();
    }

    public String getIsnodeductible() {
        return isnodeductible;
    }

    public void setIsnodeductible(String isnodeductible) {
        this.isnodeductible = isnodeductible == null ? null : isnodeductible.trim();
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

    public Integer getInsurancecompanyid() {
        return insurancecompanyid;
    }

    public void setInsurancecompanyid(Integer insurancecompanyid) {
        this.insurancecompanyid = insurancecompanyid;
    }

    public String getInsurancecompanyname() {
        return insurancecompanyname;
    }

    public void setInsurancecompanyname(String insurancecompanyname) {
        this.insurancecompanyname = insurancecompanyname == null ? null : insurancecompanyname.trim();
    }

    public String getInsurancebeneficiary() {
        return insurancebeneficiary;
    }

    public void setInsurancebeneficiary(String insurancebeneficiary) {
        this.insurancebeneficiary = insurancebeneficiary == null ? null : insurancebeneficiary.trim();
    }

    public String getInsuranceinvoice() {
        return insuranceinvoice;
    }

    public void setInsuranceinvoice(String insuranceinvoice) {
        this.insuranceinvoice = insuranceinvoice == null ? null : insuranceinvoice.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}