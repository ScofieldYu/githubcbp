package com.blchina.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.blchina.employee.dao.BDLDepartmentMapper;
import com.blchina.employee.dao.BDLEmployeeMapper;
import com.blchina.employee.dao.BDLUnEmployeeMapper;
import com.blchina.employee.dto.BDLDepartmentDTO;
import com.blchina.employee.dto.DepartmentInfoDTO;
import com.blchina.employee.dto.FDDDepartmentDTO;
import com.blchina.employee.model.BDLDepartment;
import com.blchina.employee.model.BDLEmployee;
import com.blchina.employee.model.BDLUnEmployee;
import com.blchina.employee.service.interfaces.BDLDepartmentService;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import antlr.StringUtils;
import net.sf.json.JSONObject;


@Service("bdlDepartmentService")
public class BDLDepartmentServiceImpl implements BDLDepartmentService {

   @Autowired
   private BDLDepartmentMapper bdlDepartmentMapper;
   @Autowired
   private BDLEmployeeMapper bdlEmployeeMapper;
   @Autowired
   private BDLUnEmployeeMapper bdlUnEmployeeMapper;
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   @Autowired
   protected Properties systemConfig;

   @Override
   public int insertOrUpdateDepartment(BDLDepartmentDTO.Department bdlDepartmentDTO) {
	  int status = 0;
	  BDLDepartment selectByPrimaryKey = null;
	  BDLDepartment selectDepartmentByNameAndUpId = null;
	  //DTO转换为BDLDepartment
		 BDLDepartment bdlDepartment = BDLDepartmentServiceImpl
			   .bdlDepartmentDTO2BDLDepartment(bdlDepartmentDTO);
	  if(!StringUtil.isNullorEmpty(bdlDepartmentDTO.getORGEH())){
		  selectByPrimaryKey = bdlDepartmentMapper.selectByPrimaryKey(Integer.parseInt(bdlDepartmentDTO.getORGEH()));
	  }
	  if(selectByPrimaryKey!=null){
		 status = bdlDepartmentMapper.updateByPrimaryKeySelective(bdlDepartment);
		 return status;
	  }
	  if (bdlDepartmentDTO != null && bdlDepartmentDTO.getSTEXT() != null) {
		 //根据部门名称和上级部门ID查询
		 String departmentName = bdlDepartmentDTO.getSTEXT();//部门名称
		 Map<String, Object> map = new HashMap<String, Object>();
		 
		 if(!StringUtil.isNullorEmpty(bdlDepartmentDTO.getUP_ORGEH())){
			Integer upDepartmentId = Integer
				  .parseInt(bdlDepartmentDTO.getUP_ORGEH());//上级部门
		 map.put("upDepartmentId", upDepartmentId);
		 map.put("departmentName", departmentName);
		 selectDepartmentByNameAndUpId = bdlDepartmentMapper
			   .selectByDepartmentNameAndUpDepartmentId(map);
		 }else{
			status = bdlDepartmentMapper.insertSelective(bdlDepartment);
			return status;
		 }
		 //查询旧部门
		 
		 if (selectDepartmentByNameAndUpId != null) {
			System.out.println("+++++++++++++"+bdlDepartment.getDepartmentid());
			//添加新部门
			status = bdlDepartmentMapper.insertSelective(bdlDepartment);
			//更改员工表( 激活 )部门ID
			//循环更改员工部门ID
			List<BDLEmployee> employeeList = bdlEmployeeMapper
				  .selectEmployeeByDepartmentId(
						selectDepartmentByNameAndUpId.getDepartmentid());
			for (BDLEmployee bdlEmployee : employeeList) {
			   bdlEmployee.setDepartmentid(bdlDepartment.getDepartmentid());
			   bdlEmployeeMapper.updateByPrimaryKeySelective(bdlEmployee);
			}
			//更改员工表( 未激活 )部门ID
			List<BDLUnEmployee> unEmployeeList = bdlUnEmployeeMapper
				  .selectUnEmployeeByDepartmentId(
						selectDepartmentByNameAndUpId.getDepartmentid());
			for (BDLUnEmployee bdlUnEmployee : unEmployeeList) {
			   bdlUnEmployee.setDepartmentid(bdlDepartment.getDepartmentid());
			   bdlUnEmployeeMapper.updateByPrimaryKeySelective(bdlUnEmployee);
			}
			//删除旧的部门
			bdlDepartmentMapper
				  .deleteByPrimaryKey(selectDepartmentByNameAndUpId.getDepartmentid());
		 }
		 else {
			status = bdlDepartmentMapper.insertSelective(bdlDepartment);
		 }
	  }
	  return status;
   }

   private static BDLDepartment bdlDepartmentDTO2BDLDepartment(
		 BDLDepartmentDTO.Department bdlDepartmentDTO) {
	  BDLDepartment bdlDepartment = new BDLDepartment();
	  bdlDepartment
			.setDepartmentid(Integer.parseInt(bdlDepartmentDTO.getORGEH()));
	  bdlDepartment.setDepartmentname(bdlDepartmentDTO.getSTEXT());
	  if(!StringUtil.isNullorEmpty(bdlDepartmentDTO.getUP_ORGEH())){
		 bdlDepartment.setUpdepartmentid(
			   Integer.parseInt(bdlDepartmentDTO.getUP_ORGEH()));
	  }
	  bdlDepartment.setDepartleader(bdlDepartmentDTO.getLEADER());
	  if(!StringUtil.isNullorEmpty(bdlDepartmentDTO.getPOSPLAN())){
		 bdlDepartment.setDepartleaderdutyid(
			   Integer.parseInt(bdlDepartmentDTO.getPOSPLAN()));
	  }
	  bdlDepartment.setStartdate(bdlDepartmentDTO.getBEGDA());
	  bdlDepartment.setEnddate(bdlDepartmentDTO.getENDDA());
	  bdlDepartment.setZzf1(bdlDepartmentDTO.getZZF1());
	  bdlDepartment.setZzf2(bdlDepartmentDTO.getZZF2());
	  bdlDepartment.setZzf3(bdlDepartmentDTO.getZZF3());
	  return bdlDepartment;
   }

   
   
}
