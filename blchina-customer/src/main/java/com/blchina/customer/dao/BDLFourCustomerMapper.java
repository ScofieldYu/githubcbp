package com.blchina.customer.dao;

import java.util.List;

import com.blchina.customer.dto.CardDTO;
import com.blchina.customer.model.BDLFourCustomer;

public interface BDLFourCustomerMapper {
    int deleteByPrimaryKey(Integer fourcustomerid);

    int insert(BDLFourCustomer record);

    int insertSelective(BDLFourCustomer record);

    BDLFourCustomer selectByPrimaryKey(Integer fourcustomerid);

    int updateByPrimaryKeySelective(BDLFourCustomer record);

    int updateByPrimaryKey(BDLFourCustomer record);

   List<BDLFourCustomer> selectFourListByCustomerid(Integer customerid);

   BDLFourCustomer selectFourListByCustomeridAndType(
		 BDLFourCustomer fourCustomer);

   List<BDLFourCustomer> searchFourCustomer(CardDTO cardDTO);
}