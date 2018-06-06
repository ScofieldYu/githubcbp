/*
 * @(#)MessageServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPDeliverMessageMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.model.CBPDeliverMessage;
import com.blchina.cbp.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *消息操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("MessageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    CBPDeliverMessageMapper cbpDeliverMessageMapper;
    @Override
    public CBPDeliverMessage getMessageStatus(CBPDeliverMessage cbpDeliverMessage) {
        return cbpDeliverMessageMapper.getMessageStatus(cbpDeliverMessage);
    }

    @Override
    public void updateMessageByOrderid(Integer orderid) {
        CBPDeliverMessage message=new CBPDeliverMessage();
        message.setMessagetype(CBPConstant.MessageTypeEnum.SALE_FIVEEXTERNAL.getType());
        message.setStatus(CBPConstant.MessageStatusEnum.SET.getType());
        message.setOrderid(orderid);
        cbpDeliverMessageMapper.updateSetByOrderAndType(message);

    }

    @Override
    public int saveDeliverMessage(CBPDeliverMessage cbpDeliverMessage) {
        return cbpDeliverMessageMapper.insert(cbpDeliverMessage);
    }

    @Override
    public int saveOrUpdateMessage(CBPDeliverMessage cbpDeliverMessage) {
        CBPDeliverMessage messageByOrderId = cbpDeliverMessageMapper.getMessageByOrderId(cbpDeliverMessage.getOrderid());
        if(messageByOrderId==null){
            cbpDeliverMessageMapper.insert(cbpDeliverMessage);
        }else{
            cbpDeliverMessage.setMessageid(messageByOrderId.getMessageid());
            cbpDeliverMessageMapper.updateByPrimaryKeySelective(cbpDeliverMessage);
        }
        return 0;
    }
}
