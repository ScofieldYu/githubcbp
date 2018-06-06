package com.blchina.cbp.dao;

import java.util.List;

import com.blchina.cbp.dto.InsuranceOrderInfoDTO;
import com.blchina.cbp.model.CBPInsuranceOrder;

public interface CBPInsuranceOrderMapper {
    int deleteByPrimaryKey(Integer insuranceorderid);

    int insert(CBPInsuranceOrder record);

    int insertSelective(CBPInsuranceOrder record);

    CBPInsuranceOrder selectByPrimaryKey(Integer insuranceorderid);

    int updateByPrimaryKeySelective(CBPInsuranceOrder record);

    int updateByPrimaryKey(CBPInsuranceOrder record);
    
    List<CBPInsuranceOrder> selectInsuranceOrderListByOrderId(Integer orderId);

    CBPInsuranceOrder getInsuranceOrder(Integer orderid);

    int updateInsuranceByOrderId(CBPInsuranceOrder infoDTO);
}