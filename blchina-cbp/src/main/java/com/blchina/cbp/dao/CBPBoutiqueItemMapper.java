package com.blchina.cbp.dao;

import java.util.List;

import com.blchina.cbp.dto.CBPBoutiqueItemDTO;
import com.blchina.cbp.model.CBPBoutiqueItem;

public interface CBPBoutiqueItemMapper {
    int deleteByPrimaryKey(Integer boutiqueitemid);

    int insert(CBPBoutiqueItem record);

    int insertSelective(CBPBoutiqueItem record);

    CBPBoutiqueItem selectByPrimaryKey(Integer boutiqueitemid);

    int updateByPrimaryKeySelective(CBPBoutiqueItem record);

    int updateByPrimaryKey(CBPBoutiqueItem record);

   List<CBPBoutiqueItem> selectBoutiqueItemList(Integer boutiqueorderid);

   int saveSelective(CBPBoutiqueItemDTO boutiqueItem);
}