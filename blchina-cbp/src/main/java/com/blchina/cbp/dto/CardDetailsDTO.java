/*   
 * @(#)CardDetailsDTO.java       2018年2月5日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;


/** 
 * 卡片详情dto    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class CardDetailsDTO {
   
   private String phoneNumber;
   
   private BDLCard bdlCard;
   
   private List<BDLTask> taskList;

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public BDLCard getBdlCard() {
      return bdlCard;
   }

   public void setBdlCard(BDLCard bdlCard) {
      this.bdlCard = bdlCard;
   }

   public List<BDLTask> getTaskList() {
      return taskList;
   }

   public void setTaskList(List<BDLTask> taskList) {
      this.taskList = taskList;
   } 
   
}
