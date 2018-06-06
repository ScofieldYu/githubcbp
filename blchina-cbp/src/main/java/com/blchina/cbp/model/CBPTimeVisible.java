package com.blchina.cbp.model;

public class CBPTimeVisible {
    private Integer timevisibleid;

    private Integer customerid;

    private Integer employeeid;

    private Integer orderid;

    private String isvisible;

    private String date;

    public Integer getTimevisibleid() {
        return timevisibleid;
    }

    public void setTimevisibleid(Integer timevisibleid) {
        this.timevisibleid = timevisibleid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(String isvisible) {
        this.isvisible = isvisible == null ? null : isvisible.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }
}