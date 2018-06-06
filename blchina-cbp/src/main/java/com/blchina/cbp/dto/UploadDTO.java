/*
 * @(#)UploadDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 *上传工具类
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class UploadDTO {
    private String[] file;
    private String[] uuidList;
    private String idcardnum;
    private String  type;
    private String filetypeext;
    private Integer orderid;
    private Integer customerid;
    private Integer employeeid;
    private String buyeridcardnum;
    private String organizecode;
    private Integer customerinfoid;
    private Integer contractid;
    private String docurl;
    private String  sapOrderId;
    private String[] typeList;

    public String[] getTypeList() {
        return typeList;
    }

    public void setTypeList(String[] typeList) {
        this.typeList = typeList;
    }

    public String getFiletypeext() {
        return filetypeext;
    }

    public void setFiletypeext(String filetypeext) {
        this.filetypeext = filetypeext;
    }

    public String getSapOrderId() {
        return sapOrderId;
    }

    public void setSapOrderId(String sapOrderId) {
        this.sapOrderId = sapOrderId;
    }

    public String getIdcardnum() {
        return idcardnum;
    }

    public void setIdcardnum(String idcardnum) {
        this.idcardnum = idcardnum;
    }

    public String[] getUuidList() {
        return uuidList;
    }

    public void setUuidList(String[] uuidList) {
        this.uuidList = uuidList;
    }

    public String getBuyeridcardnum() {
        return buyeridcardnum;
    }

    public void setBuyeridcardnum(String buyeridcardnum) {
        this.buyeridcardnum = buyeridcardnum;
    }

    public String getOrganizecode() {
        return organizecode;
    }

    public void setOrganizecode(String organizecode) {
        this.organizecode = organizecode;
    }

    public String getDocurl() {
        return docurl;
    }

    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }

    public Integer getCustomerinfoid() {
        return customerinfoid;
    }

    public void setCustomerinfoid(Integer customerinfoid) {
        this.customerinfoid = customerinfoid;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public String[] getFile() {
        return file;
    }

    public void setFile(String[] file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
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



}
