/*
 * @(#)MessageService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.model.CBPDeliverMessage;

/**
 *消息操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface MessageService {
    CBPDeliverMessage getMessageStatus(CBPDeliverMessage cbpDeliverMessage);

    void updateMessageByOrderid(Integer orderid);

    int saveDeliverMessage(CBPDeliverMessage cbpDeliverMessage);
    int saveOrUpdateMessage(CBPDeliverMessage cbpDeliverMessage);
}
