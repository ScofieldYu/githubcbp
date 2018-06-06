/*   
 * @(#)CardServiceImpl.java       2018年2月5日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.card.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.card.model.BDLCard;
import com.blchina.card.dao.BDLCardMapper;
import com.blchina.card.dto.CardDTO;
import com.blchina.card.service.interfaces.CardService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/CardService")
public class CardServiceImpl implements CardService {
   
   @Autowired
   private BDLCardMapper bdlCardMapper;
   @Override
   public List<BDLCard> selectCardListByCustomerName(CardDTO cardDTO) {
	  if("0".equals(cardDTO.getCardStatus())){
		 return bdlCardMapper.selectCardListByCustomerNameAll(cardDTO);
	  }
	  return bdlCardMapper.selectCardListByCustomerName(cardDTO);
   }
   @Override
   public BDLCard selectCardByCardId(Integer cardid) {
	  return bdlCardMapper.selectByPrimaryKey(cardid);
   }


    @Override
    public int saveCard(BDLCard card) {
        return bdlCardMapper.insert(card);
    }


    @Override
    public List<BDLCard> queryCardByEmployee(BDLCard card) {
        return bdlCardMapper.queryCardByEmploy(card);
    }

    @Override
    public void updateCardStatus(BDLCard card) {
        bdlCardMapper.updateByPrimaryKeySelective(card);
    }
   @Override
   public int updateCardById(BDLCard bdlCard) {
	   BDLCard card = bdlCardMapper.selectByCardIdAndEmployeeId(bdlCard.getCardid(),bdlCard.getEmployeeid());
	   if(card!=null){
		  card.setEnddate(bdlCard.getEnddate());
		  return bdlCardMapper.updateByPrimaryKeySelective(card);
	   }
	   return 0;
   }
   @Override
   public boolean batchUpdateCardStatus(List<BDLCard> list) {
	  int i = 0;
	  for(BDLCard BDLCard :list){
		 BDLCard selectByPrimaryKey = bdlCardMapper.selectByPrimaryKey(BDLCard.getCardid());
		 if(selectByPrimaryKey!=null){
			selectByPrimaryKey.setCardstatus(BDLCard.getCardstatus());
			int status = bdlCardMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
			if(status!=0){
			   i++;
			}
		 }
	  }
	  if(i==list.size()){
		 return true;
	  }
	  return false;
   }
   @Override
   public int batchDeleteCard(String string) {
	  String[] split = string.split(",");
	  int status = 0;
	  for(String cardid:split){
		 BDLCard card = bdlCardMapper.selectByPrimaryKey(Integer.valueOf(cardid));
		 if(card!=null){
			card.setIsdeleted("1");
			int i = bdlCardMapper.updateByPrimaryKeySelective(card);
			if(i!=0){
			   status++;
			}
		 }
	  }
	  if(status==split.length){
		 return 1;
	  }
	  return 0;
   }

@Override
public BDLCard getCardByOrderIdAndCardType(BDLCard bdlCard) {
	// TODO Auto-generated method stub
	return bdlCardMapper.getCardByOrderIdAndCardType(bdlCard);
}

@Override
public List<BDLCard> selectCardByOrderIdAndEmployeeId(BDLCard bdlCard) {
	// TODO Auto-generated method stub
	return bdlCardMapper.selectCardByOrderIdAndEmployeeId(bdlCard);
}
@Override
public List<BDLCard> selectCardByOrderId(BDLCard bdlCard) {
	// TODO Auto-generated method stub
	return bdlCardMapper.selectCardByOrderId(bdlCard.getOrderid());
}



}
