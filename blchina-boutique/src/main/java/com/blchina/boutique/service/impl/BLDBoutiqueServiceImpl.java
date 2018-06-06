package com.blchina.boutique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.boutique.dao.BDLBoutiqueMapper;
import com.blchina.boutique.model.BDLBoutique;
import com.blchina.boutique.service.interfaces.BLDBoutiqueService;
@Service("/BLDBoutiqueService")
public class BLDBoutiqueServiceImpl implements BLDBoutiqueService {
	@Autowired
	private BDLBoutiqueMapper boutiqueMapper;


	@Override
	public int updateBoutiqueStockByBoutiqueName(BDLBoutique boutique) {
		BDLBoutique boutique2 = boutiqueMapper.getBoutiqueByBoutiqueName(boutique);
		boutique.setStock((String.valueOf(Integer.valueOf(boutique2.getStock()).intValue()-1)));
		return boutiqueMapper.updateBoutiqueStockByBoutiqueName(boutique);
	}


	@Override
	public BDLBoutique getBoutiqueListBySerialNumber(String serialnumber) {
		// TODO Auto-generated method stub
		return boutiqueMapper.getBoutiqueListBySerialNumber(serialnumber);
	}

}
