/*   
 * @(#)WXTokenDTO.java       Nov 15, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import org.springframework.beans.factory.annotation.Value;
/** 
 * 此类功能描述    
 *
 * @author Scofield 
 * @since JDK 1.8
 */
public class WXTokenDTO {
   private String signature;
   private String timestamp;
   private String nonce;
   private String echostr;
   @Value("${weixin.token}")
   private String token;
   public String getSignature() {
      return signature;
   }
   public void setSignature(String signature) {
      this.signature = signature;
   }
   public String getTimestamp() {
      return timestamp;
   }
   public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
   }
   public String getNonce() {
      return nonce;
   }
   public void setNonce(String nonce) {
      this.nonce = nonce;
   }
   public String getEchostr() {
      return echostr;
   }
   public void setEchostr(String echostr) {
      this.echostr = echostr;
   }
   public String getToken() {
      return token;
   }
   public void setToken(String token) {
      this.token = token;
   }
    
}
