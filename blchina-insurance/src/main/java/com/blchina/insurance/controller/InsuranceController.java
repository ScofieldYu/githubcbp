/*   
 * @(#)InsuranceController.java       2018年2月2日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.blchina.insurance.dto.*;
import com.blchina.insurance.model.BDLInsuranceLimit;
import com.blchina.insurance.service.interfaces.InsuranceLimitService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.blchina.insurance.datamodel.CBPConstant;
import com.blchina.insurance.datamodel.weixin.WeixinResult;
import com.blchina.insurance.model.BDLInsurance;
import com.blchina.insurance.model.BDLInsuranceCompany;
import com.blchina.insurance.model.BDLInsuranceandCompany;
import com.blchina.insurance.service.interfaces.InsuranceCompanyService;
import com.blchina.insurance.service.interfaces.InsuranceService;
import com.blchina.insurance.service.interfaces.InsuranceandCompanyService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/insurance")
public class InsuranceController {
   
   @Autowired
   private InsuranceService insuranceService;
   @Autowired
   private InsuranceandCompanyService insuranceandCompanyService;
   @Autowired
   private Properties systemConfig;
   @Autowired
   private InsuranceCompanyService insuranceCompanyService;
    @Autowired
    private InsuranceLimitService insuranceLimitService;

    /**
     * 获取保险相关信息
     * @return
     */
    @RequestMapping("/getInsuranceInfo")
    public WeixinResult getInsuranceInfo(){
        WeixinResult res=new WeixinResult();
        InsuranceInfoDTO  insuranceInfo=new InsuranceInfoDTO();
        List<BDLInsurance> listInsurance= insuranceService.getAllInsurance();
        List<BDLInsuranceLimit> listLimit= insuranceLimitService.getAllLimit();
        List<BDLInsurance>   otherList=new ArrayList<BDLInsurance>();
        for(int i=0;i<listInsurance.size();i++){
            //第三个险
            if(listInsurance.get(i).getInsurancetype().equals(CBPConstant.InsuranceTypeEnum.THREEINSURANCE.getType())){
                InsuranceLimitDTO limitDTO=new InsuranceLimitDTO();
                limitDTO.setInsuranceid(listInsurance.get(i).getInsuranceid());
                limitDTO.setInsurancename(listInsurance.get(i).getInsurancename());
                limitDTO.setInsurancetype(listInsurance.get(i).getInsurancetype());
                List<BDLInsuranceLimit> listLimitThree=new ArrayList<BDLInsuranceLimit>();
                for(int j=0;j<listLimit.size();j++){
                    if(listLimit.get(j).getInsuranceid().intValue()==listInsurance.get(i).getInsuranceid()){
                        listLimitThree.add(listLimit.get(j));
                    }
                }
                limitDTO.setLimitList(listLimitThree);
                insuranceInfo.setThreeInsuranceList(limitDTO);
            }
            //车上人员险
            if(listInsurance.get(i).getInsurancetype().equals(CBPConstant.InsuranceTypeEnum.CARUSERINSURANCE.getType())){
                InsuranceLimitDTO limitDTO=new InsuranceLimitDTO();
                limitDTO.setInsuranceid(listInsurance.get(i).getInsuranceid());
                limitDTO.setInsurancename(listInsurance.get(i).getInsurancename());
                limitDTO.setInsurancetype(listInsurance.get(i).getInsurancetype());
                List<BDLInsuranceLimit> listLimitThree=new ArrayList<BDLInsuranceLimit>();
                for(int j=0;j<listLimit.size();j++){
                    if(listLimit.get(j).getInsuranceid().intValue()==listInsurance.get(i).getInsuranceid()){
                        listLimitThree.add(listLimit.get(j));
                    }
                }
                limitDTO.setLimitList(listLimitThree);
                insuranceInfo.setCarUserList(limitDTO);
            }
            //划痕险
            if(listInsurance.get(i).getInsurancetype().equals(CBPConstant.InsuranceTypeEnum.SCRATCHINSURANCE.getType())){
                InsuranceLimitDTO limitDTO=new InsuranceLimitDTO();
                limitDTO.setInsuranceid(listInsurance.get(i).getInsuranceid());
                limitDTO.setInsurancename(listInsurance.get(i).getInsurancename());
                limitDTO.setInsurancetype(listInsurance.get(i).getInsurancetype());
                List<BDLInsuranceLimit> listLimitThree=new ArrayList<BDLInsuranceLimit>();
                for(int j=0;j<listLimit.size();j++){
                    if(listLimit.get(j).getInsuranceid().intValue()==listInsurance.get(i).getInsuranceid()){
                        listLimitThree.add(listLimit.get(j));
                    }
                }
                limitDTO.setLimitList(listLimitThree);
                insuranceInfo.setScratchList(limitDTO);
            }
            //添加其他险种
            if(listInsurance.get(i).getInsurancetype().equals(CBPConstant.InsuranceTypeEnum.OTHERINSURANCE.getType())){
                otherList.add(listInsurance.get(i));
            }

        }
        insuranceInfo.setOtherList(otherList);
        //添加公司名称
        List<BDLInsuranceCompany> insuranceCompany = insuranceCompanyService.getInsuranceCompany();
        insuranceInfo.setListCompany(insuranceCompany);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setData(CBPConstant.MESSAGE_SUCCESS);
        res.setData(insuranceInfo);
       return res;
    }
   
   @RequestMapping("/getInsuranceListByInsuranceId")
   public List<BDLInsurance> getInsuranceListByInsuranceId(@RequestBody InsuranceDetailsDTO insuranceDetailsDTO){
	  List<BDLInsurance> list = insuranceService.getInsuranceListByInsuranceId(insuranceDetailsDTO.getInsuranceid());
	  if(list.isEmpty()){
		 return new ArrayList();
	  }else{
		 return list;
	  }
   }
   
   
   @RequestMapping("/selectListByInsuranceId")
   public List<BDLInsuranceCompany> selectListByInsuranceId(@RequestBody InsuranceDetailsDTO insuranceDetailsDTO){
	  List<BDLInsuranceCompany> list = insuranceCompanyService.selectListByInsuranceId(insuranceDetailsDTO.getInsuranceid());
	  if(list.isEmpty()){
		 return new ArrayList();
	  }else{
		 return list;
	  }
   }
    @RequestMapping("/getInsuranceCompany")
    public  List<BDLInsuranceCompany>  getInsuranceCompany(){
        List<BDLInsuranceCompany> list = insuranceCompanyService.getInsuranceCompany();
        if(list.isEmpty()){
            return new ArrayList();
        }else{
            return list;
        }
    }
    @RequestMapping("/getInsuranceList")
    public JSONObject getInsuranceList(@RequestBody InsuranceDTO insuranceDTO){
        List<InsuranceDTO> list = insuranceService.getAllInsuranceByCompany(insuranceDTO);
        JSONObject jsonArray= net.sf.json.JSONObject.fromObject(list);
        if(list.isEmpty()){
            return new JSONObject();
        }else{
            return jsonArray;
        }
    }
    @RequestMapping("/getInsuranceLimitList")
    public JSONArray getInsuranceLimitList(@RequestBody InsuranceDTO insuranceDTO){
        List<BDLInsuranceLimit> list = insuranceLimitService.getInsuranceLimitList(insuranceDTO);
        JSONArray jsonArray= net.sf.json.JSONArray.fromObject(list);
        if(list.isEmpty()){
            return new JSONArray();
        }else{
            return jsonArray;
        }
    }

}
