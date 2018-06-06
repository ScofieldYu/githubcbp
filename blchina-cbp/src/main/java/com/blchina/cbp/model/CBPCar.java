package com.blchina.cbp.model;

public class CBPCar {
    private Integer carid;

    private Integer orderid;

    private String carbrand;

    private String carseries;

    private String carappearance;

    private String carinterior;

    private String caryear;

    private String cartype;

    private String vinno;

    private String carprice;

    private String discount;

    private String cardofo;

    public Integer getCarid() {
        return carid;
    }

    public void setCarid(Integer carid) {
        this.carid = carid;
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

    public String getCarprice() {
        return carprice;
    }

    public void setCarprice(String carprice) {
        this.carprice = carprice == null ? null : carprice.trim();
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount == null ? null : discount.trim();
    }

    public String getCardofo() {
        return cardofo;
    }

    public void setCardofo(String cardofo) {
        this.cardofo = cardofo == null ? null : cardofo.trim();
    }
}