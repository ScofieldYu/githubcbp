/*   
 * @(#)CardService.java       2018年2月2日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.card.service.interfaces;

import java.util.List;

import com.blchina.card.dto.CardDTO;
import com.blchina.card.model.BDLCard;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface CardService {

   List<BDLCard> selectCardListByCustomerName(CardDTO cardDTO);

   BDLCard selectCardByCardId(Integer cardid);

   int saveCard(BDLCard card);

   List<BDLCard> queryCardByEmployee(BDLCard card);

   int updateCardById(BDLCard bdlCard);

   void updateCardStatus(BDLCard card);

   boolean batchUpdateCardStatus(List<BDLCard> list);

   int batchDeleteCard(String string);

   List<BDLCard> selectCardByOrderId(BDLCard bdlCard);

   BDLCard getCardByOrderIdAndCardType(BDLCard bdlCard);

   List<BDLCard> selectCardByOrderIdAndEmployeeId(BDLCard bdlCard);


}
