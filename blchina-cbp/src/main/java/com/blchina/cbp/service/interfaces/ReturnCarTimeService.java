/*
 * @(#)ReturnCarTimeService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.dto.ReturnCarTimeDTO;
import com.blchina.cbp.model.CBPReturnCarTime;

import java.util.List;

/**
 *订车时间设置
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface ReturnCarTimeService {
    List<CBPReturnCarTime> getEmployeeReturnTime(Integer employeeid, String today);
    List<CBPReturnCarTime> getCustomerReturnTime(Integer employeeid, String today,String endDay);
    void  addOrUpdateReturnTime(List<CBPReturnCarTime> returnCarTimeList);

    String setCustomerTime(CBPReturnCarTime returnTime);
    List<ReturnCarTimeDTO> getDayReturnTime(CBPReturnCarTime returnTime);

    List<CBPReturnCarTime> getReturnTimeByOrder(Integer orderid);

    List<CBPReturnCarTime> getEmployeeReturnTimeAll(Integer employeeid);
   CBPReturnCarTime getMaxDateByOrderId(Integer orderid);
}
