package com.blchina.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.employee.dao.BDLTitleMapper;
import com.blchina.employee.dto.BDLTitleDTO;
import com.blchina.employee.model.BDLTitle;
import com.blchina.employee.service.interfaces.BDLTitleService;


@Service("bdlTitleService")
public class BDLTitleServiceImpl implements BDLTitleService{
   
	@Autowired
	private BDLTitleMapper bdlTitleMapper;
	
	@Override
	public int insertOrUpdateTitle(BDLTitleDTO.Title title) {
		int status = 0;
		if(title != null && title.getSTELL() != null){
			BDLTitle selectByPrimaryKey = bdlTitleMapper.selectByPrimaryKey(Integer.parseInt(title.getSTELL()));
			BDLTitle bdlTitle = BDLTitleServiceImpl.bdlTitleDTO2BDLTitle(title);
			if(selectByPrimaryKey != null){
				status = bdlTitleMapper.updateByPrimaryKey(bdlTitle);
			}else{
				status = bdlTitleMapper.insert(bdlTitle);
			}
		}
		return status;
	}
	private static BDLTitle bdlTitleDTO2BDLTitle(BDLTitleDTO.Title title){
	   BDLTitle bdlTitle = new BDLTitle();
	   bdlTitle.setTitleid(Integer.parseInt(title.getSTELL()));
	   bdlTitle.setObjectname(title.getSTEXT1());
	   bdlTitle.setStartdate(title.getBEGDA());
	   bdlTitle.setEnddate(title.getENDDA());
	   bdlTitle.setZzf1(title.getZZF1());
	   bdlTitle.setZzf2(title.getZZF2());
	   bdlTitle.setZzf3(title.getZZF3());
	   return bdlTitle;
	}
}
