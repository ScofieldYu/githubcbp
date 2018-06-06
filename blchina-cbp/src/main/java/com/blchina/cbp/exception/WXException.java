/*   
 * @(#)WXException.java       2018年3月5日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.exception;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class WXException extends RuntimeException {
   
   	private static final long serialVersionUID = -7419400618793645414L;
   	
	private String code = "";// 异常对应的返回码
	
	private String message;//异常对应的描述信息

   public WXException(String code, String message) {
	  super();
	  this.code = code;
	  this.message = message;
   }

   public WXException(String message) {
	  super();
	  this.message = message;
   }

   public WXException() {
	  super();
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
	
}
