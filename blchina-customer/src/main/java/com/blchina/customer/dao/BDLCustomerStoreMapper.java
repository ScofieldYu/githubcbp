package com.blchina.customer.dao;

import org.springframework.stereotype.Service;

import com.blchina.customer.model.BDLCustomerStore;

@Service
public interface BDLCustomerStoreMapper {
    int deleteByPrimaryKey(Integer customerstoreid);

    int insert(BDLCustomerStore record);

    int insertSelective(BDLCustomerStore record);

    BDLCustomerStore selectByPrimaryKey(Integer customerstoreid);

    int updateByPrimaryKeySelective(BDLCustomerStore record);

    int updateByPrimaryKey(BDLCustomerStore record);

    BDLCustomerStore getCustomerStore(BDLCustomerStore bdlCustomerStore);

    BDLCustomerStore getCustomerStoreByOpenid(String openid);
}