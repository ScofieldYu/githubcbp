/*   
 * @(#)MyX509TrustManager.java       2017年11月25日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.datamodel;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/** 
 * 证书信任管理器（用于https请求）    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class MyX509TrustManager implements X509TrustManager {

   public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
   }  
 
   public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
   }  
 
   public X509Certificate[] getAcceptedIssuers() {  
       return null;  
   }

}
