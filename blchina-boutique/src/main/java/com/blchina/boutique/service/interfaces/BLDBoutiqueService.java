package com.blchina.boutique.service.interfaces;

import java.util.List;

import com.blchina.boutique.model.BDLBoutique;

public interface BLDBoutiqueService {

	int updateBoutiqueStockByBoutiqueName(BDLBoutique boutique);

	BDLBoutique getBoutiqueListBySerialNumber(String serialnumber);

}
