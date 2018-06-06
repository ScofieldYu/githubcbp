package com.blchina.employee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import com.blchina.employee.dto.BDLStoreDTO;
import com.blchina.employee.model.BDLBrandStore;
import com.blchina.employee.model.BrandStore;
@Service
public interface BDLBrandStoreMapper {
	
	List<BDLStoreDTO> getAllStore();

	List<BDLBrandStore> getAllBrandStore(BDLBrandStore employeeId);

	Integer getTitleIdByEmployeeId(BDLBrandStore employeeId);

	List<BrandStore> getBrandStore(String brandType);

}
