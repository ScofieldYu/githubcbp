package com.blchina.insurance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.blchina.insurance.model.BDLInsuranceCompany;
@Service
public interface BDLInsuranceCompanyMapper {
    int deleteByPrimaryKey(Integer insurancecompanyid);

    int insert(BDLInsuranceCompany record);

    int insertSelective(BDLInsuranceCompany record);

    BDLInsuranceCompany selectByPrimaryKey(Integer insurancecompanyid);

    int updateByPrimaryKeySelective(BDLInsuranceCompany record);

    int updateByPrimaryKey(BDLInsuranceCompany record);

   List<BDLInsuranceCompany> selectListByInsuranceId(@Param("substring")String substring);

    List<BDLInsuranceCompany> getInsuranceCompany();
}