package com.blchina.cbp.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPLogistics;
@Service
public interface CBPLogisticsMapper {
    int deleteByPrimaryKey(Integer logisticsid);

    int insert(CBPLogistics record);

    int insertSelective(CBPLogistics record);

    CBPLogistics selectByPrimaryKey(Integer logisticsid);

    int updateByPrimaryKeySelective(CBPLogistics record);

    int updateByPrimaryKey(CBPLogistics record);

    CBPLogistics selectLogisticsID(CBPLogistics record);

    List<CBPLogistics> selectCBPLogistics(Integer orderid);

    void deleteLogisticInfoByOrder(Integer orderid);

   CBPLogistics getMaxStatusByOrderId(Integer orderid);
}