package com.blchina.cbp.dao;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPPay;
@Service
public interface CBPPayMapper {
    int deleteByPrimaryKey(Integer payid);

    int insert(CBPPay record);

    int insertSelective(CBPPay record);

    CBPPay selectByPrimaryKey(Integer payid);

    int updateByPrimaryKeySelective(CBPPay record);

    int updateByPrimaryKey(CBPPay record);

   CBPPay selectPayByOrderId(Integer orderid);
}