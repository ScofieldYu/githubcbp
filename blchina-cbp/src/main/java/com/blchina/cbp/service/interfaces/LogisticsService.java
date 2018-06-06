package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.model.CBPLogistics;

/**
 * Created by admin on 2017/11/22.
 */
public interface LogisticsService {

    int insertOrUpdateLogistic(CBPLogistics record);

    CBPLogistics findLogisticsID(CBPLogistics record);

    List<CBPLogistics> findCBPLogistics(Integer orderid);

    void save(CBPLogistics logistics);

    void deleteLogisticInfoByOrder(Integer orderid);

   CBPLogistics getMaxStatusByOrderId(Integer orderid);
}
