package com.blchina.cbp.dao;

import com.blchina.cbp.dto.CBPReturnCarTimeDTO;
import com.blchina.cbp.dto.ReturnCarTimeDTO;
import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPReturnCarTime;

import java.util.List;

@Service
public interface CBPReturnCarTimeMapper {
    int deleteByPrimaryKey(Integer retcarid);

    int insert(CBPReturnCarTime record);

    int insertSelective(CBPReturnCarTime record);

    CBPReturnCarTime selectByPrimaryKey(Integer retcarid);

    int updateByPrimaryKeySelective(CBPReturnCarTime record);

    int updateByPrimaryKey(CBPReturnCarTime record);

    List<CBPReturnCarTime> getEmployeeReturnTime(CBPReturnCarTime record);

    List<CBPReturnCarTime> getCustomerReturnTime(CBPReturnCarTimeDTO cbpReturnCarTime);

    List<CBPReturnCarTime> getReturnTimeByOrder(Integer orderid);
    List<ReturnCarTimeDTO> getDayReturnTime(CBPReturnCarTime record);
    List<CBPReturnCarTime> gettheReturnTime(CBPReturnCarTime returnCarTime);

    List<CBPReturnCarTime> getEmployeeReturnTimeAll(Integer employeeid);

   CBPReturnCarTime getMaxDateByOrderId(Integer orderid);
}