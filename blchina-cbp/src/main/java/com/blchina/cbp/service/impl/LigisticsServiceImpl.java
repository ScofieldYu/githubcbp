package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPLogisticsMapper;
import com.blchina.cbp.model.CBPLogistics;
import com.blchina.cbp.service.interfaces.LogisticsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/11/22.
 */

@Service("LogisticsService")
public class LigisticsServiceImpl implements LogisticsService{

    @Autowired
    CBPLogisticsMapper cbpLogisticsMapper;

    @Override
    public int insertOrUpdateLogistic(CBPLogistics record) {
       CBPLogistics logistics = cbpLogisticsMapper.selectLogisticsID(record);
       if(logistics==null){//已经有的物流信息不保存
    	  cbpLogisticsMapper.insert(record);
       }else {
           record.setLogisticsid(logistics.getLogisticsid());
           cbpLogisticsMapper.updateByPrimaryKeySelective(record);
       }
        return 1;

    }

    @Override
    public CBPLogistics findLogisticsID(CBPLogistics record) {
        return cbpLogisticsMapper.selectLogisticsID(record);
    }

    @Override
    public List<CBPLogistics> findCBPLogistics(Integer orderid) {
        return cbpLogisticsMapper.selectCBPLogistics(orderid);
    }

    @Override
    public void save(CBPLogistics logistics) {
        cbpLogisticsMapper.insert(logistics);
    }

    @Override
    public void deleteLogisticInfoByOrder(Integer orderid) {
        cbpLogisticsMapper.deleteLogisticInfoByOrder(orderid);
    }

   @Override
   public CBPLogistics getMaxStatusByOrderId(Integer orderid) {
	  return cbpLogisticsMapper.getMaxStatusByOrderId(orderid);
   }

}
