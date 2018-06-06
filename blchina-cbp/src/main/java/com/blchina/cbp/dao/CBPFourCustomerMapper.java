package com.blchina.cbp.dao;

import java.util.List;

import com.blchina.cbp.dto.CardDTO;
import com.blchina.cbp.model.CBPFourCustomer;

public interface CBPFourCustomerMapper {
    int deleteByPrimaryKey(Integer fourcustomerid);

    int insert(CBPFourCustomer record);

    int insertSelective(CBPFourCustomer record);

    CBPFourCustomer selectByPrimaryKey(Integer fourcustomerid);

    int updateByPrimaryKeySelective(CBPFourCustomer record);

    int updateByPrimaryKey(CBPFourCustomer record);

   List<CBPFourCustomer> selectFourCustomerListByOrderId(Integer orderid);

   CBPFourCustomer selectByOrderIdAndType(CBPFourCustomer fourCustomer);

   List<CBPFourCustomer> searchFourCustomer(CardDTO cd);
}