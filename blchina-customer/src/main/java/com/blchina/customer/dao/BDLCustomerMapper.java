package com.blchina.customer.dao;

import com.blchina.customer.dto.OpenIdDTO;
import org.springframework.stereotype.Service;

import com.blchina.customer.model.BDLCustomer;
import java.util.List;

@Service
public interface BDLCustomerMapper {
    int deleteByPrimaryKey(Integer customerid);

    int insert(BDLCustomer record);

    int insertSelective(BDLCustomer record);

    BDLCustomer selectByPrimaryKey(Integer customerid);

    int updateByPrimaryKeySelective(BDLCustomer record);

    int updateByPrimaryKey(BDLCustomer record);
    
    List<BDLCustomer> findCustmerByPhone(Long customer);

    int updateCustmerByPhone(BDLCustomer customer);

    List<BDLCustomer> getCustmerByCard(String idcardnum);

    int updateCustmerByCard(BDLCustomer customer);

    List<BDLCustomer> getCustmerByPhoneBase(Long phonenumber);

    List<OpenIdDTO> findOpenId();
}