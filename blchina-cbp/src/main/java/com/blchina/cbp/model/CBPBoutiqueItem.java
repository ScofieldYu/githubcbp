package com.blchina.cbp.model;

public class CBPBoutiqueItem {
    private Integer boutiqueitemid;

    private Integer boutiqueorderid;

    private String boutiquename;

    private String boutiqueprice;

    private String serialnumber;

    public Integer getBoutiqueitemid() {
        return boutiqueitemid;
    }

    public void setBoutiqueitemid(Integer boutiqueitemid) {
        this.boutiqueitemid = boutiqueitemid;
    }

    public Integer getBoutiqueorderid() {
        return boutiqueorderid;
    }

    public void setBoutiqueorderid(Integer boutiqueorderid) {
        this.boutiqueorderid = boutiqueorderid;
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
}