/*   
 * @(#)BatchDeleteDTO.java       2018年2月24日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.card.dto;

import java.util.List;

import com.blchina.card.model.BDLTask;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class BatchDeleteDTO {

   private Object [] cardIdList;

   private List<BDLTask> taskList;
   
   public List<BDLTask> getTaskList() {
      return taskList;
   }

   public void setTaskList(List<BDLTask> taskList) {
      this.taskList = taskList;
   }

   public Object[] getCardIdList() {
      return cardIdList;
   }

   public void setCardIdList(Object[] cardIdList) {
      this.cardIdList = cardIdList;
   }
}
