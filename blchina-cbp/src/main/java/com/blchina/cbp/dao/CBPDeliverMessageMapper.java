package com.blchina.cbp.dao;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPDeliverMessage;
@Service
public interface CBPDeliverMessageMapper {
    int deleteByPrimaryKey(Integer messageid);

    int insert(CBPDeliverMessage record);

    int insertSelective(CBPDeliverMessage record);

    CBPDeliverMessage selectByPrimaryKey(Integer messageid);

    int updateByPrimaryKeySelective(CBPDeliverMessage record);

    int updateByPrimaryKey(CBPDeliverMessage record);

    CBPDeliverMessage getMessageByOrderAndType(CBPDeliverMessage cbpDeliverMessage);

    void updateSetByOrderAndType(CBPDeliverMessage message);

    CBPDeliverMessage getMessageStatus(CBPDeliverMessage cbpDeliverMessage);
    CBPDeliverMessage getMessageByOrderId(Integer orderid);
}