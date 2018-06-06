/*   
 * @(#)CBPConstant.java       Nov 6, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.customer.datamodel;

/**
 * 存放项目的常量值类
 *
 * @author Scofield
 * @since JDK 1.8
 */
public class CBPConstant {

   public final static String CODE_SUCCESS= "0";
   public final static String MESSAGE_SUCCESS = "成功";

   public final static String CODE_LOSE= "100";
   public final static String MESSAGE_LOSE = "消息已失效";

   public final static String CODE_FAILURE = "101";
   public final static String MESSAGE_FAILURE = "失败或系统错误";

   public final static String CODE_NULL_PARAM = "102";
   public final static String MESSAGE_NULL_PARAM = "参数不正确";

   public final static String CODE_TOKEN_INVALID = "103";
   public final static String MESSAGE_TOKEN_INVALID = "您的账号已在其他设备登录";

    public final static String CODE_CA_SELF_FALSE = "105";
    public final static String MESSAGE_CA_SELF_FALSE = "请先认证个人信息";

    public final static String CODE_CA_COMPANY_FALSE = "106";
    public final static String MESSAGE_CA_COMPANY_FALSE = "请先认证公司信息";


    public final static String CODE_NULL_EMP = "104";
   
   /**
    * 客户的账号类型
    */
   public static enum CAccountTypeEnum {
	  //添加枚举的指定常量
	  PRIVATE(1), PUBLIC(2);   // 1--私户，2--公户

	  //必须增加一个构造函数,变量,得到该变量的值
	  private int mType = 1;

	  private CAccountTypeEnum(int value) {
		 mType = value;
	  }

	  /**
	   * @return 枚举变量实际返回值
	   */
	  public int getType() {
		 return mType;
	  }
   }
   
   /**
    * SAP系统交互返回的状态
    */
   public static enum SAPResponseStatusEnum {
	  //添加枚举的指定常量
	  SUCCESS("S"), FAILURE("E");   // S--成功，E--失败

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "E";

	  private SAPResponseStatusEnum(String value) {
		 mType = value;
	  }

	  /**
	   * @return 枚举变量实际返回值
	   */
	  public String getType() {
		 return mType;
	  }
   }
   
   /**
    * 文件类型
    */
   public static enum FileEnum {
	  //添加枚举的指定常量
	  CUSTOMERIDCARD("1"), BUYERIDCARD("2"), BUSINLICENSE("3"), ATTORNEY("4"), RECEIPT("5"),CONTRACT("6");   
	  // 1--客户身份证,2--买方身份证，3--营业执照，4--委托书，5--收据，6--合同

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "1";

	  private FileEnum(String value) {
		 mType = value;
	  }

	  /**
	   * @return 枚举变量实际返回值
	   */
	  public String getType() {
		 return mType;
	  }
   }
}
