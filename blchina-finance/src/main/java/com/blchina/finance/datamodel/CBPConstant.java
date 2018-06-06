/*   
 * @(#)CBPConstant.java       Nov 6, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.finance.datamodel;

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
    
    public final static String STATUS_SUCCESS = "S";
    
    public final static String EMPLOYEE_STATUS = "3";
   
    public final static String STATUS_ERROR = "E";
    
    public final static String DESCRIPTIOIN_SUCCESS = "操作成功";
   
    public final static String DESCRIPTIOIN_ERROR = "操作失败";

    public final static String FDD_SEX_WOMAN = "F";//法大大性别女
    
    public final static String FDD_SEX_MAN = "M";//法大大性别男
    
    public final static String SAP_SEX_MAN = "1";//SAP性别男
    
    public final static String SAP_SEX_WOMAN = "0";//SAP性别女
    
    public final static String SAP_SEX_NO_MAINTENANCE = "2";//SAP性别 没维护
    
    /**
     * 客户的账号类型
     */
    public static enum EmployeeTypeEnum {
 	  //添加枚举的指定常量
       EMPLOYEE_SALES_DIRECTOR(10000049), EMPLOYEE_SALES_MANAGER(10000050),
       // 1--销售总监，						2--销售经理
       EMPLOYEE_SALES_CONSULTANT(10000066),EMPLOYEE_INSURANCE_CONSULTANT(10000820),
       //3--销售顾问                                                  4--承保顾问
       EMPLOYEE_BOUTIQUE_CONSULTANT(10000122),EMPLOYEE_CHECKCARPERSON_CONSULTANT(10001018),
        //5--精品顾问                                                  6--验车专员
       EMPLOYEE_FINANCE_CONSULTANT(10002227);
    	//7--金融经理 
 	  //必须增加一个构造函数,变量,得到该变量的值
 	  private int mType = 0;

 	  private EmployeeTypeEnum(int value) {
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
    * 门店品牌
    */
   public static enum StoreBrandEnum {
	  //添加枚举的指定常量
	  //AUDI("1"), BENZ("2"), PORSCHE("3"), VW("4"), BENTLEY("5");   
	  奥迪("1"), 奔驰("2"), 保时捷("3"), 大众("4"), 宾利("5");   
	  // 1--奥迪,2--奔驰，3--保时捷，4--大众，5--宾利

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "1";

	  private StoreBrandEnum(String value) {
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
