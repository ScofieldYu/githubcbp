package com.blchina.cbp.model;

public class CBPReceipt {
    private Integer receiptid;

    private String saporderid;

    private String receiptuuid;

    private String receiptname;

    private String receipttype;

    private String receiptsum;

    private String receiptdate;

    public Integer getReceiptid() {
        return receiptid;
    }

    public void setReceiptid(Integer receiptid) {
        this.receiptid = receiptid;
    }

    public String getSaporderid() {
        return saporderid;
    }

    public void setSaporderid(String saporderid) {
        this.saporderid = saporderid == null ? null : saporderid.trim();
    }

    public String getReceiptuuid() {
        return receiptuuid;
    }

    public void setReceiptuuid(String receiptuuid) {
        this.receiptuuid = receiptuuid == null ? null : receiptuuid.trim();
    }

    public String getReceiptname() {
        return receiptname;
    }

    public void setReceiptname(String receiptname) {
        this.receiptname = receiptname == null ? null : receiptname.trim();
    }

    public String getReceipttype() {
        return receipttype;
    }

    public void setReceipttype(String receipttype) {
        this.receipttype = receipttype == null ? null : receipttype.trim();
    }

    public String getReceiptsum() {
        return receiptsum;
    }

    public void setReceiptsum(String receiptsum) {
        this.receiptsum = receiptsum == null ? null : receiptsum.trim();
    }

    public String getReceiptdate() {
        return receiptdate;
    }

    public void setReceiptdate(String receiptdate) {
        this.receiptdate = receiptdate == null ? null : receiptdate.trim();
    }
}