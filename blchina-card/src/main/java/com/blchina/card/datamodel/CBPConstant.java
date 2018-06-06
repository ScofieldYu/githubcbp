/*   
 * @(#)CBPConstant.java       Nov 6, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.card.datamodel;

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
	/**
	 * 卡片任务类型
	 */
	public static enum CardTaskTypeEnum {
		//添加枚举的指定常量
		CARORDER("1"), ORDERKEEP("2"), SECONDCAR("3"), FINANCE("4"), CAREXCHANGE("5"),INSURANCE("6"),CHECKCAR("7"),BOUTIQUE("8");
		// 1--购车下单,2--订单确认，3--二手车，4--金融分期，5--车辆置换，6--车险 7--验车上牌,8--精品

		//必须增加一个构造函数,变量,得到该变量的值
		private String mType = "1";

		private CardTaskTypeEnum(String value) {
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
	 * 卡片状态
	 */
	public static enum CardStatus {
		//添加枚举的指定常量
		WAITTING("1"), DOING("2"), COMPLETE("3");
		// 1--待办,2--进行中，3--完成

		//必须增加一个构造函数,变量,得到该变量的值
		private String mType = "1";

		private CardStatus(String value) {
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
	    * 卡片任务固定类型
	    */
	   public static enum CardTypeEnum {
		//销售顾问
		  BUYCARORDER("1"),ORDERMAINTENANCE("2"),SIGNCONTRACT("3"),
		  TRANSACTLOAN("4"),CARPERMUTE("5"),TRANSACTINSURANCE("6"),
		  CHECKCARBAND("7"),INSTALLBOUTIQUE("8"),
		  //集团财务（财务收银）
		  AFFIRMCOLLECTDEPOSIT("9"),AFFIRMCOLLECTBALANCEPAYMENT("10"),
		  AFFIRMCOLLECTFULL("11"),AFFIRMCOLLECTFINANCIALSTAGE("12"),
		  AFFIRMCOLLECTUSERDCAR("13"),
		  //承保顾问
		  TRANSACTINSURANCEFORINSURANCE("14"),
		  //评估师
		  CARPERMUTEFORASSESSMENT("15"),
		  //二手车财务
		  AFFIRMCOLLECTMONEY("16"),
		  //金融经理
		  TRANSACTFINANCIALSTAGE("17"),
		  //精品顾问
		  PREPAREBOUTIQUE("18"),INSTALLBOUTIQUEFORBOUTIQUE("19"),
		  //验车专员
		  CHECKCARBANDFORCHECK("20"),
		  PAYMENTPURCHASETAX("21"),TRANSACTTEMPORARYLICENSE("22"),
		  //计划员
		  AFFIRMSCHEDULED("23"),AFFIRMSENT("24"),AFFIRMPORTED("25"),
		  AFFIRMROADING("26"),AFFIRMARRIVED("27");
		  //必须增加一个构造函数,变量,得到该变量的值
		  private String mType = "1";

		  private CardTypeEnum(String value) {
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
	    * 卡片任务固定描述
	    */
	   public static enum CardTypeDescriptionEnum {
		  //销售顾问
		  BUYCARORDER("购车下单"),ORDERMAINTENANCE("订单维护"),SIGNCONTRACT("签订合同"),
		  TRANSACTLOAN("办理贷款"),CARPERMUTE("车辆置换"),TRANSACTINSURANCE("办理车险"),
		  CHECKCARBAND("验车上牌"),INSTALLBOUTIQUE("加装精品"),
		  //集团财务（财务收银）
		  AFFIRMCOLLECTDEPOSIT("确认收定金"),AFFIRMCOLLECTBALANCEPAYMENT("确认收尾款"),
		  AFFIRMCOLLECTFULL("确认收全款"),AFFIRMCOLLECTFINANCIALSTAGE("确认金融分期收款"),
		  AFFIRMCOLLECTUSERDCAR("确认二手车收款"),
		  //承保顾问
		  TRANSACTINSURANCEFORINSURANCE("办理车险"),
		  //评估师
		  CARPERMUTEFORASSESSMENT("车辆置换"),
		  //二手车财务
		  AFFIRMCOLLECTMONEY("确认收款"),
		  //金融经理
		  TRANSACTFINANCIALSTAGE("办理金融分期"),
		  //精品顾问
		  PREPAREBOUTIQUE("准备精品"),INSTALLBOUTIQUEFORBOUTIQUE("加装精品"),
		  //验车专员
		  CHECKCARBANDFORCHECK("验车上牌"),
		  PAYMENTPURCHASETAX("缴纳购置税"),TRANSACTTEMPORARYLICENSE("办理临牌"),
		  //计划员
		  AFFIRMSCHEDULED("确认车辆排产"),AFFIRMSENT("确认车辆发送"),AFFIRMPORTED("确认车辆到港"),
		  AFFIRMROADING("确认车辆在途"),AFFIRMARRIVED("确认车辆到店");
		  //必须增加一个构造函数,变量,得到该变量的值
		  private String mType = "1";

		  private CardTypeDescriptionEnum(String value) {
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
	 * 卡片子任务固定类型
	 */
	public static enum TaskTypeDescriptionEnum {
		//销售顾问
		BUYCARORDERFORCUSTROMER("为客户进行购车下单"),ORDERMAINTENANCEFORCUSTROMER("为客户维护订单")
		,TOUCHFINANCIALMANAGER("联系金融经理"),APPOINTMENTUSERDCARASSESS("预约二手车评估")
		,CARPERMUTEFORCUSTROMER("为客户办理车辆置换"),TRANSACTINSURANCEFORCUSTROMER("为客户办理车险"),
		COLLECTPAYMENTPURCHASETAXDATA("收集缴纳购置税资料"),COLLECTTRANSACTTEMPORARYLICENSEDATA("收集办理临牌资料"),
		COLLECTCHECKCARBANDDATA("收集办理验车上牌资料"),APPOINTMENTCHECKCARBAND("预约办理验车上牌"),
		APPOINTMENTINSTALLBOUTIQUETIME("预约加装精品时间"),INSTALLBOUTIQUEFORCUSTOMER("为客户加装精品"),
		CUSTOMERAFFIRM("与客户确认"),
		//集团财务（财务收银）
		AFFIRM("确认"),
		//评估师
		SETUPUSERDCARASSESSPRICE("设置二手车预估价格"),
		//金融经理
		APPOINTMENTFAMILYVISIT("预约家访"),TRANSACTFINANCIALSTAGEFORCUSTROMER("为客户办理金融分期"),
		//精品顾问
		PREPAREBOUTIQUEFORCUSTROMER("为客户准备精品"),SELECTCUSTOMERDATA("查看客户资料"),
		SELECTAPPOINTMENTINSTALLBOUTIQUETIME("查看预约的加装精品时间"),
		//验车专员
		SELECTCUSTOMERPICKNUMWAY("查看客户选号方式"),SELECTAPPOINTMENTCHECKCARBANDTIME("查看预约的验车上牌时间"),
		PAYMENTPURCHASETAXFORCUSTROMER("为客户缴纳购车税"),TRANSACTTEMPORARYLICENSEFORCUSTROMER("为客户办理临牌"),
		SELECTTRANSACTTEMPORARYLICENSENUM("查看客户办理临牌此次数");
		//必须增加一个构造函数,变量,得到该变量的值
		private String mType = "1";

		private TaskTypeDescriptionEnum(String value) {
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
	 * 卡片子任务固定类型
	 */
	public static enum TaskTypeEnum {
		//销售顾问
		BUYCARORDERFORCUSTROMER("1"),ORDERMAINTENANCEFORCUSTROMER("2")
		,TOUCHFINANCIALMANAGER("3"),APPOINTMENTUSERDCARASSESS("4")
		,CARPERMUTEFORCUSTROMER("5"),TRANSACTINSURANCEFORCUSTROMER("6"),
		COLLECTPAYMENTPURCHASETAXDATA("7"),COLLECTTRANSACTTEMPORARYLICENSEDATA("8"),
		COLLECTCHECKCARBANDDATA("9"),APPOINTMENTCHECKCARBAND("10"),
		APPOINTMENTINSTALLBOUTIQUETIME("11"),INSTALLBOUTIQUEFORCUSTOMER("12"),
		CUSTOMERAFFIRM("13"),
		//集团财务（财务收银）
		AFFIRM("14"),
		//评估师
		SETUPUSERDCARASSESSPRICE("15"),
		//金融经理
		APPOINTMENTFAMILYVISIT("16"),TRANSACTFINANCIALSTAGEFORCUSTROMER("17"),
		//精品顾问
		PREPAREBOUTIQUEFORCUSTROMER("18"),SELECTCUSTOMERDATA("19"),
		SELECTAPPOINTMENTINSTALLBOUTIQUETIME("20"),
		//验车专员
		SELECTCUSTOMERPICKNUMWAY("21"),SELECTAPPOINTMENTCHECKCARBANDTIME("22"),
		PAYMENTPURCHASETAXFORCUSTROMER("23"),TRANSACTTEMPORARYLICENSEFORCUSTROMER("24"),
		SELECTTRANSACTTEMPORARYLICENSENUM("25");
		//必须增加一个构造函数,变量,得到该变量的值
		private String mType = "1";

		private TaskTypeEnum(String value) {
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
