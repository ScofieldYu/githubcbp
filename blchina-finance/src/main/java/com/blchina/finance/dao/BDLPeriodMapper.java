package com.blchina.finance.dao;

import java.util.List;

import com.blchina.finance.model.BDLBank;
import com.blchina.finance.model.BDLPeriod;

public interface BDLPeriodMapper {
    int deleteByPrimaryKey(Integer periodid);

    int insert(BDLPeriod record);

    int insertSelective(BDLPeriod record);

    BDLPeriod selectByPrimaryKey(Integer periodid);

    int updateByPrimaryKeySelective(BDLPeriod record);

    int updateByPrimaryKey(BDLPeriod record);

	List<BDLBank> getAllPeriodList();

	List<BDLPeriod> getPeriodListByBankId(Integer bankid);

	List<BDLBank> selectAllBank();

}