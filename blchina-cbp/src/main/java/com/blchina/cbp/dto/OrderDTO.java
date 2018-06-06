/*
 * @(#)OrderDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.model.CBPOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 *订单DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class OrderDTO extends CBPOrder {
    private Integer contractid;

    private String contractstatus;

    private String contractdate;

    private String vinno;

    private String totalprice;

    private String appearanceinterior;

    private String derivename;

    private String ischanged;

    private String contracturl;

    private Integer lastid;

    private Integer customerinfoid;

    private String requestTime;

    private  String depositSum;

    private  String deposittotal;

    private String depositStatus;

    private String employeePhone;
    private String logistStatus;
    private String senddate;
    private String logisticstatus;
    private String retcarid;
    private String apptime;
    private String vhcle;
    private String earnest;
    private String logisticinfo;
    private String employeeName;
    private Object[] employelist;
    private String employeeSet;
    private String customerSet;

    public String getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(String employeeSet) {
        this.employeeSet = employeeSet;
    }

    public String getCustomerSet() {
        return customerSet;
    }

    public void setCustomerSet(String customerSet) {
        this.customerSet = customerSet;
    }

    public Object[] getEmployelist() {
        return employelist;
    }

    public void setEmployelist(Object[] employelist) {
        this.employelist = employelist;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLogisticinfo() {
        return logisticinfo;
    }

    public void setLogisticinfo(String logisticinfo) {
        this.logisticinfo = logisticinfo;
    }

    public String getEarnest() {
        return earnest;
    }

    public void setEarnest(String earnest) {
        this.earnest = earnest;
    }

    public String getVhcle() {
        return vhcle;
    }

    public void setVhcle(String vhcle) {
        this.vhcle = vhcle;
    }

    public String getApptime() {
        return apptime;
    }

    public void setApptime(String apptime) {
        this.apptime = apptime;
    }

    public String getLogisticstatus() {
        return logisticstatus;
    }

    public void setLogisticstatus(String logisticstatus) {
        this.logisticstatus = logisticstatus;
    }

    public String getRetcarid() {
        return retcarid;
    }

    public void setRetcarid(String retcarid) {
        this.retcarid = retcarid;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getLogistStatus() {
        return logistStatus;
    }

    public void setLogistStatus(String logistStatus) {
        this.logistStatus = logistStatus;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    private String status= CBPConstant.MessageStatusEnum.UNSET.getType();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getDepositSum() {
        return depositSum;
    }

    public void setDepositSum(String depositSum) {
        this.depositSum = depositSum;
    }

    public String getDeposittotal() {
        return deposittotal;
    }

    public void setDeposittotal(String deposittotal) {
        this.deposittotal = deposittotal;
    }

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public Integer getCustomerinfoid() {
        return customerinfoid;
    }

    public void setCustomerinfoid(Integer customerinfoid) {
        this.customerinfoid = customerinfoid;
    }

    private String ZZF2;

    private String ZZF3;

    public Integer getLastid() {
        return lastid;
    }

    public void setLastid(Integer lastid) {
        this.lastid = lastid;
    }

    public String getContracturl() {
        return contracturl;
    }

    public void setContracturl(String contracturl) {
        this.contracturl = contracturl;
    }

    public String getZZF2() {
        return ZZF2;
    }

    public void setZZF2(String ZZF2) {
        this.ZZF2 = ZZF2;
    }

    public String getZZF3() {
        return ZZF3;
    }

    public void setZZF3(String ZZF3) {
        this.ZZF3 = ZZF3;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public String getContractstatus() {
        return contractstatus;
    }

    public void setContractstatus(String contractstatus) {
        this.contractstatus = contractstatus;
    }

    public String getContractdate() {
        return contractdate;
    }

    public void setContractdate(String contractdate) {
        this.contractdate = contractdate;
    }

    @Override
    public String getVinno() {
        return vinno;
    }

    @Override
    public void setVinno(String vinno) {
        this.vinno = vinno;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getAppearanceinterior() {
        return appearanceinterior;
    }

    public void setAppearanceinterior(String appearanceinterior) {
        this.appearanceinterior = appearanceinterior;
    }

    public String getDerivename() {
        return derivename;
    }

    public void setDerivename(String derivename) {
        this.derivename = derivename;
    }

    public String getIschanged() {
        return ischanged;
    }

    public void setIschanged(String ischanged) {
        this.ischanged = ischanged;
    }
}
