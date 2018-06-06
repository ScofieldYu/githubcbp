package com.blchina.employee.dao;

import org.springframework.stereotype.Service;
import com.blchina.employee.model.BDLStore;

import java.util.List;

@Service
public interface BDLStoreMapper {
    int deleteByPrimaryKey(Integer storeid);

    int insert(BDLStore record);

    int insertSelective(BDLStore record);

    BDLStore selectByPrimaryKey(Integer storeid);

    int updateByPrimaryKeySelective(BDLStore record);

    int updateByPrimaryKey(BDLStore record);

   BDLStore selectByBrandId(String companycode);

    BDLStore getSignStoreIdByBrandId(String brandid);

    List<BDLStore> getAllStore();
}