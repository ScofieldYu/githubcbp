package com.blchina.cbp.dao;

import java.util.List;

import com.blchina.cbp.model.CBPFinanceOrder;

public interface CBPFinanceOrderMapper {
    int deleteByPrimaryKey(Integer financeorderid);

    int insert(CBPFinanceOrder record);

    int insertSelective(CBPFinanceOrder record);

    CBPFinanceOrder selectByPrimaryKey(Integer financeorderid);

    int updateByPrimaryKeySelective(CBPFinanceOrder record);

    int updateByPrimaryKey(CBPFinanceOrder record);

	CBPFinanceOrder selectFinanceOrderByOrderId(Integer orderid);

	int updateReserveTimeByOrderid(CBPFinanceOrder cbpFinanceOrder);
	List<CBPFinanceOrder> getFinanceByOrderId(Integer orderid);
	
    List<CBPFinanceOrder> selectFianceByOrderId(Integer orderid);
}