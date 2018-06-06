package com.blchina.employee.dao;

import java.util.List;

import com.blchina.employee.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.blchina.employee.model.BDLEmployee;
@Service
public interface BDLEmployeeMapper {
    int deleteByPrimaryKey(Integer employeeid);

    int insert(BDLEmployee record);

    int insertSelective(BDLEmployee record);

    BDLEmployee selectByPrimaryKey(Integer employeeid);

    int updateByPrimaryKeySelective(BDLEmployee record);

    int updateByPrimaryKey(BDLEmployee record);

   List<BDLEmployee> selectEmployeeByDepartmentId(Integer departmentid);

    BDLEmployee getEmployeeByUserId(String email);

   List<BDLEmployee> getEmployeeListByEmployeeId(Integer employeeid);

    BDLEmployee getEmpoyeeByPhonenumber(Long phonenumber);

    BDLEmployee getEmployeeBrandFlag(Integer employeeid);

    BDLEmployee getEmployeeIdByName(String employeename);

   List<BDLEmployee> getEmployeeListByName(String employeename);

   List<BDLEmployee> getEmployeeListByEmployeeIdList(@Param("employeeId")String employeeId);

   List<BDLEmployee> getEmployeeDirectorList(Integer integer);

   List<BDLEmployee> getEmployeeListByBrandId(@Param("companycode")String companycode);

    List<BDLEmployee> getEmployUserIdAndEmployeeId(BDLEmployee employee);

    EmployeeDTO getTitleIdAndTitleName(Integer employeeid);

   BDLEmployee getPhoneNumberByCompanycode(String companycode);
}