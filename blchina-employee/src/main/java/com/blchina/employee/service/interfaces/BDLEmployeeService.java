
/*
 * @(#)CBPCustomerService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.employee.service.interfaces;

import java.util.List;

import com.blchina.employee.dto.BDLEmployeeDTO;
import com.blchina.employee.dto.CustomerMessageDTO;
import com.blchina.employee.dto.EmployeeDTO;
import com.blchina.employee.model.BDLEmployee;

/**
 *客户操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface BDLEmployeeService {
	/**
	 * 根据ID获取员工信息
	 * @param bdlEmployee
	 * @return
     */
	public BDLEmployee getEmployeeById(BDLEmployee bdlEmployee);

	public int insertOrUpdateEmployee(BDLEmployeeDTO.Employee employee);

	BDLEmployee getEmployeeByUserId(String s);

   public String getStoreIdByEmployeeId(BDLEmployee employee);

   public int receiveHRSData(BDLEmployee bdlEmployee);

   public List<BDLEmployee> getEmployeeListByEmployeeId(BDLEmployee employee);

	BDLEmployee getEmpoyeeByPhonenumber(Long phonenumber);

	BDLEmployee getEmployeeBrandFlag(Integer employeeid);

	BDLEmployee getEmployeeIdByName(String employeename);

   public List<BDLEmployee> getEmployeeListByName(BDLEmployee employee);

   public List<BDLEmployee> getEmployeeListOnlyByEmployeeIdList(
		 CustomerMessageDTO employee);

   public BDLEmployee getEmployeeByEmployeeId(Integer valueOf);

   public List<BDLEmployee> getEmployeeDirectorList(String string);

   public List<BDLEmployee> getEmployeeListByBrandId(BDLEmployee employee);

   public List<BDLEmployee> getEmployeeListOnlyaaaByEmployeeIdList(
		 Integer employeeid);

	List<BDLEmployee> getEmployUserIdAndEmployeeId(BDLEmployee employee);

	EmployeeDTO getTitleIdAndTitleName(Integer employeeid);

   public BDLEmployee getPhoneNumberByCompanycode(String companycode);
}
