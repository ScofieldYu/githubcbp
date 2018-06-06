package com.blchina.card.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.blchina.card.dto.CardDTO;
import com.blchina.card.model.BDLCard;

public interface BDLCardMapper {
    int deleteByPrimaryKey(Integer cardid);

    int insert(BDLCard record);

    int insertSelective(BDLCard record);

    BDLCard selectByPrimaryKey(Integer cardid);

    int updateByPrimaryKeySelective(BDLCard record);

    int updateByPrimaryKey(BDLCard record);

    List<BDLCard> selectCardListByCustomerName(CardDTO cardDTO);
    List<BDLCard> queryCardByEmploy(BDLCard card);

   List<BDLCard> selectCardListByCustomerNameAll(CardDTO cardDTO);

   BDLCard selectByCardIdAndEmployeeId(@Param("cardid")Integer cardid, @Param("employeeid")Integer employeeid);

   BDLCard getCardByOrderIdAndCardType(BDLCard bdlCard);

   List<BDLCard> selectCardByOrderIdAndEmployeeId(BDLCard bdlCard);

   List<BDLCard> selectCardByOrderId(Integer orderid);
}