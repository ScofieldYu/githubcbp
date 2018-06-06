/*   
 * @(#)TaskService.java       2018年2月5日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.card.service.interfaces;

import java.util.List;

import com.blchina.card.datamodel.weixin.WeixinResult;
import com.blchina.card.model.BDLCard;
import com.blchina.card.model.BDLTask;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface TaskService {

   List<BDLTask> selectTaskListByCardId(BDLCard card);

   WeixinResult updateCardTaskList(List<BDLTask> taskList);

   void save(BDLTask task);

   int updateTaskStatusComplete(BDLTask task);

}
