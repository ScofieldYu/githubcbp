package com.blchina.cbp.model;

public class CBPTimePeriod {
    private Integer timeperiodid;

    private Integer timeconfid;

    private String starttime;

    private String endtime;

    public Integer getTimeperiodid() {
        return timeperiodid;
    }

    public void setTimeperiodid(Integer timeperiodid) {
        this.timeperiodid = timeperiodid;
    }

    public Integer getTimeconfid() {
        return timeconfid;
    }

    public void setTimeconfid(Integer timeconfid) {
        this.timeconfid = timeconfid;
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
}