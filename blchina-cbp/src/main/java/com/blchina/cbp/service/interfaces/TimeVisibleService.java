/*
 * @(#)TimeVisibleService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.model.CBPTimeVisible;

import java.util.List;

/**
 *针对客户不可见时间设置
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface TimeVisibleService {
    List<CBPTimeVisible> getVisibleByOrderid(Integer orderid);
    void  addVisibleList(List<CBPTimeVisible> visibleList,Integer orderid);
}
