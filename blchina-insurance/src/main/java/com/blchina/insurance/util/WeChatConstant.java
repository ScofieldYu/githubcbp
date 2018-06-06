/*   
 * @(#)WeChatConstant.java       2017年11月24日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.util;

/** 
 * 微信常量类    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class WeChatConstant {
   
   /**公众号AppId*/
   public static final  String APP_ID = "wxcd107669fb61a238";    

   /**公众号AppSecret*/
   public static final  String APP_SECRET = "d7a7eff16e957edcfd6d3a97cb5f998b";

   /**微信支付商户号*/
   public static final String MCH_ID = "1423680702";

   /**微信支付API秘钥*/
   public static final String KEY = "1qaz2wsx3edc4rfv5tgb6yhn7ujm8ikk";
   
   /**签名*/
   public static final String SIGN = "";
   
   public static final String OAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

   
   /**返回结果成功*/
   public static final String CALLBACK_SUCESS = "SUCCESS";
   public static final String CALLBACK_SUCESS_MESSAGE = "OK";
   
   /**返回结果成功*/
   public static final String CALLBACK_FAIL = "FAIL";
   public static final String CALLBACK_FAIL_MESSAGE = "ERROR";

   /**微信支付api证书路径*/
   public static final String CERT_PATH = "***/apiclient_cert.p12";

   /**微信统一下单url*/
   public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

   /**微信申请退款url*/
   public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

   /**微信支付通知url*/
   public static final String NOTIFY_URL = "http://cbp-qas.xbotech.com/cbp/pay/wechatPayNotify";
   
   /**商家查询订单url*/
   public static final String SELECT_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

   
   /**微信交易类型:公众号支付*/
   public static final String TRADE_TYPE_JSAPI = "JSAPI";

   /**微信交易类型:原生扫码支付*/
   public static final String TRADE_TYPE_NATIVE = "NATIVE";

   /**微信甲乙类型:APP支付*/
   public static final String TRADE_TYPE_APP = "APP";
}
