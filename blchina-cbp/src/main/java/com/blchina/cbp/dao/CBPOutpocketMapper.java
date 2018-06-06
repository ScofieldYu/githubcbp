package com.blchina.cbp.dao;

import java.util.List;

import com.blchina.cbp.dto.SignMonitorDTO;
import com.blchina.cbp.model.CBPOutpocket;

public interface CBPOutpocketMapper {
    int deleteByPrimaryKey(Integer outpocketid);

    int insert(CBPOutpocket record);

    int insertSelective(CBPOutpocket record);

    CBPOutpocket selectByPrimaryKey(Integer outpocketid);

    int updateByPrimaryKeySelective(CBPOutpocket record);

    int updateByPrimaryKey(CBPOutpocket record);

   List<CBPOutpocket> selectByOrderId(Integer parseInt);

   Integer getOutpocketCountByBrandIdAndOrderStatus(SignMonitorDTO smdto);
}