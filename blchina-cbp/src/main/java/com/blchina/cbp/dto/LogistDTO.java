/*
 * @(#)LogistDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPLogistics;

import java.util.List;

/**
 * FileListDTO
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class LogistDTO {
    List<CBPLogistics> cbpLogistics;
    private String cartype;
    private String saporderid;

    public List<CBPLogistics> getCbpLogistics() {
        return cbpLogistics;
    }

    public void setCbpLogistics(List<CBPLogistics> cbpLogistics) {
        this.cbpLogistics = cbpLogistics;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getSaporderid() {
        return saporderid;
    }

    public void setSaporderid(String saporderid) {
        this.saporderid = saporderid;
    }
}
