package com.blchina.boutique.dao;

import java.util.List;

import com.blchina.boutique.model.BDLBoutique;

public interface BDLBoutiqueMapper {

    int deleteByPrimaryKey(Integer boutiqueid);

    int insert(BDLBoutique record);

    int insertSelective(BDLBoutique record);

    BDLBoutique selectByPrimaryKey(Integer boutiqueid);

    int updateByPrimaryKeySelective(BDLBoutique record);

    int updateByPrimaryKey(BDLBoutique record);

	List<BDLBoutique> getBoutiqueList();

	int updateBoutiqueStockByBoutiqueName(BDLBoutique boutique);

	BDLBoutique getBoutiqueByBoutiqueName(BDLBoutique boutique);

	BDLBoutique getBoutiqueListBySerialNumber(String serialnumber);



}