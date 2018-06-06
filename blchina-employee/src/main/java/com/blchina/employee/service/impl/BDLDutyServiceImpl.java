package com.blchina.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.common.util.string.StringUtil;
import com.blchina.employee.dao.BDLDutyMapper;
import com.blchina.employee.dto.BDLDutyDTO;
import com.blchina.employee.model.BDLDepartment;
import com.blchina.employee.model.BDLDuty;
import com.blchina.employee.service.interfaces.BDLDutyService;


@Service("bdlDutyService")
public class BDLDutyServiceImpl implements BDLDutyService{
   
	@Autowired
	private BDLDutyMapper bdlDutyMapper;

	@Override
	public int insertOrUpdateDuty(BDLDutyDTO.Duty bdlDutyDTO) {
		int status = 0;
		if(bdlDutyDTO != null && bdlDutyDTO.getPLANS() != null){
			BDLDuty selectByPrimaryKey = bdlDutyMapper.selectByPrimaryKey(Integer.parseInt(bdlDutyDTO.getPLANS()));
			BDLDuty bdlDuty = BDLDutyServiceImpl.bdlDutyDTO2BDLDuty(bdlDutyDTO);
			if(selectByPrimaryKey != null){
				status = bdlDutyMapper.updateByPrimaryKey(bdlDuty);
			}else{
				status = bdlDutyMapper.insert(bdlDuty);
			}
		}
		return status;
	}
	private static BDLDuty bdlDutyDTO2BDLDuty(BDLDutyDTO.Duty bdlDutyDTO){
	   BDLDuty bdlDuty = new BDLDuty();
	   bdlDuty.setDutyid(Integer.parseInt(bdlDutyDTO.getPLANS()));
	   bdlDuty.setDutyname(bdlDutyDTO.getSTEXT2());
	   if(!StringUtil.isNullorEmpty(bdlDutyDTO.getORGEH())){
		  bdlDuty.setDepartmentid(Integer.parseInt(bdlDutyDTO.getORGEH()));
	   }
	   bdlDuty.setDepartmentname(bdlDutyDTO.getSTEXT());
	   if(!StringUtil.isNullorEmpty(bdlDutyDTO.getSTELL())){
		  bdlDuty.setTitleid(Integer.parseInt(bdlDutyDTO.getSTELL()));
	   }
	   bdlDuty.setTitlename(bdlDutyDTO.getSTEXT1());
	   bdlDuty.setStartdate(bdlDutyDTO.getBEGDA());
	   bdlDuty.setEnddate(bdlDutyDTO.getENDDA());
	   bdlDuty.setZzf1(bdlDutyDTO.getZZF1());
	   bdlDuty.setZzf2(bdlDutyDTO.getZZF2());
	   bdlDuty.setZzf3(bdlDutyDTO.getZZF3());
	   return bdlDuty;
	}

}
