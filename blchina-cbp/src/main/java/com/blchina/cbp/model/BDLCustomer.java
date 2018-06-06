package com.blchina.cbp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BDLCustomer implements Serializable{
    private Integer customerid;

    private String openid;

    private String store4sid;

    private String wxheadimgurl;

    private String nickname;

    private String signcustomerid;

    private Long phonenumber;

    private String idcardnum;

    private String customername;

    private String email;

    private String address;

    private String sapcustomerid;

    private String postcode;

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
        this.openid = openid;
    }

    public String getStore4sid() {
        return store4sid;
    }

    public void setStore4sid(String store4sid) {
        this.store4sid = store4sid;
    }

    public String getWxheadimgurl() {
        return wxheadimgurl;
    }

    public void setWxheadimgurl(String wxheadimgurl) {
        this.wxheadimgurl = wxheadimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSigncustomerid() {
        return signcustomerid;
    }

    public void setSigncustomerid(String signcustomerid) {
        this.signcustomerid = signcustomerid;
    }

    public Long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getIdcardnum() {
        return idcardnum;
    }

    public void setIdcardnum(String idcardnum) {
        this.idcardnum = idcardnum;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSapcustomerid() {
        return sapcustomerid;
    }

    public void setSapcustomerid(String sapcustomerid) {
        this.sapcustomerid = sapcustomerid;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}