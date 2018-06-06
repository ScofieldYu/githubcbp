package com.blchina.boutique.model;

public class BDLBoutique {
    private Integer boutiqueid;

    private String boutiquename;

    private String boutiqueprice;

    private String serialnumber;

    private String stock;

    private String sapboutiqueid;

    public Integer getBoutiqueid() {
        return boutiqueid;
    }

    public void setBoutiqueid(Integer boutiqueid) {
        this.boutiqueid = boutiqueid;
    }

    public String getBoutiquename() {
        return boutiquename;
    }

    public void setBoutiquename(String boutiquename) {
        this.boutiquename = boutiquename == null ? null : boutiquename.trim();
    }

    public String getBoutiqueprice() {
        return boutiqueprice;
    }

    public void setBoutiqueprice(String boutiqueprice) {
        this.boutiqueprice = boutiqueprice == null ? null : boutiqueprice.trim();
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber == null ? null : serialnumber.trim();
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock == null ? null : stock.trim();
    }

    public String getSapboutiqueid() {
        return sapboutiqueid;
    }

    public void setSapboutiqueid(String sapboutiqueid) {
        this.sapboutiqueid = sapboutiqueid == null ? null : sapboutiqueid.trim();
    }
}