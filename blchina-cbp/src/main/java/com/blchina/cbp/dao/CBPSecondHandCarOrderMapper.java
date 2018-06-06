package com.blchina.cbp.dao;

import java.util.List;

import com.blchina.cbp.model.CBPSecondHandCarOrder;

public interface CBPSecondHandCarOrderMapper {
    int deleteByPrimaryKey(Integer secondcarid);

    int insert(CBPSecondHandCarOrder record);

    int insertSelective(CBPSecondHandCarOrder record);

    CBPSecondHandCarOrder selectByPrimaryKey(Integer secondcarid);

    int updateByPrimaryKeySelective(CBPSecondHandCarOrder record);

    int updateByPrimaryKey(CBPSecondHandCarOrder record);

   List<CBPSecondHandCarOrder> selectSecondHandCarByOrderId(Integer orderid);

   int updateByOrderId(CBPSecondHandCarOrder cbpSecondHandCarOrder);


}