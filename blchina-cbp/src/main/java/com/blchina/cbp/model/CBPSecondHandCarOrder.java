package com.blchina.cbp.model;

public class CBPSecondHandCarOrder {
    private Integer secondcarid;

    private Integer orderid;

    private String carbrand;

    private String carseries;

    private String cartype;

    private String carappearance;

    private String carinterior;

    private String caryear;

    private String kilometers;

    private String realvinno;

    private String appraiserid;

    private String appraisername;

    private String appraiserphone;

    private String reservetime;

    private String price;

    private String dealprice;

    private String exchangemethod;

    private String status;
    

	public Integer getSecondcarid() {
        return secondcarid;
    }

    public void setSecondcarid(Integer secondcarid) {
        this.secondcarid = secondcarid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand == null ? null : carbrand.trim();
    }

    public String getCarseries() {
        return carseries;
    }

    public void setCarseries(String carseries) {
        this.carseries = carseries == null ? null : carseries.trim();
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype == null ? null : cartype.trim();
    }

    public String getCarappearance() {
        return carappearance;
    }

    public void setCarappearance(String carappearance) {
        this.carappearance = carappearance == null ? null : carappearance.trim();
    }

    public String getCarinterior() {
        return carinterior;
    }

    public void setCarinterior(String carinterior) {
        this.carinterior = carinterior == null ? null : carinterior.trim();
    }

    public String getCaryear() {
        return caryear;
    }

    public void setCaryear(String caryear) {
        this.caryear = caryear == null ? null : caryear.trim();
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers == null ? null : kilometers.trim();
    }

    public String getRealvinno() {
        return realvinno;
    }

    public void setRealvinno(String realvinno) {
        this.realvinno = realvinno == null ? null : realvinno.trim();
    }

    public String getAppraiserid() {
        return appraiserid;
    }

    public void setAppraiserid(String appraiserid) {
        this.appraiserid = appraiserid == null ? null : appraiserid.trim();
    }

    public String getAppraisername() {
        return appraisername;
    }

    public void setAppraisername(String appraisername) {
        this.appraisername = appraisername == null ? null : appraisername.trim();
    }

    public String getAppraiserphone() {
        return appraiserphone;
    }

    public void setAppraiserphone(String appraiserphone) {
        this.appraiserphone = appraiserphone == null ? null : appraiserphone.trim();
    }

    public String getReservetime() {
        return reservetime;
    }

    public void setReservetime(String reservetime) {
        this.reservetime = reservetime == null ? null : reservetime.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getDealprice() {
        return dealprice;
    }

    public void setDealprice(String dealprice) {
        this.dealprice = dealprice == null ? null : dealprice.trim();
    }

    public String getExchangemethod() {
        return exchangemethod;
    }

    public void setExchangemethod(String exchangemethod) {
        this.exchangemethod = exchangemethod == null ? null : exchangemethod.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}