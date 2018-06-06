package com.blchina.cbp.dao;

import com.blchina.cbp.dto.SignMonitorDTO;
import com.blchina.cbp.model.CBPOrderFile;

public interface CBPOrderFileMapper {
    int deleteByPrimaryKey(Integer orderfileid);

    int insert(CBPOrderFile record);

    int insertSelective(CBPOrderFile record);

    CBPOrderFile selectByPrimaryKey(Integer orderfileid);

    int updateByPrimaryKeySelective(CBPOrderFile record);

    int updateByPrimaryKey(CBPOrderFile record);

    CBPOrderFile getFileByOrderId(Integer orderid);

   Integer getFileCountByBrandIdAndOrderStatus(SignMonitorDTO signMonitorDTO);
}