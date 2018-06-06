/*
 * @(#)CardService.java       2018年2月24日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.BatchDeleteDTO;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPOrder;

/**
 * 卡片创建
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface CardService {
    public String saveCard(BDLCard card);

   public WeixinResult updateCardTaskList(
		 BatchDeleteDTO bdd);

   String newCard(CBPOrder cbpOrder, String employeeid, String type,
		 String type2);
}
