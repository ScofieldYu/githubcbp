/*
 * @(#)MonitorController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;


import com.alibaba.fastjson.JSONArray;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.*;
import com.blchina.cbp.model.BDLStore;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.blchina.BlchinaUtil;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.redis.RedisUtil;
import com.blchina.common.util.string.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 监控操作MonitorController
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Autowired
    private Properties systemConfig;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WxService wxService;
    @Autowired
    private RedisUtil redisUtil;
    @RequestMapping("/showMonitorDetail")
     public WeixinResult showMonitorDetail(@RequestBody MonitorSearchDTO monitorSearchDTO){
         WeixinResult res=new WeixinResult();
         String loginemployeeid = monitorSearchDTO.getLoginemployeeid();
        if(StringUtil.isNullorEmpty(loginemployeeid)||StringUtil.isNullorEmpty(monitorSearchDTO.getAccounttype())){//当前登录人id和账户类型必须传
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
         String employeename = monitorSearchDTO.getEmployeename();
         if(!StringUtil.isNullorEmpty(employeename)){
             try {
                 String  employeeid=HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeIdByName", "{\"employeename\":" + employeename + "}");
                 if(StringUtil.isNullorEmpty(employeeid)){
                     res.setCode(CBPConstant.CODE_SUCCESS);
                     res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                     return  res;
                 }else {
                     monitorSearchDTO.setEmployeeid(employeeid);
                 }

             } catch (Exception e) {
                 e.printStackTrace();
                 e.printStackTrace();
                 res.setCode(CBPConstant.CODE_FAILURE);
                 res.setMessage("获取员工信息失败");
                 return res;
             }
         }
         List<MonitorSearchDTO> list=null;
         if(monitorSearchDTO.getAccounttype().equals("1")){//是门店的查询某些
             list = orderService.searchOrderMonitorBrand(monitorSearchDTO);
         }else {
             list= orderService.searchOrderMonitorManager(monitorSearchDTO);
         }
         for(int i=0;i<list.size();i++){
             MonitorSearchDTO monitorSearch = list.get(i);
             String employeeid = monitorSearch.getEmployeeid();
             if(redisUtil.exists(employeeid)){
                 String employeeName = redisUtil.get(String.valueOf(employeeid));
                 list.get(i).setEmployeename(employeeName);
             }else {
                 String employeeName=null;
                 try {
                     employeeName = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeNameById", "{\"employeeid\":" + monitorSearch.getEmployeeid() + "}");
                     redisUtil.set(String.valueOf(employeeid),employeeName);
                     list.get(i).setEmployeename(employeeName);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }

             }
         }
         res.setCode(CBPConstant.CODE_SUCCESS);
         res.setMessage(CBPConstant.MESSAGE_SUCCESS);
         res.setData(list);
         return res;
     }

    @RequestMapping("/showOrderSignMonitor")
    public WeixinResult showOrderSignMonitor(@RequestBody SignMonitorDTO signMonitorDTO){
        WeixinResult res=new WeixinResult();
        JSONArray storeList=null;
        List<BDLStore> bdlStores=null;
        try {
            String result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getAllStore", "{}");
            storeList=JSONArray.parseArray(result);
            bdlStores = storeList.toJavaList(BDLStore.class);
        }catch (Exception e){
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage("获取门店信息失败");
            return res;
        }
        try {
            Object[] managerList = signMonitorDTO.getManagerList();
            List  getEmployeeList=new ArrayList<>();
            if(managerList.length!=0){
                for(int i=0;i<managerList.length;i++){
                    String response = HttpUtil.postbody(
                            systemConfig.getProperty("url.employee") + "/ee/getEmployeeListByEmployeeId",
                            "{\"employeeid\":" + managerList[i] + "}");
                    System.out.println("ES系统返回"+response);
                    List<BDLEmployee> employeeList = JSONArray.parseArray(response, BDLEmployee.class);
                    for(int j=0;j<employeeList.size();j++){
                        getEmployeeList.add(employeeList.get(j).getEmployeeid());
                    }
                }
                for(int k=0;k<signMonitorDTO.getEmployeelist().length;k++){
                    getEmployeeList.add(signMonitorDTO.getEmployeelist()[k]);
                }
                signMonitorDTO.setEmployeelist(getEmployeeList.toArray());
            }
        }catch (Exception e){
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage("获取下级员工失败");
            return res;
        }
        List<Object> listBrand=new ArrayList<Object>();
        if(signMonitorDTO.getBrandType().length!=0){
           for(int i=0;i<bdlStores.size();i++){
               for(int j=0;j<signMonitorDTO.getBrandType().length;j++){
                   if(String.valueOf(signMonitorDTO.getBrandType()[j]).equals(bdlStores.get(i).getBrandType())){
                       listBrand.add(bdlStores.get(i).getBrandid());
                   }
               }
           }
        }
        if(signMonitorDTO.getBrandIdList().length!=0){
            for(int i=0;i<signMonitorDTO.getBrandIdList().length;i++){
                listBrand.add(signMonitorDTO.getBrandIdList()[i]);
            }
        }
        signMonitorDTO.setBrandIdList(listBrand.toArray());
        List<SignMonitorDTO> list= orderService.showOrderSignMonitor(signMonitorDTO);
        List<SignMonitorDTO> listStroe=new ArrayList<SignMonitorDTO>();
        for(int i=0;i<list.size();i++){
            SignMonitorDTO getsignMonitorDTO = list.get(i);
            String employeeid = String.valueOf(getsignMonitorDTO.getEmployeeId());
            if(redisUtil.exists(employeeid)&&!StringUtil.isNullorEmpty(redisUtil.get(String.valueOf(employeeid)))){
                String employeeName = redisUtil.get(String.valueOf(employeeid));
                list.get(i).setEmployeeName(employeeName);
            }else {
                String employeeName=null;
                try {
                    employeeName = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeNameById", "{\"employeeid\":" + getsignMonitorDTO.getEmployeeId() + "}");
                    redisUtil.set(String.valueOf(employeeid),employeeName);
                    list.get(i).setEmployeeName(employeeName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Boolean flag=false;//判断是否已经有该门店，如果没有则新建一个该门店记录
            for(int j=0;j<listStroe.size();j++){
               if(getsignMonitorDTO.getBrandId().equals(listStroe.get(j).getBrandId())){
                   flag=true;
                   break;
               }
            }
            if(!flag){//没有添加记录
                SignMonitorDTO storeMonitor=new SignMonitorDTO();
                storeMonitor.setSignPecent(getsignMonitorDTO.getSignPecent());
                storeMonitor.setCustomerSetSum(getsignMonitorDTO.getCustomerSetSum());
                storeMonitor.setCloseOnePecent(getsignMonitorDTO.getCloseOnePecent());
                storeMonitor.setCloseFourPecent(getsignMonitorDTO.getCloseFourPecent());
                storeMonitor.setCloseThreePecent(getsignMonitorDTO.getCloseThreePecent());
                storeMonitor.setCloseTwoPecent(getsignMonitorDTO.getCloseTwoPecent());
                storeMonitor.setBrandId(getsignMonitorDTO.getBrandId());
                storeMonitor.setEmployeeSetPecent(getsignMonitorDTO.getEmployeeSetPecent());
                storeMonitor.setFocusPecent(getsignMonitorDTO.getFocusPecent());
                storeMonitor.setEmployeeSum(getsignMonitorDTO.getEmployeeSum());
                storeMonitor.setLogisticPecent(getsignMonitorDTO.getLogisticPecent());
                storeMonitor.setUnsignOnePecent(getsignMonitorDTO.getUnsignOnePecent());
                storeMonitor.setUnsignTwoPecent(getsignMonitorDTO.getUnsignTwoPecent());
                storeMonitor.setUnsignThreePecent(getsignMonitorDTO.getUnsignThreePecent());
                storeMonitor.setUnsignFourPecent(getsignMonitorDTO.getUnsignFourPecent());
                storeMonitor.setOrder("1");
                 for(int k=0;k<bdlStores.size();k++){
                     if(bdlStores.get(k).getBrandid().equals(storeMonitor.getBrandId())){
                         storeMonitor.setEmployeeName(bdlStores.get(k).getStorecnshort());
                         storeMonitor.setEmployeeId(null);
                          break;
                     }
                 }
                listStroe.add(storeMonitor);
            }else {
                  for(int p=0;p<listStroe.size();p++){
                      if(getsignMonitorDTO.getBrandId().equals(listStroe.get(p).getBrandId())){
                          if(new Double(listStroe.get(p).getCloseFourPecent()).intValue()==0&&new Double(listStroe.get(p).getCloseTwoPecent()).intValue()==0&&new Double(listStroe.get(p).getCloseThreePecent()).intValue()==0&&new Double(listStroe.get(p).getCloseOnePecent()).intValue()==0){
                              listStroe.get(p).setCloseOnePecent(getsignMonitorDTO.getCloseOnePecent());
                              listStroe.get(p).setCloseFourPecent(getsignMonitorDTO.getCloseFourPecent());
                              listStroe.get(p).setCloseThreePecent(getsignMonitorDTO.getCloseThreePecent());
                              listStroe.get(p).setCloseTwoPecent(getsignMonitorDTO.getCloseTwoPecent());
                          }else {
                              if(new Double(getsignMonitorDTO.getCloseFourPecent()).intValue()!=0&&new Double(getsignMonitorDTO.getCloseTwoPecent()).intValue()!=0&&new Double(getsignMonitorDTO.getCloseThreePecent()).intValue()!=0&&new Double(getsignMonitorDTO.getCloseOnePecent()).intValue()!=0){
                                  listStroe.get(p).setCloseFourPecent(String.valueOf(((new Double(listStroe.get(p).getCloseFourPecent())+new Double(getsignMonitorDTO.getCloseFourPecent())))/2));
                                  listStroe.get(p).setCloseOnePecent(String.valueOf(((new Double(listStroe.get(p).getCloseOnePecent()) + new Double(getsignMonitorDTO.getCloseOnePecent())))/2));
                                  listStroe.get(p).setCloseTwoPecent(String.valueOf(((new Double(listStroe.get(p).getCloseTwoPecent()) + new Double(getsignMonitorDTO.getCloseTwoPecent())))/2));
                                  listStroe.get(p).setCloseThreePecent(String.valueOf(((new Double(listStroe.get(p).getCloseThreePecent()) + new Double(getsignMonitorDTO.getCloseThreePecent())))/2));
                              }
                          }
                          if(new Double(listStroe.get(p).getUnsignFourPecent()).intValue()==0&&new Double(listStroe.get(p).getUnsignThreePecent()).intValue()==0&&new Double(listStroe.get(p).getUnsignTwoPecent()).intValue()==0&&new Double(listStroe.get(p).getUnsignOnePecent()).intValue()==0){
                              listStroe.get(p).setUnsignFourPecent(getsignMonitorDTO.getUnsignFourPecent());
                              listStroe.get(p).setUnsignThreePecent(getsignMonitorDTO.getUnsignThreePecent());
                              listStroe.get(p).setUnsignTwoPecent(getsignMonitorDTO.getUnsignTwoPecent());
                              listStroe.get(p).setUnsignOnePecent(getsignMonitorDTO.getUnsignOnePecent());
                          }else {
                              if(new Double(getsignMonitorDTO.getCloseFourPecent()).intValue()!=0&&new Double(getsignMonitorDTO.getCloseTwoPecent()).intValue()!=0&&new Double(getsignMonitorDTO.getCloseThreePecent()).intValue()!=0&&new Double(getsignMonitorDTO.getCloseOnePecent()).intValue()!=0){
                                  listStroe.get(p).setUnsignFourPecent(String.valueOf(((new Double(listStroe.get(p).getUnsignFourPecent())+new Double(getsignMonitorDTO.getUnsignFourPecent())))/2));
                                  listStroe.get(p).setUnsignThreePecent(String.valueOf(((new Double(listStroe.get(p).getUnsignThreePecent()) + new Double(getsignMonitorDTO.getUnsignThreePecent())))/2));
                                  listStroe.get(p).setUnsignTwoPecent(String.valueOf(((new Double(listStroe.get(p).getUnsignTwoPecent()) + new Double(getsignMonitorDTO.getUnsignTwoPecent())))/2));
                                  listStroe.get(p).setUnsignOnePecent(String.valueOf(((new Double(listStroe.get(p).getUnsignOnePecent()) + new Double(getsignMonitorDTO.getUnsignOnePecent())))/2));
                              }
                          }

                          String sum=String.valueOf(Integer.valueOf(listStroe.get(p).getEmployeeSum())+Integer.valueOf(getsignMonitorDTO.getEmployeeSum()));
                          listStroe.get(p).setEmployeeSetPecent(String.valueOf(((new Double(listStroe.get(p).getEmployeeSetPecent())*new Double(listStroe.get(p).getEmployeeSum()) + new Double(getsignMonitorDTO.getEmployeeSetPecent())*new Double(getsignMonitorDTO.getEmployeeSum())))/new Double(sum)));
                          listStroe.get(p).setFocusPecent(String.valueOf(((new Double(listStroe.get(p).getFocusPecent())*new Double(listStroe.get(p).getEmployeeSum()) + new Double(getsignMonitorDTO.getFocusPecent())*new Double(getsignMonitorDTO.getEmployeeSum())))/new Double(sum)));
                          listStroe.get(p).setSignPecent(String.valueOf(((new Double(listStroe.get(p).getSignPecent())*new Double(listStroe.get(p).getEmployeeSum()) + new Double(getsignMonitorDTO.getSignPecent())*new Double(getsignMonitorDTO.getEmployeeSum())))/new Double(sum)));
                          listStroe.get(p).setLogisticPecent(String.valueOf(((new Double(listStroe.get(p).getLogisticPecent())*new Double(listStroe.get(p).getEmployeeSum()) + new Double(getsignMonitorDTO.getLogisticPecent())*new Double(getsignMonitorDTO.getEmployeeSum())))/new Double(sum)));
                          listStroe.get(p).setCustomerSetSum(String.valueOf(((new Double(listStroe.get(p).getCustomerSetSum())*new Double(listStroe.get(p).getEmployeeSum()) + new Double(getsignMonitorDTO.getCustomerSetSum())*new Double(getsignMonitorDTO.getEmployeeSum())))/new Double(sum)));
                          listStroe.get(p).setEmployeeSum(sum);

                          break;
                      }
                  }

            }


        }
        for(int i=0;i<listStroe.size();i++){
            list.add(listStroe.get(i));
        }
        for(int i=0;i<list.size();i++){
            list.get(i).setCloseFourPecent(new Double(new Double(list.get(i).getCloseFourPecent())*100).intValue()+"%");
            list.get(i).setCloseOnePecent(new Double(new Double(list.get(i).getCloseOnePecent())*100).intValue()+"%");
            list.get(i).setCloseTwoPecent(new Double(new Double(list.get(i).getCloseTwoPecent())*100).intValue()+"%");
            list.get(i).setCloseThreePecent(new Double(new Double(list.get(i).getCloseThreePecent())*100).intValue()+"%");
            list.get(i).setUnsignFourPecent(new Double(new Double(list.get(i).getUnsignFourPecent())*100).intValue()+"%");
            list.get(i).setUnsignOnePecent(new Double(new Double(list.get(i).getUnsignOnePecent())*100).intValue()+"%");
            list.get(i).setUnsignTwoPecent(new Double(new Double(list.get(i).getUnsignTwoPecent())*100).intValue()+"%");
            list.get(i).setUnsignThreePecent(new Double(new Double(list.get(i).getUnsignThreePecent())*100).intValue()+"%");
            list.get(i).setCustomerSetSum(new Double(new Double(list.get(i).getCustomerSetSum())*100).intValue()+"%");
            list.get(i).setEmployeeSetPecent(new Double(new Double(list.get(i).getEmployeeSetPecent())*100).intValue()+"%");
            list.get(i).setFocusPecent(new Double(new Double(list.get(i).getFocusPecent())*100).intValue()+"%");
            list.get(i).setSignPecent(new Double(new Double(list.get(i).getSignPecent())*100).intValue()+"%");
            list.get(i).setLogisticPecent(new Double(new Double(list.get(i).getLogisticPecent())*100).intValue()+"%");
        }
        Collections.sort(list);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(list);
        return res;
    }
    /**
     * 根据code获取员工id
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping("/getEmployeeidByCodeBase")
    public WeixinResult getEmployeeidByCodeBase(@RequestBody JSONObject jsonObject){
        WeixinResult res=new WeixinResult();
        String code = (String)jsonObject.get("code");
        if(code==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_LOSE);
            return res;
        }
        String employeeByCode = wxService.getEmployeeByCode(code);
        if(employeeByCode==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage("没有该员工信息");
            return res;
        }else {
            String[] getEmployeeStr=employeeByCode.split(",");
            Boolean  isbrand=true;
            String loginemployeeid=getEmployeeStr[0];
            //判断该员工是否门店
            if(redisUtil.exists(loginemployeeid+"isbrand")){//是门店
                String flag=redisUtil.get(loginemployeeid+"isbrand");
                if(flag.equals("Y")){
                    isbrand=true;
                }else {
                    isbrand=false;
                }
            }else {
                try {
                    String result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeBrandFlag", "{\"employeeid\":" + loginemployeeid + "}");
                    if(result.equals("0")){
                        redisUtil.set(loginemployeeid+"isbrand","N");
                        isbrand=false;
                    }else {
                        redisUtil.set(loginemployeeid+"isbrand","Y");
                        isbrand=true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    res.setCode(CBPConstant.CODE_FAILURE);
                    res.setMessage("获取员工信息失败");
                    return res;
                }

            }
            EmployeeDTO employeeDTO=new EmployeeDTO();
            String token = EncodeUtil.getMD5For32(loginemployeeid);
            employeeDTO.setEmployeeid(loginemployeeid);
            if(isbrand){
                employeeDTO.setAccouttype("1");
            }else {
                employeeDTO.setAccouttype("2");
            }
            employeeDTO.setToken(token);
            res.setData(employeeDTO);
            res.setCode(CBPConstant.CODE_SUCCESS);
            redisUtil.setex(token, BlchinaUtil.TTL_PAD_USER,loginemployeeid);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
        }
    }
    
    
    @RequestMapping("/getPaperCosts")
    public WeixinResult getPaperCosts(@RequestBody SignMonitorDTO smdto){
       WeixinResult result = new WeixinResult();
 	   result.setCode(CBPConstant.CODE_NULL_PARAM);
       result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
       if(smdto!=null && smdto.getBrandIdList().length!=0){
    	  List<PaperCostsDTO> smdtoList = orderService.getPaperCosts(smdto);
    	  if(smdtoList!=null){
    		 result.setCode(CBPConstant.CODE_SUCCESS);
    		 result.setMessage(CBPConstant.MESSAGE_SUCCESS);
    		 result.setData(smdtoList);
    	  }
       }
       return result;
    }
    
    @RequestMapping("/getPaperCostsForPad")
    public WeixinResult getPaperCostsForPad(@RequestBody SignMonitorDTO smdto){
       WeixinResult result = new WeixinResult();
 	   result.setCode(CBPConstant.CODE_NULL_PARAM);
       result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
       if(smdto!=null && smdto.getBrandIdList().length!=0){
    	  List<BDLBrandStore<BrandStore>> smdtoList = orderService.getPaperCostsForPad(smdto);
    	  if(smdtoList!=null){
    		 result.setCode(CBPConstant.CODE_SUCCESS);
    		 result.setMessage(CBPConstant.MESSAGE_SUCCESS);
    		 result.setData(smdtoList);
    	  }
       }
       return result;
    }
    
}
