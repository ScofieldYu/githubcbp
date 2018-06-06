/*
 * @(#)EmployeeDTO.java       2017年12月8日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.employee.dto;

import com.blchina.employee.model.BDLEmployee;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 员工DTOS
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO extends BDLEmployee{
    private String titleName;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
