/*
 * @(#)EmployeeController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;

import com.blchina.employee.dto.*;
import com.blchina.employee.model.BDLStore;
import com.blchina.employee.model.BDLUnEmployee;
import com.blchina.employee.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.blchina.employee.asyncthread.AsyncTask;
import com.blchina.employee.asyncthread.DepartmentAsync;
import com.blchina.employee.asyncthread.DutyAsync;
import com.blchina.employee.asyncthread.EmployeeAsync;
import com.blchina.employee.asyncthread.TitleAsync;
import com.blchina.employee.datamodel.CBPConstant;
import com.blchina.employee.datamodel.sap.SAPResponse;
import com.blchina.employee.datamodel.sap.SAPResult;
import com.blchina.employee.datamodel.weixin.WeixinResult;
import com.blchina.employee.dto.BDLEmployeeDTO.Employee;
import com.blchina.employee.model.BDLEmployee;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;
import com.itextpdf.text.log.SysoCounter;

import net.sf.json.JSONObject;


/**
 * 员工操作controller
 *
 * @author jihaisheng
 * @since JDK 1.8
 */

@RestController
@RequestMapping("/ee")
public class EmployeeController {
   @Autowired
   private BDLDepartmentService bdlDepartmentService;
   @Autowired
   private BDLTitleService bdlTitleService;
   @Autowired
   private BDLEmployeeService bdlEmployeeService;
   @Autowired
   private BDLDutyService bdlDutyService;
   @Autowired
   private BDLUnEmployeeService bdlUnEmployeeService;
	@Autowired
	private BDLStoreService storeService;
   @Autowired
   protected Properties systemConfig;
   
   @Resource(name = "taskExecutor")  
   private TaskExecutor taskExecutor;

   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   /**
    * 部门下发接口
    * 
    * @param bdlDepartmentDTO
    * @return
    */
   @RequestMapping("/department")
   public SAPResult insertOrUpdateDepartment(
		 @RequestBody BDLDepartmentDTO bdlDepartmentDTO) {
	  int status = 0;
	  SAPResult sapResult = new SAPResult();
	  List<SAPResponse> responseList = new ArrayList<SAPResponse>();
	  DepartmentAsync departmentAsync = new DepartmentAsync();
	  departmentAsync.setBdlDepartmentDTO(bdlDepartmentDTO);
	  taskExecutor.execute(new AsyncTask(departmentAsync)); 
	  int size = bdlDepartmentDTO.getRecords().size();
	  for (int i = 0; i < size; i++) {
		 SAPResponse record = new SAPResponse();
		 BDLDepartmentDTO.Department department = bdlDepartmentDTO.getRecords()
			   .get(i);
		 try {
			status = bdlDepartmentService.insertOrUpdateDepartment(department);
		 }
		 catch (Exception e) {
			record.setSAP_ID(department.getORGEH());
			record.setBL_ID(department.getORGEH());
			record.setStatus(CBPConstant.STATUS_ERROR);
			record.setDescription(e.getMessage().toString());
			status = 0;
			e.printStackTrace();
		 }
		 if (status != 0) {
			record.setSAP_ID(department.getORGEH());
			record.setBL_ID(department.getORGEH());
			record.setStatus(CBPConstant.STATUS_SUCCESS);
			record.setDescription(CBPConstant.DESCRIPTIOIN_SUCCESS);
		 }
		 responseList.add(record);
	  }
	  sapResult.setRecords(responseList);
	  return sapResult;
   }

   

   /**
    * 职位下发接口
    * 
    * @param bdlDutyDTO
    * @return
    */

   @RequestMapping("/duty")
   public SAPResult insertOrUpdateDuty(@RequestBody BDLDutyDTO bdlDutyDTO) {
	  int status = 0;
	  int size = bdlDutyDTO.getRecords().size();
	  SAPResult sapResult = new SAPResult();
	  List<SAPResponse> responseList = new ArrayList<SAPResponse>();
	  DutyAsync dutyAsync = new DutyAsync();
	  dutyAsync.setBdlDutyDTO(bdlDutyDTO);;
	  taskExecutor.execute(new AsyncTask(dutyAsync)); 
	  for (int i = 0; i < size; i++) {
		 BDLDutyDTO.Duty duty = bdlDutyDTO.getRecords().get(i);
		 SAPResponse record = new SAPResponse();
		 try {
			status = bdlDutyService.insertOrUpdateDuty(duty);
		 }
		 catch (Exception e) {
			record.setSAP_ID(duty.getPLANS());
			record.setBL_ID(duty.getPLANS());
			record.setStatus(CBPConstant.STATUS_ERROR);
			record.setDescription(e.getMessage().toString());
			status = 0;
			e.printStackTrace();
		 }		 
		 if (status != 0) {
			record.setSAP_ID(duty.getPLANS());
			record.setBL_ID(duty.getPLANS());
			record.setStatus(CBPConstant.STATUS_SUCCESS);
			record.setDescription(CBPConstant.DESCRIPTIOIN_SUCCESS);
		 }
		 responseList.add(record);

	  }
	  sapResult.setRecords(responseList);
	  return sapResult;
   }

  

   /**
    * 职务下发接口
    * 
    * @param bdlTitleDTO
    * @return
    */

   @RequestMapping("/title")
   public SAPResult insertOrUpdateTitle(@RequestBody BDLTitleDTO bdlTitleDTO) {
	  int status = 0;
	  int size = bdlTitleDTO.getRecords().size();
	  SAPResult sapResult = new SAPResult();
	  TitleAsync titleAsync = new TitleAsync();
	  titleAsync.setBdlTitleDTO(bdlTitleDTO);;
	  taskExecutor.execute(new AsyncTask(titleAsync)); 
	  List<SAPResponse> responseList = new ArrayList<SAPResponse>();
	  for (int i = 0; i < size; i++) {
		 SAPResponse record = new SAPResponse();
		 BDLTitleDTO.Title title = bdlTitleDTO.getRecords().get(i);
		 try {
			status = bdlTitleService.insertOrUpdateTitle(title);
		 }
		 catch (Exception e) {
			record.setSAP_ID(title.getSTELL());
			record.setBL_ID(title.getSTELL());
			record.setStatus(CBPConstant.STATUS_ERROR);
			record.setDescription(e.getMessage().toString());
			status = 0;
			e.printStackTrace();
		 }	
		 if (status != 0) {
			record.setSAP_ID(title.getSTELL());
			record.setBL_ID(title.getSTELL());
			record.setStatus(CBPConstant.STATUS_SUCCESS);
			record.setDescription(CBPConstant.DESCRIPTIOIN_SUCCESS);
		 }
		 responseList.add(record);
	  }
	  sapResult.setRecords(responseList);
	  return sapResult;

   }
   /**
    * 员工下发接口
    * 
    * @param bdlEmployeeDTO
    * @return
    */

   @RequestMapping("/employee")
   public SAPResult insertOrUpdateEmployee(
		 @RequestBody BDLEmployeeDTO bdlEmployeeDTO) {
	  int status = 0;
	  SAPResult sapResult = new SAPResult();
	  SAPResponse records = new SAPResponse();
	  List<SAPResponse> responseList = new ArrayList<SAPResponse>();
	  List<Employee> recordsList = bdlEmployeeDTO.getRecords();
	  int size = bdlEmployeeDTO.getRecords().size();
	  EmployeeAsync employeeAsync = new EmployeeAsync();
	  employeeAsync.setBdlEmployeeDTO(bdlEmployeeDTO);;
	  taskExecutor.execute(new AsyncTask(employeeAsync)); 
	  for (int i = 0; i < size; i++) {
		 SAPResponse record = new SAPResponse();
		 BDLEmployeeDTO.Employee employee=null;
		 try{
		 employee = recordsList.get(i);
		 }catch(Exception e){
			System.out.println(i);
		 }
		 if (bdlEmployeeDTO != null
			   && CBPConstant.EMPLOYEE_STATUS.equals(employee.getZLZ_FLAG())) {
			//激活
			try {
			   if(StringUtil.isNullorEmpty(employee.getE_MAIL())){
				  throw new RuntimeException("邮箱不能为空");
				 }
				employee.setE_MAIL(employee.getE_MAIL().toLowerCase());
			   status = bdlEmployeeService.insertOrUpdateEmployee(employee);
			}
			catch (Exception e) {
			   record.setSAP_ID(employee.getPERNR());
			   record.setBL_ID(employee.getPERNR());
			   record.setStatus(CBPConstant.STATUS_ERROR);
			   record.setDescription(e.getMessage().toString());
			   status = 0;
			   e.printStackTrace();
			}
			if (status != 0) {
			   record.setSAP_ID(employee.getPERNR());
			   record.setBL_ID(employee.getPERNR());
			   record.setStatus(CBPConstant.STATUS_SUCCESS);
			   record.setDescription(CBPConstant.DESCRIPTIOIN_SUCCESS);
			}
			responseList.add(record);
		 }
		 if (bdlEmployeeDTO != null
			   && !CBPConstant.EMPLOYEE_STATUS.equals(employee.getZLZ_FLAG())) {
			//未激活
			try {
			   status = bdlUnEmployeeService.insertOrUpdateUnEmployee(employee);
			}
			catch (Exception e) {
			   record.setSAP_ID(employee.getPERNR());
			   record.setBL_ID(employee.getPERNR());
			   record.setStatus(CBPConstant.STATUS_ERROR);
			   record.setDescription(e.getMessage().toString());
			   status = 0;
			   e.printStackTrace();
			}
			if (status != 0) {
			   record.setSAP_ID(employee.getPERNR());
			   record.setBL_ID(employee.getPERNR());
			   record.setStatus(CBPConstant.STATUS_SUCCESS);
			   record.setDescription(CBPConstant.DESCRIPTIOIN_SUCCESS);
			}
			responseList.add(record);
		 }
	  }
	  sapResult.setRecords(responseList);
	  return sapResult;
   }


   @RequestMapping("/getEmployeeShop")
   public WeixinResult getEmployeeShop(@RequestBody BDLEmployee bdlEmployee) {
	  WeixinResult res = new WeixinResult();
	  BDLEmployee getEmployee = bdlEmployeeService.getEmployeeById(bdlEmployee);
	  if (getEmployee == null) {
		 res.setCode(CBPConstant.CODE_NULL_PARAM);
		 res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  }
	  else {
		 res.setCode(CBPConstant.CODE_SUCCESS);
		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 res.setData(getEmployee.getCompanycode()+","+getEmployee.getPhonenumber());
	  }
	  return res;
   }
   
   
   
   /**    
    * hr系统传过来的参数    
    *    
    * @param      
    */ 
   @RequestMapping("/receiveHRSData")
   public WeixinResult receiveHRSData(@RequestBody BDLEmployee bdlEmployee){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(bdlEmployee!=null){
		 int status = bdlEmployeeService.receiveHRSData(bdlEmployee);
		 if(status!=0){
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			return res;
		 }
	  }
	  return res;
   }
   
   
   



	@RequestMapping("/getEmployUserId")
	public WeixinResult getEmployUserId(@RequestBody BDLEmployee employee) {
		WeixinResult res = new WeixinResult();
		BDLEmployee getEmployee = bdlEmployeeService.getEmployeeById(employee);
		if (getEmployee == null) {
			res.setCode(CBPConstant.CODE_NULL_PARAM);
			res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
		}
		else {
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			String email = getEmployee.getEmail();
			String shop = getEmployee.getCompanycode();
			res.setData(email);
		}
		return res;
	}
   @RequestMapping("/getEmployUserIdAndShop")
   public WeixinResult getEmployUserIdAndShop(
		 @RequestBody BDLEmployee employee) {
	  WeixinResult res = new WeixinResult();
	  BDLEmployee getEmployee = bdlEmployeeService.getEmployeeById(employee);
	  if (getEmployee == null) {
		 res.setCode(CBPConstant.CODE_NULL_PARAM);
		 res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  }
	  else {
		 res.setCode(CBPConstant.CODE_SUCCESS);
		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 String email = getEmployee.getEmail();
		 String shop = getEmployee.getCompanycode();
		 res.setData(email + "," + shop);
	  }
	  return res;
   }


   @RequestMapping("/getEmployeeTitleId")
   public String getEmployeeTitleId(@RequestBody BDLEmployee employee) {
	  BDLEmployee getEmp = bdlEmployeeService.getEmployeeById(employee);
	  if (getEmp == null)
		 return null;
	  return String.valueOf(getEmp.getTitleid());
   }

   @RequestMapping("/getStoreIdByEmployeeId")
   public String getStoreIdByEmployeeId(@RequestBody BDLEmployee employee) {
	  String storeId = bdlEmployeeService.getStoreIdByEmployeeId(employee);
	  return storeId;
   }

	@RequestMapping("/getEmployeeByUserid")
	public String getEmployIdByUserId(@RequestBody BDLEmployee employee) {
		BDLEmployee getEmp = bdlEmployeeService
				.getEmployeeByUserId(employee.getEmail());
		if (getEmp == null)
			return null;
		BDLStore  getStore = storeService.getStoreByBrandId(getEmp.getCompanycode());
		if(getStore==null){
			return getEmp.getEmployeeid() + "," + getEmp.getCompanycode() + ","
					+ getEmp.getPhonenumber() + "," +
					getEmp.getEmployeename()+",集团";
		}
		return getEmp.getEmployeeid() + "," + getEmp.getCompanycode() + ","
				+ getEmp.getPhonenumber() + "," +
				getEmp.getEmployeename()+","+getStore.getStorecnshort();
	}
	@RequestMapping("/getEmployeeNameById")
	public String getEmployeeNameById(@RequestBody BDLEmployee employee){
		BDLEmployee getEmp = bdlEmployeeService.getEmployeeById
				(employee);
		if (getEmp == null){
			BDLUnEmployee getUnEmp=bdlUnEmployeeService.getEmployeeById(employee.getEmployeeid());
			if(getUnEmp==null){
				return "";
			}else{
               return  getUnEmp.getEmployeename()+"(离职)";
			}
		}
		return getEmp.getEmployeename();
	}
	@RequestMapping("/getEmployeePhoneNumberById")
	public String getEmployeePhoneNumberById(@RequestBody BDLEmployee
													 employee){
		BDLEmployee getEmp = bdlEmployeeService.getEmployeeById
				(employee);
		if (getEmp == null)
			return "";
		return String.valueOf(getEmp.getPhonenumber());
	}
	
	@RequestMapping("/getEmployeeListByEmployeeId")
	public List<BDLEmployee> getEmployeeListByEmployeeId(@RequestBody BDLEmployee employee){
	   List<BDLEmployee> list = null;
	   if(!StringUtil.isNullorEmpty(employee+"")){
		  list = bdlEmployeeService.getEmployeeListByEmployeeId(employee);
		  if(list.isEmpty() || list.size()==1){
			 System.out.println("++++++++++该成员为销售顾问++++++++++");
		  }else{
			 System.out.println("++++++++++该成员为非销售顾问++++++++++");
		  }
	   }
	   return list;
	}
	@RequestMapping("/getEmployeeByEmployeeId")
	public BDLEmployee getEmployeeByEmployeeId(@RequestBody BDLEmployee employee){
	   
	   BDLEmployee employee2 = bdlEmployeeService.getEmployeeById(employee);
	   
	   return employee2;
	}
	

	@RequestMapping("/getEmpoyeeByPhonenumber")
	public String getEmpoyeeByPhonenumber(@RequestBody BDLEmployee employee){
		BDLEmployee getEmployee=bdlEmployeeService.getEmpoyeeByPhonenumber(employee.getPhonenumber());
		if(getEmployee==null){
			return null;
		}else {
			JSONObject getJson=JSONObject.fromObject(getEmployee);
			return  getJson.toString();
		}

	}
	/**
	 * 判断是否门店人员
	 * @param employee
	 * @return
	 */
	@RequestMapping("/getEmployeeBrandFlag")
	public String getEmployeeBrandFlag(@RequestBody BDLEmployee employee){
		BDLEmployee getEmployee=bdlEmployeeService.getEmployeeBrandFlag(employee.getEmployeeid());
		if(getEmployee==null){
			return "0";
		}else {
			return  "1";
		}

	}

	/**
	 * 根据姓名查询EmployeeId
	 * @param employee
	 * @return
     */
	@RequestMapping("/getEmployeeIdByName")
	public String getEmployeeIdByName(@RequestBody BDLEmployee employee){
		BDLEmployee getEmployee=bdlEmployeeService.getEmployeeIdByName(employee.getEmployeename());
		if(getEmployee==null){
			return null;
		}else {
			return String.valueOf(getEmployee.getEmployeeid());
		}
	}
	
	
	
	
	@RequestMapping("/getEmployeeListByEmployee")
	public List<BDLEmployee> getEmployeeListByEmployee(@RequestBody CustomerMessageDTO employee){
	   if(!StringUtil.isNullorEmpty(employee.getEmployeeId())){
			 List<BDLEmployee> employeelist1 = bdlEmployeeService.getEmployeeListOnlyByEmployeeIdList(employee);
			 List<BDLEmployee> employeelist2 = new ArrayList<>();
			 for(BDLEmployee e:employeelist1){
				if(e.getTitleid()==CBPConstant.EmployeeTypeEnum.EMPLOYEE_SALES_CONSULTANT.getType()
					  ||e.getTitleid()==CBPConstant.EmployeeTypeEnum.EMPLOYEE_SALES_MANAGER.getType()){
				   employeelist2.add(e);
				}
			 }
			 if(employeelist1.isEmpty()){
				return new ArrayList<>();
			 }
			 return employeelist2;
		  }else{
			 return new ArrayList<>();
		  }
	}
	
	
	
	@RequestMapping("/getEmployeeListByBrandId")
	public List<BDLEmployee> getEmployeeListByBrandId(@RequestBody BDLEmployee employee){
	    return bdlEmployeeService.getEmployeeListByBrandId(employee);
			 
	}
	
	
	@RequestMapping("/getTitleIdByEmployeeId")
	public String getTitleIdByEmployeeId(@RequestBody BDLEmployee employee){
	   BDLEmployee employee2 = bdlEmployeeService.getEmployeeByEmployeeId(employee.getEmployeeid());
	   return String.valueOf(employee2.getTitleid());
	}

	/**
	 * 获取门店的承保顾问
	 * @param employee
	 * @return
     */
	@RequestMapping("getEmployUserIdAndEmployeeId")
	public String getEmployUserIdAndEmployeeId(@RequestBody BDLEmployee employee){
		employee.setTitleid(CBPConstant.EmployeeTypeEnum.EMPLOYEE_INSURANCE_CONSULTANT.getType());
		List<BDLEmployee> list=bdlEmployeeService.getEmployUserIdAndEmployeeId(employee);
		if(list.size()==0){
			return null;
		}else if(list.size()==1){
			return list.get(0).getEmployeeid()+","+list.get(0).getEmail();
		}else{//大于一随机获取一个
			Random random = new Random(list.size());
			int i = random.nextInt()-1;
			return list.get(i).getEmployeeid()+","+list.get(i).getEmail();
		}
	}
	@RequestMapping("/getCompanyCodeByEmployeeId")
	public WeixinResult getCompanyCodeByEmployeeId(@RequestBody BDLEmployee employee){
		
		return null;
	}
	/**
	 * 获取门店的精品
	 * @param employee
	 * @return
	 */
	@RequestMapping("getboutiqueEmployUserIdAndEmployeeId")
	public String getboutiqueEmployUserIdAndEmployeeId(@RequestBody BDLEmployee employee){
		employee.setTitleid(CBPConstant.EmployeeTypeEnum.EMPLOYEE_BOUTIQUE_CONSULTANT.getType());
		List<BDLEmployee> list=bdlEmployeeService.getEmployUserIdAndEmployeeId(employee);
		if(list.size()==0){
			return null;
		}else if(list.size()==1){
			return list.get(0).getEmployeeid()+","+list.get(0).getEmail();
		}else{//大于一随机获取一个
			Random random = new Random(list.size());
			int i = random.nextInt()-1;
			return list.get(i).getEmployeeid()+","+list.get(i).getEmail();
		}
	}
	@RequestMapping("/getTitleIdAndTitleName")
	public WeixinResult getTitleIdAndTitleName(@RequestBody BDLEmployee employee){
		WeixinResult res=new WeixinResult();
		EmployeeDTO getEmployee = bdlEmployeeService.getTitleIdAndTitleName(employee.getEmployeeid());
		if(getEmployee==null){
			res.setCode(CBPConstant.CODE_NULL_PARAM);
			res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			return res;
		}
		res.setCode(CBPConstant.CODE_SUCCESS);
		res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		res.setData(getEmployee);
		return res;

	}

	/**
	 * 获取门店的二手车顾问
	 * @param employee
	 * @return
	 */
	@RequestMapping("getSecendCarEmployUserIdAndEmployeeId")
	public String getSecendCarEmployUserIdAndEmployeeId(@RequestBody BDLEmployee employee){
		employee.setTitleid(CBPConstant.EmployeeTypeEnum.EMPLOYEE_SECENDCAR_CONSULTANT.getType());
		List<BDLEmployee> list=bdlEmployeeService.getEmployUserIdAndEmployeeId(employee);
		if(list.size()==0){
			return null;
		}else if(list.size()==1){
			return list.get(0).getEmployeeid()+","+list.get(0).getEmail();
		}else{//大于一随机获取一个
			Random random = new Random(list.size());
			int i = random.nextInt()-1;
			return list.get(i).getEmployeeid()+","+list.get(i).getEmail();
		}
	}
	
	
	/**
	 * 获取门店的二手车顾问
	 * @param employee
	 * @return
	 */
	@RequestMapping("getSecendCarFinanceEmployUserIdAndEmployeeId")
	public String getSecendCarFinanceEmployUserIdAndEmployeeId(@RequestBody BDLEmployee employee){
		employee.setTitleid(CBPConstant.EmployeeTypeEnum.EMPLOYEE_SECENDCAR_CONSULTANT.getType());
		List<BDLEmployee> list=bdlEmployeeService.getEmployUserIdAndEmployeeId(employee);
		if(list.size()==0){
			return null;
		}else if(list.size()==1){
			return list.get(0).getEmployeeid()+","+list.get(0).getEmail();
		}else{//大于一随机获取一个
			Random random = new Random(list.size());
			int i = random.nextInt()-1;
			return list.get(i).getEmployeeid()+","+list.get(i).getEmail();
		}
	}
	
	/**
	 * 获取门店的验车专员
	 * @param employee
	 * @return
	 */
	@RequestMapping("getCheckCarEmployUserIdAndEmployeeId")
	public String getCheckCarEmployUserIdAndEmployeeId(@RequestBody BDLEmployee employee){
		employee.setTitleid(CBPConstant.EmployeeTypeEnum.EMPLOYEE_CHECKCARPERSON_CONSULTANT.getType());
		List<BDLEmployee> list=bdlEmployeeService.getEmployUserIdAndEmployeeId(employee);
		if(list.size()==0){
			return null;
		}else if(list.size()==1){
			return list.get(0).getEmployeeid()+","+list.get(0).getEmail();
		}else{//大于一随机获取一个
			Random random = new Random(list.size());
			int i = random.nextInt()-1;
			return list.get(i).getEmployeeid()+","+list.get(i).getEmail();
		}
	}
	
	@RequestMapping("/getPhoneNumberByCompanycode")
	public String getPhoneNumberByCompanycode(@RequestBody BDLEmployee employee){
	   BDLEmployee bdlEmployee = bdlEmployeeService.getPhoneNumberByCompanycode(employee.getCompanycode());
	   if(bdlEmployee!=null){
		  return bdlEmployee.getPhonenumber().toString();
	   }
	   return null;
	}
}
