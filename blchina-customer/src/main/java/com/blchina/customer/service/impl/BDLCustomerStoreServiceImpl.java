/*
 * @(#)BDLCustomerStoreServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.service.impl;

import com.blchina.customer.dao.BDLCustomerStoreMapper;
import com.blchina.customer.model.BDLCustomerStore;
import com.blchina.customer.service.interfaces.BDLCustomerService;
import com.blchina.customer.service.interfaces.BDLCustomerStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *客户门店service
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("BDLCustomerStoreService")
public class BDLCustomerStoreServiceImpl implements BDLCustomerStoreService{
    @Autowired
    BDLCustomerStoreMapper bdlCustomerStoreMapper;
    @Override
    public BDLCustomerStore getCustomerStore(BDLCustomerStore bdlCustomerStore) {
        return bdlCustomerStoreMapper.getCustomerStore(bdlCustomerStore);
    }

    @Override
    public int addCustomerStore(BDLCustomerStore bcs) {
        return bdlCustomerStoreMapper.insert(bcs);
    }

    @Override
    public BDLCustomerStore getCustomerStoreByOpenid(String openid) {
        return bdlCustomerStoreMapper.getCustomerStoreByOpenid(openid);
    }

    @Override
    public void update(BDLCustomerStore bcs) {
        bdlCustomerStoreMapper.updateByPrimaryKeySelective(bcs);
    }
}
