package com.blchina.cbp.model;

public class CBPOrderFile {
    private Integer orderfileid;

    private Integer orderid;

    private String customercarduuid;

    private String buyeridcarduuid;

    private String busilicenseuuid;

    private String attorneyuuid;

    public Integer getOrderfileid() {
        return orderfileid;
    }

    public void setOrderfileid(Integer orderfileid) {
        this.orderfileid = orderfileid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getCustomercarduuid() {
        return customercarduuid;
    }

    public void setCustomercarduuid(String customercarduuid) {
        this.customercarduuid = customercarduuid == null ? null : customercarduuid.trim();
    }

    public String getBuyeridcarduuid() {
        return buyeridcarduuid;
    }

    public void setBuyeridcarduuid(String buyeridcarduuid) {
        this.buyeridcarduuid = buyeridcarduuid == null ? null : buyeridcarduuid.trim();
    }

    public String getBusilicenseuuid() {
        return busilicenseuuid;
    }

    public void setBusilicenseuuid(String busilicenseuuid) {
        this.busilicenseuuid = busilicenseuuid == null ? null : busilicenseuuid.trim();
    }

    public String getAttorneyuuid() {
        return attorneyuuid;
    }

    public void setAttorneyuuid(String attorneyuuid) {
        this.attorneyuuid = attorneyuuid == null ? null : attorneyuuid.trim();
    }
}