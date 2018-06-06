/*   
 * @(#)Async.java       2017年12月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.asyncthread;


import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.employee.asyncthread.AsyncBasic;

/** 
 * 异步线程池    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class AsyncTask implements Runnable{
   
   private AsyncBasic asyncBasic;

   public AsyncTask(){
	  
   }
   public AsyncTask(AsyncBasic asyncBasic){
	  this.asyncBasic =asyncBasic;
   }
   
   @Override
   public void run() {
	  try { 
		 asyncBasic.asyncTask();
      } catch (Exception e) {  
          e.printStackTrace();  
      }  
   }

   public AsyncBasic getAsyncBasic() {
      return asyncBasic;
   }

   public void setAsyncBasic(AsyncBasic asyncBasic) {
      this.asyncBasic = asyncBasic;
   }
   
}
