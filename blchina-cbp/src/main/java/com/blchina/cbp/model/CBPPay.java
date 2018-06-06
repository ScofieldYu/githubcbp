package com.blchina.cbp.model;

public class CBPPay {
    private Integer payid;

    private String paystatus;

    private String paysum;

    private String payclass;

    private String paytype;

    private Integer orderid;

    private String openid;

    private String isrecnotiify;

    private String banktype;

    private String paytime;

    private String tradetype;

    public Integer getPayid() {
        return payid;
    }

    public void setPayid(Integer payid) {
        this.payid = payid;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus == null ? null : paystatus.trim();
    }

    public String getPaysum() {
        return paysum;
    }

    public void setPaysum(String paysum) {
        this.paysum = paysum == null ? null : paysum.trim();
    }

    public String getPayclass() {
        return payclass;
    }

    public void setPayclass(String payclass) {
        this.payclass = payclass == null ? null : payclass.trim();
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getIsrecnotiify() {
        return isrecnotiify;
    }

    public void setIsrecnotiify(String isrecnotiify) {
        this.isrecnotiify = isrecnotiify == null ? null : isrecnotiify.trim();
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype == null ? null : banktype.trim();
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime == null ? null : paytime.trim();
    }

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype == null ? null : tradetype.trim();
    }
}