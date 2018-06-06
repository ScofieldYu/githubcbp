package com.blchina.cbp.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blchina.cbp.dto.CBPBoutiqueItemDTO;
import com.blchina.cbp.model.CBPBoutiqueOrder;
@Service
public interface CBPBoutiqueOrderMapper {
    int deleteByPrimaryKey(Integer boutiqueorderid);

    int insert(CBPBoutiqueOrder record);

    int insertSelective(CBPBoutiqueOrder record);

    CBPBoutiqueOrder selectByPrimaryKey(Integer boutiqueorderid);

    int updateByPrimaryKeySelective(CBPBoutiqueOrder record);

    int updateByPrimaryKey(CBPBoutiqueOrder record);

   List<CBPBoutiqueOrder> selectBoutiqueByOrderId(Integer orderid);

   int updateCreateTime(CBPBoutiqueOrder cbpBoutiqueOrder);

   int deleteBoutiqueOrderByBoutiqueId(Integer boutiqueorderid);

   int saveSelective(CBPBoutiqueItemDTO boutiqueItem);

}