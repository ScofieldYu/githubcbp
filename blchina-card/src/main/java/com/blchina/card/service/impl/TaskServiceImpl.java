/*   
 * @(#)TaskServiceImpl.java       2018年2月5日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.card.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.card.model.BDLCard;
import com.blchina.card.model.BDLTask;
import com.blchina.card.dao.*;
import com.blchina.card.datamodel.CBPConstant;
import com.blchina.card.datamodel.weixin.WeixinResult;
import com.blchina.card.service.interfaces.TaskService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/TaskService")
public class TaskServiceImpl implements TaskService {
   
   @Autowired
   private BDLTaskMapper bdlTaskMapper;
   @Override
   public List<BDLTask> selectTaskListByCardId(BDLCard card) {
	  return bdlTaskMapper.selectTaskListByCardId(card.getCardid());
   }
   @Override
   public WeixinResult updateCardTaskList(List<BDLTask> taskList) {
	  WeixinResult res = new WeixinResult();
      res.setCode(CBPConstant.CODE_NULL_PARAM);
      res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  int status = 0;
	  int status1 = 0;
	  for(BDLTask task : taskList){
		 BDLTask task2 = bdlTaskMapper.selectByPrimaryKey(task.getTaskid());
		 if(task2!=null && "1".equals(task2.getTaskstatus())){
			status1++;
		 }
	  }
	  if(status1==taskList.size()){
		 res.setCode("1");
	     res.setMessage(CBPConstant.MESSAGE_SUCCESS);	
	     return res;
	  }
	  for(BDLTask task : taskList){
		 BDLTask task2 = bdlTaskMapper.selectByPrimaryKey(task.getTaskid());
		 if(task2!=null){
			task2.setTaskstatus(task.getTaskstatus());
			int i = bdlTaskMapper.updateByPrimaryKeySelective(task2);
			if(i!=0){
			   status++;
			}
		 }
	  }
	  if(status==taskList.size()){
		 res.setCode(CBPConstant.CODE_SUCCESS);
	     res.setMessage(CBPConstant.MESSAGE_SUCCESS);	
	  }
	  return res;
   }

	@Override
	public void save(BDLTask task) {
		bdlTaskMapper.insert(task);
	}
	@Override
	public int updateTaskStatusComplete(BDLTask task) {
		// TODO Auto-generated method stub
		return bdlTaskMapper.updateTaskStatusComplete(task);
	}

}
