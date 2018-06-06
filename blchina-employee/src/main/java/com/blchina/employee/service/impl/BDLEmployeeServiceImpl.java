package com.blchina.employee.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.blchina.employee.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.common.util.string.StringUtil;
import com.blchina.employee.dao.BDLEmployeeMapper;
import com.blchina.employee.dao.BDLStoreMapper;
import com.blchina.employee.dao.BDLUnEmployeeMapper;
import com.blchina.employee.dto.BDLEmployeeDTO;
import com.blchina.employee.dto.CustomerMessageDTO;
import com.blchina.employee.model.BDLEmployee;
import com.blchina.employee.model.BDLStore;
import com.blchina.employee.service.interfaces.BDLEmployeeService;


@Service("bdlEmployeeService")
public class BDLEmployeeServiceImpl  implements BDLEmployeeService {
   	@Autowired
    private BDLEmployeeMapper bdlEmployeeMapper;
   	@Autowired
    private BDLUnEmployeeMapper bdlUnEmployeeMapper;
   	@Autowired
   	private BDLStoreMapper bdlStoreMapper;
	/**
	 * 根据ID获取员工信息
	 * @param bdlEmployee
	 * @return
	 */
	@Override
	public BDLEmployee getEmployeeById(BDLEmployee bdlEmployee) {
		return bdlEmployeeMapper.selectByPrimaryKey(bdlEmployee.getEmployeeid());
	}

	@Override
	public int insertOrUpdateEmployee(BDLEmployeeDTO.Employee employee) {
		int status = 0;
		bdlUnEmployeeMapper.deleteByPrimaryKey(Integer.parseInt(employee.getPERNR()));
		if(employee != null && employee.getPERNR() != null){
			BDLEmployee selectByPrimaryKey = bdlEmployeeMapper.selectByPrimaryKey(Integer.parseInt(employee.getPERNR()));
			BDLEmployee bdlEmployee = BDLEmployeeServiceImpl.bdlEmployeeDTO2BDLEmployee(employee);
			if(selectByPrimaryKey != null){
				status = bdlEmployeeMapper.updateByPrimaryKeySelective(bdlEmployee);
			}else{
				status = bdlEmployeeMapper.insert(bdlEmployee);
			}
		}
		return status;
	}

	/**
	 * 根据邮箱获取员工
	 * @param s
	 * @return
     */
	@Override
	public BDLEmployee getEmployeeByUserId(String s) {
		return bdlEmployeeMapper.getEmployeeByUserId(s);
	}

	private static BDLEmployee bdlEmployeeDTO2BDLEmployee(BDLEmployeeDTO.Employee employee){
	   BDLEmployee bdlEmployee = new BDLEmployee();
	   bdlEmployee.setEmployeeid(Integer.parseInt(employee.getPERNR()));
	   bdlEmployee.setEmployeename(employee.getPENAM());
	   if(!StringUtil.isNullorEmpty(employee.getORGEH())){
		  bdlEmployee.setDepartmentid(Integer.parseInt(employee.getORGEH()));  
	   }
	   if(!StringUtil.isNullorEmpty(employee.getPLANS())){
		  bdlEmployee.setDutyid(Integer.parseInt(employee.getPLANS()));
	   }
	   if(!StringUtil.isNullorEmpty(employee.getSTELL())){
		  bdlEmployee.setTitleid(Integer.parseInt(employee.getSTELL()));        
	   }
	   bdlEmployee.setCompanycode(employee.getBUKRS());       
	   bdlEmployee.setStatus(employee.getZLZ_FLAG());       
	   bdlEmployee.setUpdomainaccount(employee.getCOMM_ID_LONG());       
	   bdlEmployee.setSex(employee.getGESCH()); 
	   if(!StringUtil.isNullorEmpty(employee.getTEL_PHONE())){
		  bdlEmployee.setPhonenumber(Long.parseLong(employee.getTEL_PHONE()));       
	   }
	   bdlEmployee.setEmail(employee.getE_MAIL());        
	   bdlEmployee.setBirth(employee.getGBDAT());       
	   bdlEmployee.setEnterdate(employee.getBEGDA()); 
	   if(!StringUtil.isNullorEmpty(employee.getZZF1())){
		  bdlEmployee.setUpdutyid(Integer.parseInt(employee.getZZF1()));        
	   }
	   bdlEmployee.setZzf1(employee.getZZF2());
	   bdlEmployee.setZzf2(employee.getZZF3());
	   return bdlEmployee;
	}
	@Override
	   public int receiveHRSData(BDLEmployee bdlEmployee) {
		  int status = 0;
		  if(bdlEmployee!=null&&!StringUtil.isNullorEmpty(bdlEmployee.getEmployeeid()+"")){
			 //先删除未激活中可能有的员工信息
			 int i = bdlUnEmployeeMapper.deleteByPrimaryKey(bdlEmployee.getEmployeeid());
			 BDLEmployee selectByPrimaryKey = bdlEmployeeMapper.selectByPrimaryKey(bdlEmployee.getEmployeeid());
			 if(selectByPrimaryKey!=null){
				status = bdlEmployeeMapper.updateByPrimaryKeySelective(bdlEmployee);
			 }else{
				status = bdlEmployeeMapper.insertSelective(bdlEmployee);
			 }
		  }
		  return status;
	   }
   @Override
   public String getStoreIdByEmployeeId(BDLEmployee employee) {
	  BDLEmployee bdlEmployee = bdlEmployeeMapper.selectByPrimaryKey(employee.getEmployeeid());
	  return bdlEmployee.getCompanycode();
   }

   List<BDLEmployee> lists = new ArrayList<BDLEmployee>();
   @Override
   public List<BDLEmployee> getEmployeeListByEmployeeId(BDLEmployee employee) {
	  List<BDLEmployee> list = bdlEmployeeMapper.getEmployeeListByEmployeeId(employee.getEmployeeid());
	  BDLEmployee selectByPrimaryKey = bdlEmployeeMapper.selectByPrimaryKey(employee.getEmployeeid());
	  lists.add(selectByPrimaryKey);
	  List<BDLEmployee> list1 = new ArrayList<BDLEmployee>();
	  List<BDLEmployee> list2 = getMinEmployeeList(list);
	  list1.addAll(list2);
	  list2.removeAll(list2);
	  return list1;
   }

	@Override
	public BDLEmployee getEmpoyeeByPhonenumber(Long phonenumber) {
		return bdlEmployeeMapper.getEmpoyeeByPhonenumber(phonenumber);
	}

	/**
	 * 判断是否门店人员
	 * @param employeeid
	 * @return
     */
	@Override
	public BDLEmployee getEmployeeBrandFlag(Integer employeeid) {
		return bdlEmployeeMapper.getEmployeeBrandFlag(employeeid);
	}

	@Override
	public BDLEmployee getEmployeeIdByName(String employeename) {
		return bdlEmployeeMapper.getEmployeeIdByName(employeename);
	}

	public List<BDLEmployee> getMinEmployeeList(List<BDLEmployee> list){
	  for(BDLEmployee e:list){
		   List<BDLEmployee> list2 = bdlEmployeeMapper.getEmployeeListByEmployeeId(e.getEmployeeid());
		   if(list2.isEmpty()){
			  lists.add(e);
		   }else{
			  lists.add(e);
			  getMinEmployeeList(list2);
		   }
		}
	  return lists;
   }

   @Override
   public List<BDLEmployee> getEmployeeListByName(BDLEmployee employee) {
	  
	  return bdlEmployeeMapper.getEmployeeListByName(employee.getEmployeename());
   }

   @Override
   public List<BDLEmployee> getEmployeeListOnlyByEmployeeIdList(
		 CustomerMessageDTO employee) {
	  return bdlEmployeeMapper.getEmployeeListByEmployeeIdList(employee.getEmployeeId());
   }

   @Override
   public BDLEmployee getEmployeeByEmployeeId(Integer valueOf) {
	  return bdlEmployeeMapper.selectByPrimaryKey(valueOf);
   }

   @Override
   public List<BDLEmployee> getEmployeeDirectorList(String string) {
	  return bdlEmployeeMapper.getEmployeeDirectorList(Integer.valueOf(string));
   }

   @Override
   public List<BDLEmployee> getEmployeeListByBrandId(BDLEmployee employee) {
	  return bdlEmployeeMapper.getEmployeeListByBrandId(employee.getCompanycode());
   }

   @Override
   public List<BDLEmployee> getEmployeeListOnlyaaaByEmployeeIdList(
		 Integer employeeid) {
	  return bdlEmployeeMapper.getEmployeeListByEmployeeId(employeeid);
   }

	@Override
	public List<BDLEmployee> getEmployUserIdAndEmployeeId(BDLEmployee employee) {
		return bdlEmployeeMapper.getEmployUserIdAndEmployeeId(employee);
	}

	@Override
	public EmployeeDTO getTitleIdAndTitleName(Integer employeeid) {
		return bdlEmployeeMapper.getTitleIdAndTitleName(employeeid);
	}

   @Override
   public BDLEmployee getPhoneNumberByCompanycode(String companycode) {
	  return bdlEmployeeMapper.getPhoneNumberByCompanycode(companycode);
   }

}
