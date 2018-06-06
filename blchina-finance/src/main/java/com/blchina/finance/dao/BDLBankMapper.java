package com.blchina.finance.dao;

import java.util.List;

import com.blchina.finance.model.BDLBank;

public interface BDLBankMapper {
    int deleteByPrimaryKey(Integer bankid);

    int insert(BDLBank record);

    int insertSelective(BDLBank record);

    BDLBank selectByPrimaryKey(Integer bankid);

    int updateByPrimaryKeySelective(BDLBank record);

    int updateByPrimaryKey(BDLBank record);

	List<BDLBank> selectAllBank();
}