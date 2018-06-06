package com.blchina.employee.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.blchina.employee.model.BDLDepartment;
@Service
public interface BDLDepartmentMapper {
    int deleteByPrimaryKey(Integer departmentid);

    int insert(BDLDepartment record);

    int insertSelective(BDLDepartment record);

    BDLDepartment selectByPrimaryKey(Integer departmentid);

    int updateByPrimaryKeySelective(BDLDepartment record);

    int updateByPrimaryKey(BDLDepartment record);

   BDLDepartment selectByDepartmentNameAndUpDepartmentId(Map<String,Object> map);
}