package com.blchina.cbp.dao;

import com.blchina.cbp.model.CBPInvoice;

public interface CBPInvoiceMapper {
    int deleteByPrimaryKey(Integer invoiceid);

    int insert(CBPInvoice record);

    int insertSelective(CBPInvoice record);

    CBPInvoice selectByPrimaryKey(Integer invoiceid);

    int updateByPrimaryKeySelective(CBPInvoice record);

    int updateByPrimaryKey(CBPInvoice record);

	CBPInvoice getInvoiceByOrderId(Integer orderid);

	void updateByOrderId(CBPInvoice invoice);

}