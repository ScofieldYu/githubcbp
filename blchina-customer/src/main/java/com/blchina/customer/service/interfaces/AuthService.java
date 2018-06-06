/*
 * @(#)AuthService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.service.interfaces;

import com.blchina.customer.model.BDLCustomer;

/**
 *认证service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface AuthService {
    void AuthCustmer(BDLCustomer customer);

    void AuthCompanyCustmer(BDLCustomer customer);
}
