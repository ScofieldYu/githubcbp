package com.blchina.employee.dao;

import org.springframework.stereotype.Service;

import com.blchina.employee.model.BDLDuty;
@Service
public interface BDLDutyMapper {
    int deleteByPrimaryKey(Integer dutyid);

    int insert(BDLDuty record);

    int insertSelective(BDLDuty record);

    BDLDuty selectByPrimaryKey(Integer dutyid);

    int updateByPrimaryKeySelective(BDLDuty record);

    int updateByPrimaryKey(BDLDuty record);
}