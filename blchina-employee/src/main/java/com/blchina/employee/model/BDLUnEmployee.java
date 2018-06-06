package com.blchina.employee.model;

public class BDLUnEmployee {
    private Integer unemployeeid;

    private String employeename;

    private Integer departmentid;

    private Integer dutyid;

    private Integer titleid;

    private String companycode;

    private String status;

    private String updomainaccount;

    private String sex;

    private Long phonenumber;

    private String email;

    private String birth;

    private String enterdate;

    private Integer updutyid;

    private String zzf1;

    private String zzf2;

    public Integer getUnemployeeid() {
        return unemployeeid;
    }

    public void setUnemployeeid(Integer unemployeeid) {
        this.unemployeeid = unemployeeid;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename == null ? null : employeename.trim();
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getDutyid() {
        return dutyid;
    }

    public void setDutyid(Integer dutyid) {
        this.dutyid = dutyid;
    }

    public Integer getTitleid() {
        return titleid;
    }

    public void setTitleid(Integer titleid) {
        this.titleid = titleid;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode == null ? null : companycode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUpdomainaccount() {
        return updomainaccount;
    }

    public void setUpdomainaccount(String updomainaccount) {
        this.updomainaccount = updomainaccount == null ? null : updomainaccount.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    public String getEnterdate() {
        return enterdate;
    }

    public void setEnterdate(String enterdate) {
        this.enterdate = enterdate == null ? null : enterdate.trim();
    }

    public Integer getUpdutyid() {
        return updutyid;
    }

    public void setUpdutyid(Integer updutyid) {
        this.updutyid = updutyid;
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
}