package com.blchina.cbp.model;

import java.io.Serializable;

public class BDLCustomerInfo  implements Serializable {
    private Integer customerinfoid;

    private Integer customerid;

    private Integer storeid;

    private String accounttype;

    private String isindex;

    private String organizecode;

    private String organizename;

    private String isseal;

    private String buyername;

    private String buyeridcardnum;

    private String customercarduuid;

    private String buyeridcarduuid;

    private String busilicenseuuid;

    private String attorneyuuid;

    private String signcustomerid;

    private Integer signcount;

    public Integer getSigncount() {
        return signcount;
    }

    public void setSigncount(Integer signcount) {
        this.signcount = signcount;
    }


    public Integer getCustomerinfoid() {
        return customerinfoid;
    }

    public void setCustomerinfoid(Integer customerinfoid) {
        this.customerinfoid = customerinfoid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype == null ? null : accounttype.trim();
    }

    public String getIsindex() {
        return isindex;
    }

    public void setIsindex(String isindex) {
        this.isindex = isindex == null ? null : isindex.trim();
    }

    public String getOrganizecode() {
        return organizecode;
    }

    public void setOrganizecode(String organizecode) {
        this.organizecode = organizecode == null ? null : organizecode.trim();
    }

    public String getOrganizename() {
        return organizename;
    }

    public void setOrganizename(String organizename) {
        this.organizename = organizename == null ? null : organizename.trim();
    }

    public String getIsseal() {
        return isseal;
    }

    public void setIsseal(String isseal) {
        this.isseal = isseal == null ? null : isseal.trim();
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public String getBuyeridcardnum() {
        return buyeridcardnum;
    }

    public void setBuyeridcardnum(String buyeridcardnum) {
        this.buyeridcardnum = buyeridcardnum == null ? null : buyeridcardnum.trim();
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

    public String getSigncustomerid() {
        return signcustomerid;
    }

    public void setSigncustomerid(String signcustomerid) {
        this.signcustomerid = signcustomerid == null ? null : signcustomerid.trim();
    }
}