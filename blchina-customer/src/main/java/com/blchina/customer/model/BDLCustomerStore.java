package com.blchina.customer.model;

public class BDLCustomerStore {
    private Integer customerstoreid;

    private Integer customerid;

    private String openid;

    private String brandid;

    public Integer getCustomerstoreid() {
        return customerstoreid;
    }

    public void setCustomerstoreid(Integer customerstoreid) {
        this.customerstoreid = customerstoreid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }
}