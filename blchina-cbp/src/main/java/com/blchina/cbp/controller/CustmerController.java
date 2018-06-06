/*
 * @(#)CustmerController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.*;
import com.blchina.cbp.model.BDLCustomer;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.AuthService;
import com.blchina.cbp.service.interfaces.ContractManageService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.util.UploadUtil;
import com.blchina.common.util.file.FileUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.redis.RedisUtil;
import com.blchina.common.util.string.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户操作controller
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/auth")
public class CustmerController {
    @Autowired
    protected Properties systemConfig;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AuthService authService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ContractManageService contractManageService;

    /**
     * 客户认证接口
     *
     * @param customer
     * @return
     */
    @RequestMapping("/selfauth")
    public WeixinResult custmerAuth(@RequestBody CustmerDTO customer) {
        WeixinResult res = new WeixinResult();
        res.setCode(CBPConstant.CODE_NULL_PARAM);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        String identitycardnum = customer.getIdcardnum();
        Long phonenumber = customer.getPhonenumber();
        String customername = customer.getCustomername();
        customer.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
        if (!StringUtil.isNullorEmpty(identitycardnum) && !StringUtil.isNullorEmpty(customername) && !StringUtil.isNullorEmpty(String.valueOf(phonenumber))) {
            JSONObject jsonOject =new JSONObject();
            jsonOject.put("phonenumber",phonenumber);
            jsonOject.put("customername",customername);
            jsonOject.put("idcardnum",identitycardnum);
            jsonOject.put("accounttype",String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
            if(!StringUtil.isNullorEmpty(String.valueOf(customer.getCustomerid()))){
                jsonOject.put("customerid",customer.getCustomerid());
            }

            try {
                //去customer校验验证次数
                String getInfo = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/authInfo", jsonOject.toString());
                JSONObject getInfoJSON = JSONObject.fromObject(getInfo);
                res = (WeixinResult) JSONObject.toBean(getInfoJSON, WeixinResult.class);
                if (!res.getCode().equals(CBPConstant.CODE_SUCCESS)) {
                    return res;
                }
                //去法大大认证
                WeixinResult getRes = authService.seltauth(customer);
                String code = getRes.getCode();
                JSONObject fadadaJson = JSONObject.fromObject(getRes.getData());
                if (code.equals(CBPConstant.CODE_SUCCESS)) {//认证通过更新用户信息
                    String customer_id = (String) fadadaJson.get("customer_id");
                    String customercarduuid = customer.getCustomercarduuid();
                    if(!StringUtil.isNullorEmpty(customercarduuid)){
                        if(customercarduuid.equals("1")){//是1不传
                        }else {
                            File file= FileUtil.GenerateImage(customercarduuid,systemConfig.getProperty("file.url.image")+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".png","image");
                            List<File> cardList=new ArrayList<File>();
                            cardList.add(file);
                            UploadDTO uploadDTO=new UploadDTO();
                            uploadDTO.setIdcardnum(customer.getIdcardnum());
                            uploadDTO.setType(CBPConstant.FileEnum.CUSTOMERIDCARD.getType());
                            String result = UploadUtil.uploadByFile(systemConfig.getProperty("contract.url")+"upload/fileUpload.do",cardList,new ArrayList<String>(),uploadDTO,null,null,systemConfig.getProperty("fadada.appSecret"));
                            JSONObject getJson=JSONObject.fromObject(result);
                            if(String.valueOf(getJson.get("code")).equals("200")){
                                getJson.remove("data");
                                FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
                                if(String.valueOf(fileuuidList.getCode()).equals("200")){
                                    String uuid=fileuuidList.getObj()[0].getUuid();
                                    jsonOject.put("customercarduuid",uuid);
                                }

                            }
                        }
                    }else {
                        jsonOject.put("customercarduuid","");
                    }
                    jsonOject.put("signcustomerid", customer_id);
                    String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/updateAuthCustmer", jsonOject.toString());
                    JSONObject resObject = JSONObject.fromObject(result);
                    res = (WeixinResult) JSONObject.toBean(resObject, WeixinResult.class);
                    if(!StringUtil.isNullorEmpty(String.valueOf(customer.getCustomerid()))){
                    Integer  getCustomerId=Integer.valueOf(String.valueOf(res.getData()));
                        if(getCustomerId.intValue()!=customer.getCustomerid().intValue()){//不相等需要更新订单表Customerid；
                            res.setData(customer.getCustomerid());
                            List<CBPOrder> orderList = orderService.getOrderList(String.valueOf(getCustomerId));
                            for(int i=0;i<orderList.size();i++){
                                orderList.get(i).setCustomerid(customer.getCustomerid());
                                orderService.updateOrderBySapId(orderList.get(i));
                            }
                        }
                    }
                } else {
                    res.setCode(CBPConstant.CODE_FAILURE);
                    res.setMessage("认证错误");
                }
            } catch (Exception e) {
                return res;
            }
        }
        return res;
    }

    /**
     * 客户认证接口
     *
     * @param customer
     * @return
     */
    @RequestMapping("/selfupdate")
    public WeixinResult selfupdate(@RequestBody CustmerDTO customer) {
        WeixinResult res = new WeixinResult();
        res.setCode(CBPConstant.CODE_NULL_PARAM);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        String identitycardnum = customer.getIdcardnum();
        Long phonenumber = customer.getPhonenumber();
        String customername = customer.getCustomername();
        customer.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
        if (!StringUtil.isNullorEmpty(identitycardnum) && !StringUtil.isNullorEmpty(customername) && !StringUtil.isNullorEmpty(String.valueOf(phonenumber))) {
            JSONObject jsonOject =new JSONObject();
            jsonOject.put("phonenumber",phonenumber);
            jsonOject.put("customername",customername);
            jsonOject.put("idcardnum",identitycardnum);
            jsonOject.put("accounttype",String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
            try {
                String buyeridcarduuid = customer.getBuyeridcarduuid();
                if(!StringUtil.isNullorEmpty(buyeridcarduuid)){
                    if(buyeridcarduuid.equals("1")){//是1不传
                    }else {
                        File file= FileUtil.GenerateImage(buyeridcarduuid,systemConfig.getProperty("file.url.image")+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".png","image");
                        List<File> cardList=new ArrayList<File>();
                        cardList.add(file);
                        UploadDTO uploadDTO=new UploadDTO();
                        uploadDTO.setIdcardnum(customer.getIdcardnum());
                        uploadDTO.setType(CBPConstant.FileEnum.CUSTOMERIDCARD.getType());
                        String result = UploadUtil.uploadByFile(systemConfig.getProperty("contract.url")+"upload/fileUpload.do",cardList,new ArrayList<String>(),uploadDTO,null,null,systemConfig.getProperty("fadada.appSecret"));
                        JSONObject getJson=JSONObject.fromObject(result);
                        if(String.valueOf(getJson.get("code")).equals("200")){
                            getJson.remove("data");
                            FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
                            if(String.valueOf(fileuuidList.getCode()).equals("200")){
                                String uuid=fileuuidList.getObj()[0].getUuid();
                                jsonOject.put("customercarduuid",uuid);
                            }

                        }
                    }
                }else {
                    jsonOject.put("customercarduuid","");
                }
                    String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/updateAuthCustmer", jsonOject.toString());
                    JSONObject resObject = JSONObject.fromObject(result);
                    res = (WeixinResult) JSONObject.toBean(resObject, WeixinResult.class);
            } catch (Exception e) {
                return res;
            }
        }
        return res;
    }

    /**
     * 公司客户认证接口
     *
     * @param customer
     * @return
     */
    @RequestMapping("/companyauth")
    public WeixinResult companyauth(@RequestBody CustmerDTO customer) {
        WeixinResult res = new WeixinResult();
        res.setCode(CBPConstant.CODE_NULL_PARAM);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        String organizename = customer.getOrganizename();
        String organizeid = customer.getOrganizecode();
        Long phonenumber = customer.getPhonenumber();
        customer.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PUBLIC.getType()));
        if (String.valueOf(phonenumber) == null) {//公户认证也需要参数手机号通过手机号更新客户信息
            return res;
        }
        if (!StringUtil.isNullorEmpty(organizeid) && !StringUtil.isNullorEmpty(organizename)) {

            try {
                //去法大大认证
                 WeixinResult getRes = authService.companyauth(customer);
                String code = getRes.getCode();
                if (code.equals(CBPConstant.CODE_SUCCESS)) {//认证通过更新用户信息
                    String customer_id = (String)getRes.getData();
                    JSONObject jsonOject = new JSONObject();
                    String customercarduuid = customer.getCustomercarduuid();
                    if(!StringUtil.isNullorEmpty(customercarduuid)){
                        if(customercarduuid.equals("1")){//是1不传
                        }else {
                            File file= FileUtil.GenerateImage(customercarduuid,systemConfig.getProperty("file.url.image")+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".png","image");
                            List<File> cardList=new ArrayList<File>();
                            cardList.add(file);
                            UploadDTO uploadDTO=new UploadDTO();
                            uploadDTO.setIdcardnum(customer.getIdcardnum());
                            uploadDTO.setType(CBPConstant.FileEnum.CUSTOMERIDCARD.getType());
                            String result = UploadUtil.uploadByFile(systemConfig.getProperty("contract.url")+"upload/fileUpload.do",cardList,new ArrayList<String>(),uploadDTO,null,null,systemConfig.getProperty("fadada.appSecret"));
                            JSONObject getJson=JSONObject.fromObject(result);
                            if(String.valueOf(getJson.get("code")).equals("200")){
                                getJson.remove("data");
                                FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
                                if(String.valueOf(fileuuidList.getCode()).equals("200")){
                                    String uuid=fileuuidList.getObj()[0].getUuid();
                                    jsonOject.put("customercarduuid",uuid);
                                }

                            }
                        }
                    }else {
                        jsonOject.put("customercarduuid","");
                    }
                    String busilicenseuuid = customer.getBusilicenseuuid();
                    if(!StringUtil.isNullorEmpty(busilicenseuuid)){
                        if(busilicenseuuid.equals("1")){//是1不传
                        }else {
                            File file= FileUtil.GenerateImage(busilicenseuuid,systemConfig.getProperty("file.url.image")+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".png","image");
                            List<File> busiList=new ArrayList<File>();
                            busiList.add(file);
                            UploadDTO uploadDTO=new UploadDTO();
                            uploadDTO.setIdcardnum(customer.getIdcardnum());
                            uploadDTO.setType(CBPConstant.FileEnum.BUSINLICENSE.getType());
                            String result = UploadUtil.uploadByFile(systemConfig.getProperty("contract.url")+"upload/fileUpload.do",busiList,new ArrayList<String>(),uploadDTO,null,null,systemConfig.getProperty("fadada.appSecret"));
                            JSONObject getJson=JSONObject.fromObject(result);
                            if(String.valueOf(getJson.get("code")).equals("200")){
                                getJson.remove("data");
                                FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
                                if(String.valueOf(fileuuidList.getCode()).equals("200")){
                                    String uuid=fileuuidList.getObj()[0].getUuid();
                                    jsonOject.put("busilicenseuuid",uuid);
                                }
                            }
                        }
                    }else {
                        jsonOject.put("busilicenseuuid","");
                    }
                    jsonOject.put("organizename",organizename);
                    jsonOject.put("phonenumber",phonenumber);
                    jsonOject.put("organizecode",organizeid);
                    jsonOject.put("accounttype",String.valueOf(CBPConstant.CAccountTypeEnum.PUBLIC.getType()));
                    jsonOject.put("signcustomerid", customer_id);
                    jsonOject.put("idcardnum",customer.getIdcardnum());
                    jsonOject.put("customername",customer.getCustomername());
                    String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/updateAuthCustmer", jsonOject.toString());
                    JSONObject resObject = JSONObject.fromObject(result);
                    res = (WeixinResult) JSONObject.toBean(resObject, WeixinResult.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return res;
            }

        }
        return res;

    }


    /**
     * 更新客户接口
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping("/updateCustmer")
    public WeixinResult updateCustmer(@RequestBody JSONObject jsonObject) {
        WeixinResult res = new WeixinResult();
        String result = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/updateCustmer", jsonObject.toString());
            JSONObject getJson = JSONObject.fromObject(result);
            res = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
            if(res.getCode().equals(CBPConstant.CODE_SUCCESS)){
                CustmerDTO custmerDTO=(CustmerDTO)JSONObject.toBean(jsonObject,CustmerDTO.class);
                    res.setCode(CBPConstant.CODE_SUCCESS);
                    res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
        }
        return res;
    }

    /**
     * 获取token接口
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping("/getToken")
    public WeixinResult getToken(@RequestBody JSONObject jsonObject) {
        WeixinResult res = new WeixinResult();
        String result = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getToken", jsonObject.toString());
            jsonObject = JSONObject.fromObject(result);
            res = (WeixinResult) JSONObject.toBean(jsonObject, WeixinResult.class);
        } catch (Exception e) {
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
        }
        return res;
    }

    /**
     * 获取用户信息接口
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping("/getCustomerInfo")
    public String getCustomerInfo(@RequestBody JSONObject jsonObject) {
        //去customer校验验证次数

        try {
            String getInfo = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerInfo", jsonObject.toString());
            return getInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("/getCustomerByEmployeeId")
    public WeixinResult getCustomerByEmployeeId(@RequestBody JSONObject jsonObject) {
        WeixinResult res = new WeixinResult();
        Integer employeeid = Integer.valueOf((String) jsonObject.get("employeeid"));
        String type = (String) jsonObject.get("type");
        List<OrderDTO> list=null;
        if(type!=null&&type.equals("1")){
            list = orderService.getCustomerByEmployeeIdBase(employeeid);
        }else {
            list = orderService.getCustomerByEmployeeId(employeeid);
        }
        List<CustomerOrderDTO> resultList=new ArrayList<CustomerOrderDTO>();
        for(int i=0;i<list.size();i++){
            Boolean flag=true;
            OrderDTO orderDTO = list.get(i);
            CBPOrder order=(CBPOrder)orderDTO;
            String phonenumber = orderDTO.getPhonenumber();
            for(int j=0;j<resultList.size();j++){
               if(phonenumber.equals(resultList.get(j).getPhonenumber())){
                  resultList.get(j).getOrderlist().add(order);
                   flag=false;
                   break;
               }
            }
            //手机号不同新增result
            if(flag){
                CustomerOrderDTO cod=new CustomerOrderDTO();
                cod.setCustomername(orderDTO.getCustomername());
                cod.setPhonenumber(phonenumber);
                List<CBPOrder>  orderlist=new ArrayList<CBPOrder>();
                orderlist.add(order);
                cod.setOrderlist(orderlist);
                resultList.add(cod);
            }

        }
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(resultList);
        return res;
    }

    /**
     * 获取客户signcustomerid
     * @param customer
     * @return
     * @throws Exception
     */
    @RequestMapping("/getSignCustomerId")
    public WeixinResult getSignCustomerId(@RequestBody CustmerDTO customer) throws Exception {
        WeixinResult res=new WeixinResult();
        Integer customerid = customer.getCustomerid();
        if(customerid==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return  res;
        }
        String signcustomerid=null;
        if(customer.getOrganizecode()==null){
            signcustomerid = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getsignCustomerId", "{\"customerid\":" + customerid +"}");
        }else {
            signcustomerid = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getsignCustomerId", "{\"customerid\":" + customerid +",\"organizecode\":\""+customer.getOrganizecode()+ "\"}");
        }
        if(signcustomerid==null){
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage("请先认证个人信息");
        }else{
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            res.setData(signcustomerid);
        }
        return res;
    }

    /**
     * 通过手机号
     * 获取用户信息
     *
     */
    @RequestMapping("/getCustomerByPhone")
    public String getCustomerByPhone(@RequestBody JSONObject jsonObject) throws Exception {
        String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerByPhone", jsonObject.toString());
        return result;

    }
    /**
     * 通过手机号身份证查询用户
     * 获取用户信息
     *
     */
    @RequestMapping("/getCustomerBase")
    public String getCustomerBase(@RequestBody JSONObject jsonObject) throws Exception {
        String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerBase", jsonObject.toString());
        return result;

    }

}
