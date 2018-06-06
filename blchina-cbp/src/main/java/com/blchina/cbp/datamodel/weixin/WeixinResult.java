/*   
 * @(#)WeixinResult.java       Nov 2, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.datamodel.weixin;

import java.io.Serializable;

/**
     和微信端数据交互模板
 *
 * @author Scofield 
 * @since JDK 1.8
 */
public class WeixinResult<T> implements Serializable {


   private String code;
   private String message;
   private T data;

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