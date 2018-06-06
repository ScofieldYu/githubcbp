package com.blchina.cbp.model;


public class CBPOrder {
    private Integer orderid;

    private Integer customerid;

    private String orderstatus;

    private String orderdate;

    private String cartype;

    private String vinno;

    private String saporderid;

    private Integer employeeid;

    private String delivertime;

    private String phonenumber;

    private String idcardnum;

    private String organizecode;

    private String buyeridcardnum;

    private String ordertype;

    private String brandid;

    private String realvinno;

    private String customername;

    private String buyername;

    private String organizename;

    private String contractflag;
    
    private String carid;
    
    private String allprice;

    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAllprice() {
      return allprice;
   }

   public void setAllprice(String allprice) {
      this.allprice = allprice;
   }

   public String getCarid() {
      return carid;
   }

   public void setCarid(String carid) {
      this.carid = carid;
   }

   public String getContractflag() {
        return contractflag;
    }

    public void setContractflag(String contractflag) {
        this.contractflag = contractflag;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus == null ? null : orderstatus.trim();
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate == null ? null : orderdate.trim();
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype == null ? null : cartype.trim();
    }

    public String getVinno() {
        return vinno;
    }

    public void setVinno(String vinno) {
        this.vinno = vinno == null ? null : vinno.trim();
    }

    public String getSaporderid() {
        return saporderid;
    }

    public void setSaporderid(String saporderid) {
        this.saporderid = saporderid == null ? null : saporderid.trim();
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public String getDelivertime() {
        return delivertime;
    }

    public void setDelivertime(String delivertime) {
        this.delivertime = delivertime == null ? null : delivertime.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public String getIdcardnum() {
        return idcardnum;
    }

    public void setIdcardnum(String idcardnum) {
        this.idcardnum = idcardnum == null ? null : idcardnum.trim();
    }

    public String getOrganizecode() {
        return organizecode;
    }

    public void setOrganizecode(String organizecode) {
        this.organizecode = organizecode == null ? null : organizecode.trim();
    }

    public String getBuyeridcardnum() {
        return buyeridcardnum;
    }

    public void setBuyeridcardnum(String buyeridcardnum) {
        this.buyeridcardnum = buyeridcardnum == null ? null : buyeridcardnum.trim();
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    public String getRealvinno() {
        return realvinno;
    }

    public void setRealvinno(String realvinno) {
        this.realvinno = realvinno == null ? null : realvinno.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public String getOrganizename() {
        return organizename;
    }

    public void setOrganizename(String organizename) {
        this.organizename = organizename == null ? null : organizename.trim();
    }
}