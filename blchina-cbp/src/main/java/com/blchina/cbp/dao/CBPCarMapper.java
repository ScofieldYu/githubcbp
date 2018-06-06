package com.blchina.cbp.dao;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPCar;
@Service
public interface CBPCarMapper {
    int deleteByPrimaryKey(Integer carid);

    int insert(CBPCar record);

    int insertSelective(CBPCar record);

    CBPCar selectByPrimaryKey(Integer carid);

    int updateByPrimaryKeySelective(CBPCar record);

    int updateByPrimaryKey(CBPCar record);

   CBPCar selectCarByOrderId(Integer orderid);
}