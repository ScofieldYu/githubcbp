/*   
 * @(#)CBPConstant.java       Nov 6, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.cbp.datamodel;

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

    public final static String SHOP_DAY= "5";//门店默认预约天数
    public final static String SHOP_PaperCost= "0.1";//默认纸张成本
    public final static int SHOP_ContractCount= 3;//默认合同张数
    public final static int SHOP_TEMPLATE_NUM= 3;//门店默认时间模板数目
    public final static String SHOP_TIME_FIRST_START="09:30";
    public final static String SHOP_TIME_FIRST_END="11:30";
    public final static String SHOP_TIME_TWO_START="11:30";
    public final static String SHOP_TIME_TWO_END="15:30";
    public final static String SHOP_TIME_THREE_START="15:30";
    public final static String SHOP_TIME_THREE_END="17:30";

    public final static String CODE_NULL_EMP = "104";
    
    public final static String ISRECNOTIIFY_YES = "1";
    public final static String ISRECNOTIIFY_NO = "0";
    
    public final static String ORDER_NOEXIST = "订单不存在";
   
    
    public final static String SIGN_SUCCESS = "3000";//签章成功
    public final static String SIGN_FAIL = "3001";//签章失败
    
    public final static String PAY_CLASS_FULL = "2";//全款
    public final static String PAY_CLASS_DEPOSIT = "1";//定金
    
    public final static String PAY_STATUS_YES = "1";//支付状态
    public final static String PAY_STATUS_NO = "0";//未支付状态
    
    public final static String PAY_TYPE_WX = "71";//微信
    public final static String PAY_TYPE_DH = "41";//电汇
    
    public final static String ORDER_NO_INSURANCE = "该订单下没有车险";
    
    public final static String CLOSE_CAUSE_TYPE1 = "客户放弃购买";
    
    public final static String CLOSE_CAUSE_TYPE2 = "合同下发时机错误";
    
    public final static String CLOSE_CAUSE_TYPE3 = "需要变更合同编号";
    

    public final static String KEPP_NUMBER = "保留原号";
    
    public final static String FIFTH_ONE = "50选一";
    
    public final static String SELF_NUMBER = "自编号牌";
    
    public final static String FOUR_INVOICE = "新车机动车发票(四联)原件";
    public final static String CAR_CERTIFICATION = "机动车合格证原件";
    public final static String ENVIRON_INFO = "轻型汽油车环保信息原件";
    public final static String CONSISTENT = "车辆一致性证书原件";
    public final static String CAR_ENVIRON = "轻型汽油车环保信息随车清单原件";
    public final static String IDENTIFICATION = "身份证及居住证原件";
    public final static String COMPULSORY = "车辆交强险原件";
    public final static String BUY_TAX = "车辆购置税申请信息表";
    public final static String CAR_UPDATE = "车辆更新指标三张";
    public final static String TAX_FIRST = "新车机动车发票第一联原件";
    
    
    public final static String BDLSELL_CARD = "bdlSell_card.html";//卡片首页
    
    public final static String ORDER_DETAILS = "orderDetails.html";//精品顾问订单详情
    public final static String CUSTOMER_ORDERDETAILS = "customerOrderDetails.html";//客户订单详情
    
   
    
    //四方类型
    public static enum FourCustomerTypeEnum {
   	  //添加枚举的指定常量
       PAYER("1"),INVOICE("2"),SOLD("3"),DELIVERY("4");  
       //1--付款方  2--开票方          3--售达方         4--送达方

   	  //必须增加一个构造函数,变量,得到该变量的值
   	  private String mType = "1";

   	  private FourCustomerTypeEnum(String value) {
   		 mType = value;
   	  }
   	  public String getType() {
		 return mType;
	  }
    }
	/**
	 * 保险类型
	 */
	public static enum InsuranceTypeEnum {
		//添加枚举的指定常量
		THREEINSURANCE("1"),CARUSERINSURANCE("2"),SCRATCHINSURANCE("3"),OTHERINSURANCE("4"),BUSINESSINSURANCE("5"),MUSTINSURANCE("6");   // 1--私户，2--公户

		//必须增加一个构造函数,变量,得到该变量的值
		private String mType = "1";

		private InsuranceTypeEnum(String value) {
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
       EMPLOYEE_FINANCE_CONSULTANT(10002227),EMPLOYEE_BRAND_MANAGER(10000130);
    	//7--金融经理 						8---总经理
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
    
    
    //合同关闭类型
    public static enum CloseCauseTypeEnum {
   	  //添加枚举的指定常量
   	  TYPE1("1"), TYPE2("2"),TYPE3("3"),TYPE4("4");   //1--原因1  2--原因2  3--原因 4--其他

   	  //必须增加一个构造函数,变量,得到该变量的值
   	  private String mType = "1";

   	  private CloseCauseTypeEnum(String value) {
   		 mType = value;
   	  }
   	  public String getType() {
		 return mType;
	  }
    }
    //定金状态
    public static enum DepositStatusEnum {
  	  //添加枚举的指定常量
  	  PAYSUCCESS("支付"), PAYERROR("未支付");   

  	  //必须增加一个构造函数,变量,得到该变量的值
  	  private String mType = "未支付";

  	  private DepositStatusEnum(String value) {
  		 mType = value;
  	  }

  	  /**
  	   * @return 枚举变量实际返回值
  	   */
  	  public String getType() {
  		 return mType;
  	  }
     }
	//合同关闭类型
	public static enum UnsignReasonEnum {
		//添加枚举的指定常量
		TYPE1("1"), TYPE2("2"),TYPE3("3"),TYPE4("4");   //1--开票方为公户  2--客户预付款汇款2  3--客户不配合 4--其他

		//必须增加一个构造函数,变量,得到该变量的值
		private String mType = "1";

		private UnsignReasonEnum(String value) {
			mType = value;
		}
		public String getType() {
			return mType;
		}
	}
	//合同关闭类型
	public static enum UnsignReasonDescriptionEnum {
		//添加枚举的指定常量
		TYPE1("开票方为公户"), TYPE2("客户预付款汇款"),TYPE3("客户不配合"),TYPE4("其他");   //1--开票方为公户  2--客户预付款汇款2  3--客户不配合 4--其他

		//必须增加一个构造函数,变量,得到该变量的值
		private String mType = "1";

		private UnsignReasonDescriptionEnum(String value) {
			mType = value;
		}
		public String getType() {
			return mType;
		}
	}
    //微信支付状态
    public static enum PayStatusEnum {
 	  //添加枚举的指定常量
 	  PAYNO(0),PAYSUCCESS(1), PAYERROR(2);   // 0--未支付，1--支付成功，2--支付失败

 	  //必须增加一个构造函数,变量,得到该变量的值
 	  private int mType = 0;

 	  private PayStatusEnum(int value) {
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
    * 时段预约状态
    */
   public static enum TimePeriodStatusEnum {
	  //添加枚举的指定常量
	  CANRESERVE(1), NONRESERVE(3), RESERVED(2);   // 1--可预约，3--不可预约，2--已预约

	  //必须增加一个构造函数,变量,得到该变量的值
	  private int mType = 1;

	  private TimePeriodStatusEnum(int value) {
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
    * 订单类型
    */
   public static enum OrderTypeEnum {
	  //添加枚举的指定常量
	  SHOWING("350x"), MAGNATE("Q005"), DERIVEORDER("Q007"),DERIVERETURN("Q008");   
	  // 350X--整车:展厅销售订单/Q005--整车:大客户销售订单/Q007--衍生单独销售订单/Q008--衍生单独销售退单
      //Q007不在APP上显示，Q008不在APP和微信服务号上显示
	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "350x";

	  private OrderTypeEnum(String value) {
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
    * 订单状态
    */
   public static enum OrderStatusEnum {
	  //添加枚举的指定常量
	  WAITINGPAY(0), WAITINGALLPAY(1), PAID(2),CLOSED(3);   // 0--等待支付，1--等待结清尾款，2--完成支付,3--已关单

	  //必须增加一个构造函数,变量,得到该变量的值
	  private int mType = 0;

	  private OrderStatusEnum(int value) {
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
    * 无纸化二期订单状态
    */
   public static enum PaperlessOrderStatusEnum {
	  //添加枚举的指定常量
	  WAITINGORDER(0), WAITINGPAY(1), WAITINGCONFIRM(2),FINISHED(3);   // 0--待下单，1--待支付，2--待确认,3--已完成

	  //必须增加一个构造函数,变量,得到该变量的值
	  private int mType = 0;

	  private PaperlessOrderStatusEnum(int value) {
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
	  * 业务模块状态
	 */
	 public static enum ModuleStatusEnum {
		  //添加枚举的指定常量
		  WAITING(1), PROCESSING(2),FINISHED(3);   //1--代办，2--办理中，3--已完成

		  //必须增加一个构造函数,变量,得到该变量的值
		  private int mType = 0;

		  private ModuleStatusEnum(int value) {
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
    * 合同状态
    */
   public static enum ContractStatusEnum {
	  //添加枚举的指定常量
	  WAITINSIGN(1), SIGNED(2),FINISH(3),CLOSED(4),REVOCATION(5);   // 1--待签，2--已签(客户签署后),3--完成(公司签署后),4--已关闭,5--已撤销

	  //必须增加一个构造函数,变量,得到该变量的值
	  private int mType = 1;

	  private ContractStatusEnum(int value) {
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
    * 消息状态
    */
   public static enum MessageStatusEnum {
	  //添加枚举的指定常量
	   UNSET("1"),SET("2"), ;   // 1--未设置，2--已设置

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "1";

	  private MessageStatusEnum(String value) {
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
    * 消息类型
    */
   public static enum MessageTypeEnum {
	  //添加枚举的指定常量
	   SALE_FIVEINTERNAL("1"),SALE_CHECK("2"),SALE_FIVEEXTERNAL("3"),CUSTOMER_FIVEINTERNAL("4"),LOGISTICS("5") ;   
	  // 1--销售顾问5天内的不可用时间设置，2--销售顾问查看时间设置，3--销售顾问为客户设置5天外的预约时间，4--客户设置5天内预约时间，5--物流状态

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "1";

	  private MessageTypeEnum(String value) {
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
    * 进口车物流状态
    */
   public static enum ImportLogisticsStatusEnum {
	  //添加枚举的指定常量
	  CONFIRMED("1"),SCHEDULED("2"), SENT("3"), PORTED("4"),ROADING("5"),ARRIVED("6"),DELIVERED("7");   
	  // 1--订单已确认，2--车辆已排产,3--车辆已发运,4--车辆已到港,5--车辆已在途，6--车辆已到店

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "1";

	  private ImportLogisticsStatusEnum(String value) {
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
    * 物流状态
    */
   public static enum LogisticsStatusEnum {
	  //添加枚举的指定常量
	  CONFIRMED("订单已确认"),SCHEDULED("车辆已排产"), SENT("车辆已发运"), PORTED("车辆已到港"),ROADING("车辆已在途"),ARRIVED("车辆已到店"),DELIVERED("车辆已交付"),DELIVERY("车辆已出库");

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "订单已确认";

	  private LogisticsStatusEnum(String value) {
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
    * 国产车物流状态
    */
   public static enum DomesticLogisticsStatusEnum {
	  //添加枚举的指定常量
	  CONFIRMED("11"), ROADING("12"),ARRIVED("13"),DELIVERED("14");   
	  // 1--订单已确认2--车辆已在途，3--车辆已到店,4--车辆已交付

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "11";

	  private DomesticLogisticsStatusEnum(String value) {
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
    * 收据类型
    */
   public static enum ReceiptStatusEnum {
	  //添加枚举的指定常量
	  EARNEST("1"), REMAIN("2"), FULL("3");   
	  // 1--定金电子收据,2--尾款电子收据

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "1";

	  private ReceiptStatusEnum(String value) {
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
	 * 文件扩展类型
	 */
	public static enum FileTypeExtEnum {
		//添加枚举的指定常量
		BASICINFO("1"), CONTRACTINFO("2"), PAYINFO("3"),FINAACEINFO("4"),CHANGECAR("5"),INSURANCEINFO("6"),BOUTIQUEINFO("7"),VEHICLELICENSING("8"),RETURNCARINFO("9");
		// 1--基础信息,2--合同相关3支付相关4金融分期5二手车置换6保险信息7加装精品8验车上牌9交车相关

		//必须增加一个构造函数,变量,得到该变量的值
		private String mType = "1";

		private FileTypeExtEnum(String value) {
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
	  CUSTOMERIDCARD("1"), BUYERIDCARD("2"), BUSINLICENSE("3"), ATTORNEY("4"), RECEIPT("5"),CONTRACT("6"),ADVANCEDPAID("7"),
	  // 1--客户身份证,2--买方身份证，3--营业执照，4--委托书，5--收据，6--合同,7--垫付证明
	   DRIVERLICENSE("9"), VEHICLECONIRMLIST("10039"),VEHICLEEXTSERVICE("10040"),VEHICLEFINANCEINFO("10041"),FREEAPPLICATIONLIST("10042"),TICKETCONFIRMLIST("10043"),INVIOCENAMECHAGECERTFICATE("10044"),
      //9驾驶证 ,10039车辆销售确认单,10040车辆延伸服务协议,10041车辆财务信息联,10042手续费减免申请单,10043开票信息确认单,10044发票名称变更证明
	   VEHICLESALESINVICE("10045"),VEHICLEINSPECTIIONCONFIRMLIST("10046"),ADDCARPURCHASELIST("10047"),REFUNDS("10048"),RESIDENCEPERMIT("10049"),VEHICLECERTIFICATE("10050"),IMPORTCARINSPECTIONLIST("10051"),
	   //10045机动车销售发票,10046商品车交车检查确认单,10047追加购车费用清单,10048退款说明,10049暂住证/居住证,10050车辆合格证,10051进口车关单/商检单
	   WARRANTYHANDBOOK("10052"),THREEPACKAGEVOUCHERS("10053"),VEHICLEMANAGEMENTHANDOVERTABLE("10054"),DISCLAIMERAGREEMENT("10055"),SALESCUSTOMERARCHIVES("10056"),MARRIAGECERTIFICATE("10057"),EMPLOYEECARD("10058"),
	   //10052保修保养手册,10053三包凭证,10054车辆管理交接表,10055免责协议,10056销售客户档案明细,10057结婚证,10058工作证
	   OFFICERSCERTIFICATE("10059"),POLICECARD("10060"),CIVILSERVICECERTIFICATE("10061"),HOUSECARD("10062"),DRIVINGBOOK("10063"),VEHICLEREGISTERCERTIFICATE("10064"),FINANCIALINSURANCESUMMARY("10065"),FINANCIALINSURANCELOANLETTER("10066"),
	   //10059军官证,10060警官证,10061公务员证,10062房产证,10063行驶证，10064车辆登记证,10065金融保险咨询纪要,10066金融机构批贷函
	   USERDCARASSESSMENTLIST("10067"),ONEHUNDRENDANDTENTESTSHEETS("10068"),MAINTENANCEPLAN("10069"),PREPAREDNESSPLANBOOK("10070"),PURCHASEINTENTIONCUSTOMERMANAGECARD("10071"),CONFIRMATIONPURCHASEBOOK("10072"),PURCHASEVEHICLEHANDOVER("10073"),
	   //10067二手车评估单,10068110项检测单,10069维修整备计划书(评估),10070手续整备计划书(评估),10071评估收购意向客户管理卡,10072收购确认书,10073收购车辆手续交接单Motor vehicle driving licensevehicle and ship use
	   REFURBISHMENTTABLE("10074"),RECEIPTLIST("10075"),PURCHASECARINVOICES("10076"),OWNERIDENTTYCERTIFICATE("10077"),MOTORVEHICLEDRVINGLICENSE("10078"),MOTORVEHICLEREGISTERLICENSE("10079"),PURCHASECARFULLCERTIFICATE("10080"),VEHICLESHIPUSECERTIFICATE("10081"),
	    //10074翻新鉴定表 ,10075收款单(资金往来凭证),10076购车发票(过户发票),10077车主身份证明,10078机动车行驶证,10079机动车登记证,10080车辆购置税完税证明 ,10081车船使用税凭证
       INSURANCELISTINVOICE("10082"),LETTERENTRUSTMENT("10083"),CARPHOTO("10084"),FIRSTPREMINUMRECEIPTS("10085"),MOTORVEHICLECERTIFICATE("10086"),ORIGINALVEHICLEINVOICE("10087"),LIGHTVEHICLEINFORSOURCE("10088"),VEHICLECONFORMANCECERTIFICATE("10089"),LIGHTVEHICLEINFORWITHSOURCE("10090"),
	   //10082保险单及发票,10083过户委托书,10084车辆照片,10085保费收据第一联原件,10086机动车合格证原件,10087 新车机动车发票(四联)原件,10088轻型汽油车环保信息原件,10089车辆一致性证书原件,10090轻型汽油车环保信息随车清单原件Vehicle inspection agency attorneyConstruction confirmation shee
	   VEHICLEINSURANCEORIGINAL("10091"),APPLICATIONVEHICLEPURHASE("10092"),THREEVEHICLEUPDATEINDICATORS("10093"),AGENCYAGREEMENT("10094"),APPLICATIONAGENCYAGREEMENT("10095"),VEHICLEINSURNACELIST("10096"),VEHICLEINVOICESOURCE("10097"),VEHILEINSPECTIONAGENCYBOOK("10098"),CONSTRUCTIONCONFIRMATION("10099"),BOUTIQUECONFIRMATION("10100"),
	    //10091车辆交强险原件,10092车辆购置税申请信息表,10093车辆更新指标三张,10094加盖公章的代办协议,10095 加盖公章的车辆购置税纳税申请,10096车辆保险单,10097机动车发票(第1联)原件,10098代办验车委托书,10099施工确认单,10010精品确认单
		IMPORTCARLIST("10101"),VEHICLEBUSINESSLIST("10102");
		//10101进口车关单原件，10102车辆商业险原件
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
    * 验车上牌选号类型
    */
   public static enum ChoiceNumberEnum {
	  //添加枚举的指定常量
	  KEPPNUMBER("1"), FIFTHONE("2"), SELFNUMBER("3");   
	  // 1--保留原号,2--50选一，3--自编号牌

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "1";

	  private ChoiceNumberEnum(String value) {
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
    * 验车上牌类型
    */
   public static enum CheckCarResourceEnum {
	  //添加枚举的指定常量
	  FOURINVOICE("1"), CARCERTIFICATION("2"), ENVIRONINFO("3"),CONSISTENT("4"),CARENVIRON("5"),IDENTIFICATION("6"),COMPULSORY("7"),BUYTAX("8"),CARUPDATE("9"), TAXFIRST("10");   
	  // 1--新车机动车发票(四联)原件,2--机动车合格证原件，3--轻型汽油车环保信息原件,4--车辆一致性证书原件，5--轻型汽油车环保信息随车清单原件，
	  // 6--身份证及居住证原件，7--车辆交强险原件，8--车辆购置税申请信息表,9--车辆更新指标三张,10--新车机动车发票第一联原件

	  //必须增加一个构造函数,变量,得到该变量的值
	  private String mType = "1";

	  private CheckCarResourceEnum(String value) {
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
	  PAYMENTPURCHASETAXFORCUSTROMER("为客户缴纳购车税"),TRANSACTTEMPORARYLICENSEFORCUSTROMER("办理临牌"),
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
	
	
	/**
	 * @return 权限
	 */
	public static enum EmployeeTypeEnumRole {
	   //添加枚举的指定常量
	   EMPLOYEE_SALES_DIRECTOR_ROLE("101"), EMPLOYEE_SALES_MANAGER_ROLE("102"),
       // 1--销售总监，						2--销售经理
       EMPLOYEE_SALES_CONSULTANT_ROLE("103"),EMPLOYEE_INSURANCE_CONSULTANT_ROLE("105"),
       //3--销售顾问                                                  4--精品顾问
       EMPLOYEE_BOUTIQUE_CONSULTANT_ROLE("108"),EMPLOYEE_CHECKCARPERSON_CONSULTANT_ROLE("107"),
        //5-- 二手车验车专员                                                 6--验车上牌专员
       EMPLOYEE_FINANCE_CONSULTANT_ROLE("104"),EMPLOYEE_STOREMANAGER_CONSULTANT_ROLE("100"), EMPLOYEE_SECENDCAR_CONSULTANT_ROLE("109"),
    	//7--金融承保顾问                             			 8--店总                                 						9二手车评估师
    	EMPLOYEE_SECONDCAR_CHECK_CONSULTANT_ROLE("106"),EMPLOYEE_STOREFINANCE_COUNSULTANT_ROLE("110"),EMPLOYEE_SECONDCAR_FINANCE_CONSULTANT_ROLE("111");
    	//10           承保顾问                                                                    11--门店财务											12--二手车财务
	   	//必须增加一个构造函数,变量,得到该变量的值
	   	private String mType = "100";
	   
	   private EmployeeTypeEnumRole(String value) {
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
