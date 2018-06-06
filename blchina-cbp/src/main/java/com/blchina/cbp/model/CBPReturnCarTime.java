package com.blchina.cbp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CBPReturnCarTime   implements Comparable{
    private Integer retcarid;

    private Integer customerid;

    private Integer employeeid;

    private Integer orderid;

    private String starttime;

    private String endtime;

    private String date;

    private String status;

    public Integer getRetcarid() {
        return retcarid;
    }

    public void setRetcarid(Integer retcarid) {
        this.retcarid = retcarid;
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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
    public int compareTo(Object o) {
        CBPReturnCarTime cbpReturnCarTime = (CBPReturnCarTime)o;
        String date = cbpReturnCarTime.getDate();
        SimpleDateFormat  sdf =new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(date);
            String dateTwo = this.getDate();
            Date parse2 = sdf.parse(dateTwo);
            if(parse.getTime()==parse2.getTime()){
                String starttime = cbpReturnCarTime.getStarttime();
                String fstarttime = this.getStarttime();
                SimpleDateFormat dayformat=new SimpleDateFormat("HH:mm");
                Date  startdate=dayformat.parse(starttime);
                Date  fstartdate=dayformat.parse(fstarttime);
                if(startdate.getTime()>fstartdate.getTime()){
                     return -1;
                }else {
                    return 1;
                }
            } else if(parse.getTime()>parse2.getTime()){
                return -1;
            }else{
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
           return -1;
        }

    }
}