/*
 * @(#)FileListDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import java.util.List;

/**
 * FileListDTO
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class FileListDTO {
    private FileUUIDDTO[] obj;
    private String message;
    private Boolean isSuccess;
    private Integer code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public FileUUIDDTO[] getObj() {
        return obj;
    }

    public void setObj(FileUUIDDTO[] obj) {
        this.obj = obj;
    }
}
