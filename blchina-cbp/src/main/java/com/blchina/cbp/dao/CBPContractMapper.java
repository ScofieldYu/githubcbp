package com.blchina.cbp.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPContract;
@Service
public interface CBPContractMapper {
    int deleteByPrimaryKey(Integer contractid);

    int insert(CBPContract record);

    int insertSelective(CBPContract record);

    CBPContract selectByPrimaryKey(Integer contractid);

    int updateByPrimaryKeySelective(CBPContract record);

    int updateByPrimaryKey(CBPContract record);

    int updateConstractByOrderId(CBPContract contract);

    int updateConstractByVinNo(String vinno);

    CBPContract findConstract(CBPContract constract);

    CBPContract getMaxConstract(Integer orderid);

   CBPContract selectByOrderId(Integer parseInt);

    void updateisChangeByOrderId(Integer orderid);

   Integer cancelContract(Integer orderid);

   List<CBPContract> selectCBPContractListByOrderId(Integer orderid);
}