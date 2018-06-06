package com.blchina.cbp.dao;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPDeposit;
@Service
public interface CBPDepositMapper {
    int deleteByPrimaryKey(Integer depositid);

    int insert(CBPDeposit record);

    int insertSelective(CBPDeposit record);

    CBPDeposit selectByPrimaryKey(Integer depositid);

    int updateByPrimaryKeySelective(CBPDeposit record);

    int updateByPrimaryKey(CBPDeposit record);
    
    CBPDeposit selectByOrderId(Integer parseInt);

    CBPDeposit selectBySapOrderId(String parseInt);

}