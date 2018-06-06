package com.blchina.insurance.dao;

import java.util.List;

import com.blchina.insurance.model.BDLInsuranceandCompany;

public interface BDLInsuranceandCompanyMapper {
    int deleteByPrimaryKey(Integer insuranceandcomid);

    int insert(BDLInsuranceandCompany record);

    int insertSelective(BDLInsuranceandCompany record);

    BDLInsuranceandCompany selectByPrimaryKey(Integer insuranceandcomid);

    int updateByPrimaryKeySelective(BDLInsuranceandCompany record);

    int updateByPrimaryKey(BDLInsuranceandCompany record);

   List<BDLInsuranceandCompany> selectListByInsuranceId(String substring);
}