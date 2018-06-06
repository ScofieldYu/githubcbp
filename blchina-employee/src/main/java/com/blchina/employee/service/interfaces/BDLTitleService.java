package com.blchina.employee.service.interfaces;

import com.blchina.employee.dto.BDLTitleDTO;

public interface BDLTitleService {
	   	//职务
		public int insertOrUpdateTitle(BDLTitleDTO.Title title);
}
