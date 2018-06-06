package com.blchina.insurance.model;

public class BDLInsuranceCompany {
    private Integer insurancecompanyid;

    private String insurancecompanyname;

    private String companylogo;

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

    public String getCompanylogo() {
        return companylogo;
    }

    public void setCompanylogo(String companylogo) {
        this.companylogo = companylogo == null ? null : companylogo.trim();
    }
}