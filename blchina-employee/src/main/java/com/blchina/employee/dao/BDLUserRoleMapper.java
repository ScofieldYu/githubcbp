package com.blchina.employee.dao;

import java.util.List;

import com.blchina.employee.dto.BDLUserRoleDTO;
import com.blchina.employee.model.BDLEmployee;
import com.blchina.employee.model.BDLRole;
import com.blchina.employee.model.BDLUserRole;

public interface BDLUserRoleMapper {
    int deleteByPrimaryKey(Integer userroleid);

    int insert(BDLUserRole record);

    int insertSelective(BDLUserRole record);

    BDLUserRole selectByPrimaryKey(Integer userroleid);

    int updateByPrimaryKeySelective(BDLUserRole record);

    int updateByPrimaryKey(BDLUserRole record);

	BDLUserRole getUserIdByCompanyCodeAndRoleId(BDLUserRoleDTO bdlUserRole);

   BDLRole getRoleKeyByEmployeeId(Integer userid);
}