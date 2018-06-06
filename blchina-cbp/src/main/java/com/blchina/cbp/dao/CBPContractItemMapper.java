package com.blchina.cbp.dao;

import com.blchina.cbp.model.CBPContractItem;

public interface CBPContractItemMapper {
    int deleteByPrimaryKey(Integer itemid);

    int insert(CBPContractItem record);

    int insertSelective(CBPContractItem record);

    CBPContractItem selectByPrimaryKey(Integer itemid);

    int updateByPrimaryKeySelective(CBPContractItem record);

    int updateByPrimaryKey(CBPContractItem record);

   CBPContractItem selectByContractId(int parseInt);

   CBPContractItem selectContractItemByContractIdAndContractStatus(
		 Integer contractId);
}