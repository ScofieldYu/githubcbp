/*   
 * @(#)WeixinResult.java       Nov 2, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.model;

/** 
     和微信端数据交互模板
 *
 * @author Scofield 
 * @since JDK 1.8
 */
public class AllResult<T> {

   private String code;
   private String message;
   private Integer accountType;
   private T data;
   

   
	
	public Integer getAccountType() {
	return accountType;
	}
	
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getCode() {
	       return code;
   }

   public void setCode(String code) {
       this.code = code;
   }

   public String getMessage() {
       return message;
   }

   public void setMessage(String message) {
       this.message = message;
   }

   public T getData() {
       return data;
   }

   public void setData(T data) {
       this.data = data;
   }
}