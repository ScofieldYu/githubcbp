package com.blchina.insurance.dao;

import com.blchina.insurance.dto.InsuranceDTO;
import com.blchina.insurance.model.BDLInsuranceLimit;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BDLInsuranceLimitMapper {
    int deleteByPrimaryKey(Integer insurancelimitid);

    int insert(BDLInsuranceLimit record);

    int insertSelective(BDLInsuranceLimit record);

    BDLInsuranceLimit selectByPrimaryKey(Integer insurancelimitid);

    int updateByPrimaryKeySelective(BDLInsuranceLimit record);

    int updateByPrimaryKey(BDLInsuranceLimit record);

    List<BDLInsuranceLimit> getInsuranceLimitList(InsuranceDTO insuranceDTO);
    List<BDLInsuranceLimit> getAllLimit();
}