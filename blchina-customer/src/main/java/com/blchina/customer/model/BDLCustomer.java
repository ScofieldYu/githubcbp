package com.blchina.customer.model;

public class BDLCustomer {
    private Integer customerid;

    private String wxheadimgurl;

    private String nickname;

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

    public String getWxheadimgurl() {
        return wxheadimgurl;
    }

    public void setWxheadimgurl(String wxheadimgurl) {
        this.wxheadimgurl = wxheadimgurl == null ? null : wxheadimgurl.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
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
        this.idcardnum = idcardnum == null ? null : idcardnum.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSapcustomerid() {
        return sapcustomerid;
    }

    public void setSapcustomerid(String sapcustomerid) {
        this.sapcustomerid = sapcustomerid == null ? null : sapcustomerid.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

   @Override
   public String toString() {
	  return "BDLCustomer [customerid=" + customerid + ", wxheadimgurl="
			+ wxheadimgurl + ", nickname=" + nickname + ", phonenumber="
			+ phonenumber + ", idcardnum=" + idcardnum + ", customername="
			+ customername + ", email=" + email + ", address=" + address
			+ ", sapcustomerid=" + sapcustomerid + ", postcode=" + postcode
			+ "]";
   }
    
}