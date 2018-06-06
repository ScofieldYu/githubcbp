package com.blchina.finance.dao;

import com.blchina.finance.model.BDLDownpayment;

public interface BDLDownpaymentMapper {
    int deleteByPrimaryKey(Integer downpaymentid);

    int insert(BDLDownpayment record);

    int insertSelective(BDLDownpayment record);

    BDLDownpayment selectByPrimaryKey(Integer downpaymentid);

    int updateByPrimaryKeySelective(BDLDownpayment record);

    int updateByPrimaryKey(BDLDownpayment record);
}