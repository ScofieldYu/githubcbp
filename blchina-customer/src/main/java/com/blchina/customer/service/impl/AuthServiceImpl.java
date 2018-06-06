/*
 * @(#)AuthServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.service.impl;

import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.service.interfaces.AuthService;
import org.springframework.stereotype.Service;

/**
 * 客户认证service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("AuthService")
public class AuthServiceImpl implements AuthService{
    @Override
    public void AuthCustmer(BDLCustomer customer) {
//        FddClientBase clientbase = new FddClientBase("appId", "secret", "version"," url");
//        String response = clientbase.invokeSyncPersonAuto(customer.getCustomername(), customer.getEmail(), customer.getIdentitycardnum(),"0", customer.getPhonenumber().toString());
    }

    @Override
    public void AuthCompanyCustmer(BDLCustomer customer) {
    /*    SyncCompanyAuto client = new SyncCompanyAuto(appId, secret, version, url);
        String response = client.invoke(customer_name, email, id_card, mobile);
*/
    }
}
