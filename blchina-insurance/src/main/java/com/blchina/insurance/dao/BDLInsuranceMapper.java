package com.blchina.insurance.dao;

import java.util.List;

import com.blchina.insurance.dto.InsuranceDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.blchina.insurance.model.BDLInsurance;
@Service
public interface BDLInsuranceMapper {
    int deleteByPrimaryKey(Integer insuranceid);

    int insert(BDLInsurance record);

    int insertSelective(BDLInsurance record);

    BDLInsurance selectByPrimaryKey(Integer insuranceid);

    int updateByPrimaryKeySelective(BDLInsurance record);

    int updateByPrimaryKey(BDLInsurance record);

   List<BDLInsurance> selectListByInsuranceId(@Param("insuranceid")String insuranceid);

    List<InsuranceDTO> getAllInsuranceByCompany(InsuranceDTO insurance);

    List<BDLInsurance> getAllInsurance();
}