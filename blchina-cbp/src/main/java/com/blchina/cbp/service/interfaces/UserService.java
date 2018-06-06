/*
 * @(#)UserService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;
/**
 *客户操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface UserService {

    public int userLogin(String name, String passwd, String deviceId);
    public String getTitleIdByEmployeeId(Integer employeeId);
}
