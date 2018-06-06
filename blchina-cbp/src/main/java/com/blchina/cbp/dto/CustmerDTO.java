/*
 * @(#)CustmerDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;


import com.blchina.cbp.model.BDLCustomer;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *客户DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustmerDTO extends BDLCustomer {
    private String  secret;
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


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getIsindex() {
        return isindex;
    }

    public void setIsindex(String isindex) {
        this.isindex = isindex;
    }

    public String getOrganizecode() {
        return organizecode;
    }

    public void setOrganizecode(String organizecode) {
        this.organizecode = organizecode;
    }

    public String getOrganizename() {
        return organizename;
    }

    public void setOrganizename(String organizename) {
        this.organizename = organizename;
    }

    public String getIsseal() {
        return isseal;
    }

    public void setIsseal(String isseal) {
        this.isseal = isseal;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getBuyeridcardnum() {
        return buyeridcardnum;
    }

    public void setBuyeridcardnum(String buyeridcardnum) {
        this.buyeridcardnum = buyeridcardnum;
    }

    public String getCustomercarduuid() {
        return customercarduuid;
    }

    public void setCustomercarduuid(String customercarduuid) {
        this.customercarduuid = customercarduuid;
    }

    public String getBuyeridcarduuid() {
        return buyeridcarduuid;
    }

    public void setBuyeridcarduuid(String buyeridcarduuid) {
        this.buyeridcarduuid = buyeridcarduuid;
    }

    public String getBusilicenseuuid() {
        return busilicenseuuid;
    }

    public void setBusilicenseuuid(String busilicenseuuid) {
        this.busilicenseuuid = busilicenseuuid;
    }

    public String getAttorneyuuid() {
        return attorneyuuid;
    }

    public void setAttorneyuuid(String attorneyuuid) {
        this.attorneyuuid = attorneyuuid;
    }

    public String getSigncustomerid() {
        return signcustomerid;
    }

    public void setSigncustomerid(String signcustomerid) {
        this.signcustomerid = signcustomerid;
    }
}
