package com.blchina.cbp.dao;

import com.blchina.cbp.model.CBPInsuranceExt;

import java.util.List;

public interface CBPInsuranceExtMapper {
    int deleteByPrimaryKey(Integer insuranceextid);

    int insert(CBPInsuranceExt record);

    int insertSelective(CBPInsuranceExt record);

    CBPInsuranceExt selectByPrimaryKey(Integer insuranceextid);

    int updateByPrimaryKeySelective(CBPInsuranceExt record);

    int updateByPrimaryKey(CBPInsuranceExt record);

    List<CBPInsuranceExt> getOrderExtInsurance(Integer orderid);

    int deleteOrderExtInsurance(Integer orderid);
}