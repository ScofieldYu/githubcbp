/*   
 * @(#)SignatureResult.java       Nov 2, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.datamodel.signature;

/** 
 * 和第三方签名服务商数据交互模板    
 *
 * @author Scofield 
 * @since JDK 1.8
 */
public class SignatureResult<T> {

   private String code;
   private String msg;
   private String result;
   private T data;

   public String getCode() {
       return code;
   }

   public void setCode(String code) {
       this.code = code;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public String getResult() {
      return result;
   }

   public void setResult(String result) {
      this.result = result;
   }

   public T getData() {
       return data;
   }

   public void setData(T data) {
       this.data = data;
   }
}

