package com.blchina.insurance.model;

public class BDLInsurance {
    private Integer insuranceid;

    private String insurancename;

    private String insurancetype;

    private String isnodeductible;

    private String expense;

    private String subjecttype;

    private String isshownodeductible;

    private String isincludelimit;

    public Integer getInsuranceid() {
        return insuranceid;
    }

    public void setInsuranceid(Integer insuranceid) {
        this.insuranceid = insuranceid;
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

    public String getIsnodeductible() {
        return isnodeductible;
    }

    public void setIsnodeductible(String isnodeductible) {
        this.isnodeductible = isnodeductible == null ? null : isnodeductible.trim();
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense == null ? null : expense.trim();
    }

    public String getSubjecttype() {
        return subjecttype;
    }

    public void setSubjecttype(String subjecttype) {
        this.subjecttype = subjecttype == null ? null : subjecttype.trim();
    }

    public String getIsshownodeductible() {
        return isshownodeductible;
    }

    public void setIsshownodeductible(String isshownodeductible) {
        this.isshownodeductible = isshownodeductible == null ? null : isshownodeductible.trim();
    }

    public String getIsincludelimit() {
        return isincludelimit;
    }

    public void setIsincludelimit(String isincludelimit) {
        this.isincludelimit = isincludelimit == null ? null : isincludelimit.trim();
    }
}