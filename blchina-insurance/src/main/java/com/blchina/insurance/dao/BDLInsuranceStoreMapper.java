package com.blchina.insurance.dao;

import org.springframework.stereotype.Service;

import com.blchina.insurance.model.BDLInsuranceStore;
@Service
public interface BDLInsuranceStoreMapper {
    int deleteByPrimaryKey(Integer insurancestoreid);

    int insert(BDLInsuranceStore record);

    int insertSelective(BDLInsuranceStore record);

    BDLInsuranceStore selectByPrimaryKey(Integer insurancestoreid);

    int updateByPrimaryKeySelective(BDLInsuranceStore record);

    int updateByPrimaryKey(BDLInsuranceStore record);
}