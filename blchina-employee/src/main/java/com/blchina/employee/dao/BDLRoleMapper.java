package com.blchina.employee.dao;

import com.blchina.employee.model.BDLRole;

public interface BDLRoleMapper {
    int deleteByPrimaryKey(Integer roleid);

    int insert(BDLRole record);

    int insertSelective(BDLRole record);

    BDLRole selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(BDLRole record);

    int updateByPrimaryKey(BDLRole record);

	BDLRole selectByRoleKey(String rolekey);
}