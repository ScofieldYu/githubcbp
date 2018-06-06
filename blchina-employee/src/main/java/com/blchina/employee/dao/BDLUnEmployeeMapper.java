package com.blchina.employee.dao;

import java.util.List;

import com.blchina.employee.model.BDLEmployee;
import org.springframework.stereotype.Service;

import com.blchina.employee.model.BDLUnEmployee;
@Service
public interface BDLUnEmployeeMapper {
    int deleteByPrimaryKey(Integer unemployeeid);

    int insert(BDLUnEmployee record);

    int insertSelective(BDLUnEmployee record);

    BDLUnEmployee selectByPrimaryKey(Integer unemployeeid);

    int updateByPrimaryKeySelective(BDLUnEmployee record);

    int updateByPrimaryKey(BDLUnEmployee record);

   List<BDLUnEmployee> selectUnEmployeeByDepartmentId(Integer departmentid);

    BDLEmployee getEmployeeByUserId(String email);
}