package com.blchina.cbp.model;

public class CBPOutpocket {
    private Integer outpocketid;

    private Integer orderid;

    private String outpocketuuid;

    public Integer getOutpocketid() {
        return outpocketid;
    }

    public void setOutpocketid(Integer outpocketid) {
        this.outpocketid = outpocketid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOutpocketuuid() {
        return outpocketuuid;
    }

    public void setOutpocketuuid(String outpocketuuid) {
        this.outpocketuuid = outpocketuuid == null ? null : outpocketuuid.trim();
    }
}