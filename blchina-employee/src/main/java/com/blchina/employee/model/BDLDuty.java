package com.blchina.employee.model;

public class BDLDuty {
    private Integer dutyid;

    private String dutyname;

    private Integer departmentid;

    private String departmentname;

    private Integer titleid;

    private String titlename;

    private String startdate;

    private String enddate;

    private String zzf1;

    private String zzf2;

    private String zzf3;

    public Integer getDutyid() {
        return dutyid;
    }

    public void setDutyid(Integer dutyid) {
        this.dutyid = dutyid;
    }

    public String getDutyname() {
        return dutyname;
    }

    public void setDutyname(String dutyname) {
        this.dutyname = dutyname == null ? null : dutyname.trim();
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname == null ? null : departmentname.trim();
    }

    public Integer getTitleid() {
        return titleid;
    }

    public void setTitleid(Integer titleid) {
        this.titleid = titleid;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename == null ? null : titlename.trim();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public String getZzf1() {
        return zzf1;
    }

    public void setZzf1(String zzf1) {
        this.zzf1 = zzf1 == null ? null : zzf1.trim();
    }

    public String getZzf2() {
        return zzf2;
    }

    public void setZzf2(String zzf2) {
        this.zzf2 = zzf2 == null ? null : zzf2.trim();
    }

    public String getZzf3() {
        return zzf3;
    }

    public void setZzf3(String zzf3) {
        this.zzf3 = zzf3 == null ? null : zzf3.trim();
    }
}