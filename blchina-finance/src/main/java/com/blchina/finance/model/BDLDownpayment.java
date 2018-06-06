package com.blchina.finance.model;

public class BDLDownpayment {
    private Integer downpaymentid;

    private Integer bankid;

    private String downpaymentvalue;

    public Integer getDownpaymentid() {
        return downpaymentid;
    }

    public void setDownpaymentid(Integer downpaymentid) {
        this.downpaymentid = downpaymentid;
    }

    public Integer getBankid() {
        return bankid;
    }

    public void setBankid(Integer bankid) {
        this.bankid = bankid;
    }

    public String getDownpaymentvalue() {
        return downpaymentvalue;
    }

    public void setDownpaymentvalue(String downpaymentvalue) {
        this.downpaymentvalue = downpaymentvalue == null ? null : downpaymentvalue.trim();
    }
}