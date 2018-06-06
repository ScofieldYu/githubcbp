package com.blchina.insurance.dao;

import org.springframework.stereotype.Service;

import com.blchina.insurance.model.BDLInsuranceComStore;
@Service
public interface BDLInsuranceComStoreMapper {
    int deleteByPrimaryKey(Integer insurancecomstoreid);

    int insert(BDLInsuranceComStore record);

    int insertSelective(BDLInsuranceComStore record);

    BDLInsuranceComStore selectByPrimaryKey(Integer insurancecomstoreid);

    int updateByPrimaryKeySelective(BDLInsuranceComStore record);

    int updateByPrimaryKey(BDLInsuranceComStore record);
}