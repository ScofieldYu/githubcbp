/*
 * @(#)BDLCustomerStoreService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.service.interfaces;

import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.model.BDLCustomerStore;

/**
 *客户门店service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface BDLCustomerStoreService {
    BDLCustomerStore  getCustomerStore(BDLCustomerStore bdlCustomerStore);

    int addCustomerStore(BDLCustomerStore bcs);

    BDLCustomerStore getCustomerStoreByOpenid(String openid);

    void update(BDLCustomerStore bcs);
}
