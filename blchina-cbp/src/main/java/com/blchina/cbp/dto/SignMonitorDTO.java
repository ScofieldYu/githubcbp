/*
 * @(#)SignMonitorDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.common.util.string.StringUtil;

/**
 * 监控基类
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class SignMonitorDTO implements  Comparable{
    private String brandId;
    private Integer employeeId;
    private String employeeSum;
    private String signPecent;
    private String closeOnePecent;
    private String closeTwoPecent;
    private String closeThreePecent;
    private String closeFourPecent;
    private String unsignOnePecent;
    private String unsignTwoPecent;
    private String unsignThreePecent;
    private String unsignFourPecent;
    private String focusPecent;
    private String employeeSetPecent;
    private String customerSetSum;
    private String employeeName;
    private String dateStart;
    private String dateEnd;
    private Object[] brandType;
    private Object[] employeelist;
    private Object[] brandIdList;
    private String  order;
    private String logisticPecent;
    private Object[] managerList;

    public String getUnsignOnePecent() {
        return unsignOnePecent;
    }

    public void setUnsignOnePecent(String unsignOnePecent) {
        this.unsignOnePecent = unsignOnePecent;
    }

    public String getUnsignTwoPecent() {
        return unsignTwoPecent;
    }

    public void setUnsignTwoPecent(String unsignTwoPecent) {
        this.unsignTwoPecent = unsignTwoPecent;
    }

    public String getUnsignThreePecent() {
        return unsignThreePecent;
    }

    public void setUnsignThreePecent(String unsignThreePecent) {
        this.unsignThreePecent = unsignThreePecent;
    }

    public String getUnsignFourPecent() {
        return unsignFourPecent;
    }

    public void setUnsignFourPecent(String unsignFourPecent) {
        this.unsignFourPecent = unsignFourPecent;
    }

    public Object[] getManagerList() {
        return managerList;
    }

    public void setManagerList(Object[] managerList) {
        this.managerList = managerList;
    }

    public String getLogisticPecent() {
        return logisticPecent;
    }

    public void setLogisticPecent(String logisticPecent) {
        this.logisticPecent = logisticPecent;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Object[] getBrandIdList() {
        return brandIdList;
    }

    public void setBrandIdList(Object[] brandIdList) {
        this.brandIdList = brandIdList;
    }

    public Object[] getEmployeelist() {
        return employeelist;
    }

    public void setEmployeelist(Object[] employeelist) {
        this.employeelist = employeelist;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeSum() {
        return employeeSum;
    }

    public void setEmployeeSum(String employeeSum) {
        this.employeeSum = employeeSum;
    }

    public String getSignPecent() {
        return signPecent;
    }

    public void setSignPecent(String signPecent) {
        this.signPecent = signPecent;
    }

    public String getCloseOnePecent() {
        return closeOnePecent;
    }

    public void setCloseOnePecent(String closeOnePecent) {
        this.closeOnePecent = closeOnePecent;
    }

    public String getCloseTwoPecent() {
        return closeTwoPecent;
    }

    public void setCloseTwoPecent(String closeTwoPecent) {
        this.closeTwoPecent = closeTwoPecent;
    }

    public String getCloseThreePecent() {
        return closeThreePecent;
    }

    public void setCloseThreePecent(String closeThreePecent) {
        this.closeThreePecent = closeThreePecent;
    }

    public String getCloseFourPecent() {
        return closeFourPecent;
    }

    public void setCloseFourPecent(String closeFourPecent) {
        this.closeFourPecent = closeFourPecent;
    }

    public String getFocusPecent() {
        return focusPecent;
    }

    public void setFocusPecent(String focusPecent) {
        this.focusPecent = focusPecent;
    }

    public String getEmployeeSetPecent() {
        return employeeSetPecent;
    }

    public void setEmployeeSetPecent(String employeeSetPecent) {
        this.employeeSetPecent = employeeSetPecent;
    }

    public String getCustomerSetSum() {
        return customerSetSum;
    }

    public void setCustomerSetSum(String customerSetSum) {
        this.customerSetSum = customerSetSum;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Object[] getBrandType() {
        return brandType;
    }

    public void setBrandType(Object[] brandType) {
        this.brandType = brandType;
    }

    @Override
    public int compareTo(Object o) {
        SignMonitorDTO signMonitorDTO=(SignMonitorDTO)o;
        if(signMonitorDTO.getBrandId().equals(this.getBrandId())){
            if(StringUtil.isNullorEmpty(signMonitorDTO.getOrder())){
                return -1;
            }else{
                return 1;
            }
        }else {
            if(Integer.valueOf(signMonitorDTO.getBrandId())>Integer.valueOf(this.getBrandId())){
                return 1;
            }else {
                return -1;
            }
        }
    }
}
