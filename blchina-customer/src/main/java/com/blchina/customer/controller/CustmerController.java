/*
 * @(#)CustmerController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.controller;

import com.blchina.customer.datamodel.CBPConstant;
import com.blchina.customer.datamodel.sap.SAPResponse;
import com.blchina.customer.datamodel.sap.SAPResult;
import com.blchina.customer.datamodel.weixin.WeixinResult;
import com.blchina.customer.dto.BDLFourCustomerListDTO;
import com.blchina.customer.dto.CardDTO;
import com.blchina.customer.dto.CustmerDTO;
import com.blchina.customer.dto.CustomerBaseDTO;
import com.blchina.customer.dto.CustomerInfoDTO;
import com.blchina.customer.dto.DocumentQueryModel;
import com.blchina.customer.dto.SapCustomerDTO;
import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.model.BDLCustomerInfo;
import com.blchina.customer.model.BDLCustomerStore;
import com.blchina.customer.model.BDLFourCustomer;
import com.blchina.customer.service.interfaces.BDLCustomerService;
import com.blchina.customer.dto.PagePojo;
import com.blchina.common.util.base64.Base64Util;
import com.blchina.common.util.blchina.BlchinaUtil;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.redis.RedisUtil;
import com.blchina.common.util.string.StringUtil;
import com.blchina.customer.service.interfaces.BDLCustomerStoreService;
import com.blchina.customer.service.interfaces.CustomerInfoService;
import com.github.pagehelper.PageHelper;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户操作controller
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
public class CustmerController {
    @Autowired
    protected BDLCustomerService bdlCustomerService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private Properties systemConfig;
    @Autowired
    private BDLCustomerStoreService css;
    /**
     * 更新客户接口
     *
     * @param customer
     * @return
     */
    @RequestMapping("/updateCustmer")
    public WeixinResult updateCustmer(@RequestBody CustmerDTO customer) {
        WeixinResult res = new WeixinResult();
        customer.setCustomername(null);
        res.setCode(CBPConstant.CODE_FAILURE);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        if (customer.getCustomerid()!=null) {
            int i=bdlCustomerService.updateCustomer(customer);
                if (i == 1) {
                    res = customerInfoService.updateCusomerInfo(customer.getCustomerid(), customer);
                    return res;
                }

        }
        return res;
    }

    /**
     *
     * 获取token接口
     * @param customer
     * @return
     */
    @RequestMapping("/getToken")
    public WeixinResult getToken(@RequestBody CustmerDTO customer) {
        WeixinResult res = new WeixinResult();
        String fromsecret = customer.getSecret();
        String secret = Base64Util.decode(fromsecret);
        System.out.println("getToken=======:"+customer.toString());
        if (StringUtil.isNullorEmpty(fromsecret)||customer.getBrandid()==null||customer.getOpenid()==null) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            return res;
        }
        String[] strs = secret.split("!!");
        if (strs.length != 2) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            return res;
        } else {
            String timestamp = strs[1];
            try {
                Long phonenumber = Long.valueOf(strs[0]);
                Long fromtime = Long.valueOf(timestamp);
                Date date = new Date();
                long time = date.getTime();
                Long  martchtime=Math.abs(time - fromtime);
                customer.setNickname(new String (Base64.getEncoder().encode(customer.getNickname().getBytes()),"utf-8"));
                if (martchtime - 2000000<0) {//2分钟有效
                    String idcardnum = customer.getIdcardnum();
                    customer.setPhonenumber(phonenumber);
                    if(customer.getNickname()!=null){
                        customer.setNickname(URLDecoder.decode(customer.getNickname(),"utf-8"));
                    }
                    if(!StringUtil.isNullorEmpty(customer.getEmail())&&customer.getEmail().equals("NaN")){
                        customer.setEmail(null);
                    }
                    BDLCustomer getCutomer=null;
                    BDLCustomerStore getStrore = css.getCustomerStoreByOpenid(customer.getOpenid());
                    if(getStrore==null){
                        if (StringUtil.isNullorEmpty(idcardnum)||idcardnum.equals("NaN")) {//无身份证根据手机号关联
                            customer.setIdcardnum(null);
                            if(getStrore==null){
                                getCutomer = bdlCustomerService.getCustmerByPhone(phonenumber);
                                if (getCutomer==null) {//没有手机号关联用户新增 如果有则更新记录
                                    bdlCustomerService.insertCustomer(customer);
                                }else{
                                    bdlCustomerService.updateCustmerByPhone(customer);
                                }
                                Integer customerid=null;
                                if(getCutomer==null){
                                    customerid=customer.getCustomerid();
                                }else {
                                    customerid=getCutomer.getCustomerid();
                                }
                                BDLCustomerStore bcs=new BDLCustomerStore();
                                bcs.setBrandid(customer.getBrandid());
                                bcs.setOpenid(customer.getOpenid());
                                bcs.setCustomerid(customerid);
                                css.addCustomerStore(bcs);
                            }

                        } else {
                            getCutomer= bdlCustomerService.getCustmerByCard(idcardnum);
                            if (getCutomer == null) {
                                getCutomer=  bdlCustomerService.getCustmerByPhone(phonenumber);
                                if(getCutomer==null){
                                    bdlCustomerService.insertCustomer(customer);
                                }else {
                                    bdlCustomerService.updateCustmerByCard(customer);
                                }
                            }else {
                                bdlCustomerService.updateCustmerByCard(customer);
                            }
                            BDLCustomerStore bcs=new BDLCustomerStore();
                            bcs.setBrandid(customer.getBrandid());
                            bcs.setOpenid(customer.getOpenid());
                            if(getCutomer==null){
                                bcs.setCustomerid(customer.getCustomerid());
                            }else {
                                bcs.setCustomerid(getCutomer.getCustomerid());
                            }
                            css.addCustomerStore(bcs);
                        }
                    }else{
                        Integer customerid = getStrore.getCustomerid();
                        customer.setCustomerid(customerid);
                    }
                    String token = EncodeUtil.getMD5For32(fromsecret);
                    JSONObject jsonOject = JSONObject.fromObject(customer);
                    redisUtil.setex(token, BlchinaUtil.TTL_WENXI_USER, jsonOject.toString());
                    res.setCode(CBPConstant.CODE_SUCCESS);
                    res.setData(token);
                    return res;
                } else {
                    res.setCode(CBPConstant.CODE_NULL_PARAM);
                    res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                    return res;
                }

            } catch (Exception e) {
                e.printStackTrace();
                res.setCode(CBPConstant.CODE_LOSE);
                res.setMessage(CBPConstant.MESSAGE_LOSE);
                return res;
            }
        }
    }

    /**
     * 更新客户接口
     *
     * @param customer
     * @return
     */
    @RequestMapping("/updateAuthCustmer")
    public WeixinResult updateAuthCustmer(@RequestBody CustmerDTO customer) {
        WeixinResult res = new WeixinResult();
        res.setCode(CBPConstant.CODE_TOKEN_INVALID);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        System.out.println("updateAuthCustmer=======:"+customer.toString());
        String idcardnum = customer.getIdcardnum();
        BDLCustomer getCustomer =null;
        Integer  fixCustomerId=null;
        if(!StringUtil.isNullorEmpty(idcardnum)){
            getCustomer=  bdlCustomerService.getCustmerByCard(idcardnum);
        }
        try {
            int i = 0;
            if(!StringUtil.isNullorEmpty(String.valueOf(customer.getCustomerid()))){
                if(getCustomer!=null&&getCustomer.getCustomerid().intValue()!=customer.getCustomerid().intValue()){
                    getCustomer.setIdcardnum("");
                    bdlCustomerService.updateCustomer(getCustomer);
                    fixCustomerId=getCustomer.getCustomerid();
                }
                i=bdlCustomerService.updateCustomer(customer);
            }else {
                if (getCustomer == null) {
                    getCustomer = bdlCustomerService.getCustmerByPhone(customer.getPhonenumber());
                    if(getCustomer==null){
                        BDLCustomer bdlCustomer=new BDLCustomer();
                        bdlCustomer.setIdcardnum(customer.getIdcardnum());
                        bdlCustomer.setPhonenumber(customer.getPhonenumber());
                        i= bdlCustomerService.insertCustomer(bdlCustomer);
                        getCustomer=bdlCustomer;
                    }else {
                        i = bdlCustomerService.updateCustmerByPhone(customer);
                    }

                } else {
                    i = bdlCustomerService.updateCustmerByCard(customer);
                }

            }
            if (i == 1) {
                BDLCustomerInfo customerInfo = new BDLCustomerInfo();
                customerInfo.setAccounttype(customer.getAccounttype());
                if(!StringUtil.isNullorEmpty(String.valueOf(customer.getCustomerid()))){
                    customerInfo.setCustomerid(customer.getCustomerid());
                    if(getCustomer!=null){
                        res.setData(getCustomer.getCustomerid());
                    }else {
                        res.setData(customer.getCustomerid());
                    }
                }else{
                    customerInfo.setCustomerid(getCustomer.getCustomerid());
                    res.setData(getCustomer.getCustomerid());
                }
                customerInfo.setOrganizecode(customer.getOrganizecode());
                customerInfo.setOrganizename(customer.getOrganizename());
                customerInfo.setSigncustomerid(customer.getSigncustomerid());
                customerInfo.setCustomercarduuid(customer.getCustomercarduuid());
                customerInfo.setBusilicenseuuid(customer.getBusilicenseuuid());
                //更新成功后插入customerinfo
                customerInfoService.insertOrUpdateCustomerInfo(customerInfo);
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                return res;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
        return res;
    }

    /**
     * 验证用户认证情况
     *
     * @param customer
     * @return
     */
    @RequestMapping("/authInfo")
    public WeixinResult authInfo(@RequestBody BDLCustomer customer) {
        WeixinResult res = new WeixinResult();
        String identitycardnum = customer.getIdcardnum();
        BDLCustomer custmerByCard = bdlCustomerService.getCustmerByCard(identitycardnum);
        if (custmerByCard == null) {
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
        } else {
            Integer customerid = custmerByCard.getCustomerid();
            BDLCustomerInfo customerinfo = new BDLCustomerInfo();
            customerinfo.setCustomerid(customerid);
            customerinfo.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
            Boolean flag = customerInfoService.doCustomerInfoByCustomerid(customerinfo);
            if (flag) {
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                return res;
            } else {
                res.setCode(CBPConstant.CODE_FAILURE);
                res.setMessage("超过认证次数,请联系相关人员");
                return res;
            }

        }
    }

    /**
     * 获取用户信息接口
     *
     * @param customer
     * @return
     */
    @RequestMapping("/getCustomerInfo")
    public WeixinResult getCustomerInfo(@RequestBody CustmerDTO customer) {
        WeixinResult res = new WeixinResult();
        String openid = customer.getOpenid();
        System.out.println("getCustomerInfo=======:"+customer.toString());
        if (openid == null) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        BDLCustomerStore customerStore = css.getCustomerStoreByOpenid(openid);
        if(customerStore==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        Integer customerid = customerStore.getCustomerid();
        BDLCustomer getcustomer = bdlCustomerService.findCustomerByPrimaryKey(customerid);
        List<BDLCustomerInfo> listinfo = customerInfoService.getCustomerInfo(customerid);
        CustomerInfoDTO customerDTO = new CustomerInfoDTO();
        customerDTO.setCustomer(getcustomer);
        customerDTO.setListinfo(listinfo);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(customerDTO);
        return res;
    }
    /**
     * 获取客户id通过openid
     *
     */
    @RequestMapping(value = "/getCustomerIdByOpenid")
      public String getCustomerIdByOpenid(@RequestBody CustmerDTO customer){
          BDLCustomerStore customerStore = css.getCustomerStoreByOpenid(customer.getOpenid());
          if(customerStore==null){
              return null;
          }else {
              return String.valueOf(customerStore.getCustomerid());
          }
      }
    /**
     * 获取用户下发接口
     *
     * @param sapCustomerDTO
     * @return
     */
    @RequestMapping(value = "/synCustomer")
    public SAPResult synCustomer(@RequestBody SapCustomerDTO sapCustomerDTO) {
        SAPResult sapRespone=new SAPResult();
        List<CustmerDTO> custmerDTOList = sapCustomerDTO.getRecords();
        List<SAPResponse> list=new ArrayList<SAPResponse>();
        for(int i=0;i<custmerDTOList.size();i++){
            CustmerDTO customer = custmerDTOList.get(i);
            SAPResult sapResult=new SAPResult();
            SAPResponse res = new SAPResponse();
            String idcardnum = customer.getIdcardnum();
            Long phonenumber = customer.getPhonenumber();
            customer.setPhonenumber(phonenumber);
            res.setSAP_ID(customer.getSapcustomerid());
            res.setBL_ID(customer.getSapcustomerid());
            if (StringUtil.isNullorEmpty(String.valueOf(phonenumber)) ||StringUtil.isNullorEmpty(idcardnum)) {
                res.setSAP_ID(customer.getSapcustomerid());
                res.setBL_ID(customer.getSapcustomerid());
                res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
                if(StringUtil.isNullorEmpty(String.valueOf(phonenumber))){
                    res.setDescription(customer.getSigncustomerid()+"用户手机号为空");
                }else {
                    res.setDescription(customer.getSigncustomerid()+"用户身份证为空");
                }
                sapResult.setRecords(res);
                list.add(res);
                continue;
            }

            BDLCustomer bcustomer = bdlCustomerService.getCustmerByCard(idcardnum);
            if (bcustomer == null) {
                bdlCustomerService.insertCustomer(customer);
            } else {
                bdlCustomerService.updateCustmerByCard(customer);
            }
            res.setStatus(CBPConstant.SAPResponseStatusEnum.SUCCESS.getType());
            res.setDescription("同步成功");
            list.add(res);
        }

     /*   Boolean flag = bdlCustomerService.synCustomer(customer);
        if(!flag){
            res.setSAP_ID(customer.getSigncustomerid());
            res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
            res.setDescription("同步合同管理系统失败");
            sapResult.setRecords(res);
            return sapResult;
        }*/
        sapRespone.setOppDate(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
        sapRespone.setDescriptioin("客户下发");
        sapRespone.setRecords(list);
        return sapRespone;
    }

    /**
     * 获取用户姓名
     *
     * @param customer
     * @return
     */
    @RequestMapping("/getCustomerNameAndOpenid")
    public String getCustomerName(@RequestBody CustmerDTO customer) {
        BDLCustomer customerByPrimaryKey = bdlCustomerService.findCustomerByPrimaryKey(customer.getCustomerid());
        BDLCustomerStore bcs=new BDLCustomerStore();
        bcs.setCustomerid(customer.getCustomerid());
        bcs.setBrandid(customer.getBrandid());
        BDLCustomerStore customerStore = css.getCustomerStore(bcs);
        if(customerStore==null){
            return customerByPrimaryKey.getCustomername()+",";
        }
        return customerByPrimaryKey.getCustomername()+","+customerStore.getOpenid();
    }

    /**
     * 获取用户id
     *
     * @param customer
     * @return
     */
    @RequestMapping("/getCustomerIdAndOpenIDByphone")
    public String getCustomerIdAndOpenIDByphone(@RequestBody CustmerDTO customer) {
        Long phonenumber = customer.getPhonenumber();
        if (phonenumber == null) {
            return null;
        }
        BDLCustomer getcustomer = bdlCustomerService.getCustmerByPhone(phonenumber);
        if (getcustomer == null) {
            return null;
        } else {
            return String.valueOf(getcustomer.getCustomerid()) + ",";// + getcustomer.getOpenid();
        }
    }

    /**
     * 获取用户id
     *
     * @param customer
     * @return
     */
    @RequestMapping("/getsignCustomerId")
    public String getsignCustomerId(@RequestBody CustmerDTO customer) {
        Integer customerid = customer.getCustomerid();
        BDLCustomerInfo info=new BDLCustomerInfo();
        info.setCustomerid(customerid);
        info.setOrganizecode(customer.getOrganizecode());
        return customerInfoService.getsignCustomerId(info);
    }
    /**
     * 获取用户id
     *
     * @param customer
     * @return
     */
    @RequestMapping("/getCustomerId")
    public String getCustomerId(@RequestBody CustmerDTO customer ) {
        String idcardnum = customer.getIdcardnum();
        Long phonenumber = customer.getPhonenumber();
        BDLCustomer getcustomer = bdlCustomerService.getCustmerByCard(idcardnum);
        if(getcustomer==null){
            getcustomer=bdlCustomerService.getCustmerByPhone(phonenumber);
            if(getcustomer==null){
                BDLCustomer bdlcustomer=new BDLCustomer();
                bdlcustomer.setPhonenumber(phonenumber);
                bdlcustomer.setIdcardnum(idcardnum);
                bdlcustomer.setSapcustomerid(customer.getSapcustomerid());
                bdlCustomerService.insertCustomer(bdlcustomer);
                return bdlcustomer.getCustomerid().toString();
            }
        }
        return getcustomer.getCustomerid().toString();
    }
    /**
     * 同步订单客户
     */
    @RequestMapping("/synOrderCustomer")
    public String synOrderCustomer(@RequestBody CustmerDTO custmerDTO){
        String idcardnum = custmerDTO.getIdcardnum();
        Long phonenumber = custmerDTO.getPhonenumber();
        String customerid=null;
        BDLCustomer getcustomer = bdlCustomerService.getCustmerByCard(idcardnum);
        if(getcustomer==null){
            getcustomer=bdlCustomerService.getCustmerByPhone(phonenumber);
            if(getcustomer==null){
                BDLCustomer bdlcustomer=new BDLCustomer();
                bdlcustomer.setPhonenumber(phonenumber);
                bdlcustomer.setIdcardnum(idcardnum);
                bdlcustomer.setCustomername(custmerDTO.getCustomername());
                bdlCustomerService.insertCustomer(bdlcustomer);
                getcustomer=bdlcustomer;
            }
        }else {
            String customername = getcustomer.getCustomername();
            if(StringUtil.isNullorEmpty(customername)){
                BDLCustomer bdlcustomer=new BDLCustomer();
                bdlcustomer.setPhonenumber(phonenumber);
                bdlcustomer.setIdcardnum(idcardnum);
                bdlcustomer.setCustomername(custmerDTO.getCustomername());
                bdlCustomerService.updateCustmerByCard(bdlcustomer);
            }
        }
        BDLCustomerInfo customerInfo=new BDLCustomerInfo();
        customerInfo.setCustomerid(getcustomer.getCustomerid());
        customerInfo.setOrganizename(custmerDTO.getOrganizename());
        customerInfo.setOrganizecode(custmerDTO.getOrganizecode());
        //同步customerinfo
        if(!StringUtil.isNullorEmpty(custmerDTO.getBuyeridcardnum())){
            if(custmerDTO.getBuyeridcardnum().equals(getcustomer.getIdcardnum())){
                customerInfo.setBuyeridcardnum(null);
            }else{
                customerInfo.setBuyername(custmerDTO.getBuyername());
                customerInfo.setBuyeridcardnum(custmerDTO.getBuyeridcardnum());
            }

        }
        if(StringUtil.isNullorEmpty(custmerDTO.getOrganizecode())){
            customerInfo.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
        }else {
            customerInfo.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PUBLIC.getType()));
        }
        customerInfoService.synOrderCustomer(customerInfo);
        customerid = String.valueOf(getcustomer.getCustomerid());
        BDLCustomerStore bcs=new BDLCustomerStore();
        bcs.setCustomerid(getcustomer.getCustomerid());
        bcs.setBrandid(custmerDTO.getBrandid());
        BDLCustomerStore customerStore = css.getCustomerStore(bcs);
        if(customerStore!=null){
            return customerid+","+customerStore.getOpenid();
        }
        return customerid;
    }

      /**
     * 获取客户姓名和sap客户编号
     */
    @RequestMapping("/getSapCustomerId")
    public String getSapCustomerId(@RequestBody BDLCustomer customer){
        Integer customerid = customer.getCustomerid();
        BDLCustomer getCustomer = bdlCustomerService.findCustomerByPrimaryKey(customerid);
        if(getCustomer==null){
            return null;
        }else {
            return getCustomer.getCustomername()+","+getCustomer.getSapcustomerid();
        }
    }


    @RequestMapping("/getCustomerByPhone")
    public WeixinResult  getCustomerByPhone(@RequestBody CustmerDTO customer){
        WeixinResult res=new WeixinResult();
        List<BDLCustomer> getCustomerList=bdlCustomerService.getCustmerByPhoneBase(customer.getPhonenumber());
        if(getCustomerList==null){
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
        }
        List<CustomerBaseDTO> list=new ArrayList<CustomerBaseDTO>();
        for(int i=0;i<getCustomerList.size();i++){
            CustomerBaseDTO base=new CustomerBaseDTO();
            base.setCustomer(getCustomerList.get(i));
            List<BDLCustomerInfo> customerInfo = customerInfoService.getCustomerInfoTotal(getCustomerList.get(i).getCustomerid());
            base.setListinfo(customerInfo);;
            list.add(base);
        }
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(list);
        return res;
    }

    /**
     * 通过手机号身份证查询用户
     * 获取用户信息
     *
     */
    @RequestMapping("/getCustomerBase")
    public WeixinResult  getCustomerBase(@RequestBody CustmerDTO customer){
        WeixinResult res=new WeixinResult();
        String idcardnum = customer.getIdcardnum();
        BDLCustomer getCustomer=null;
        if(!StringUtil.isNullorEmpty(idcardnum)){
            getCustomer=bdlCustomerService.getCustmerByCard(idcardnum);
        }else {
            getCustomer = bdlCustomerService.getCustmerByPhone(customer.getPhonenumber());
        }
        if(getCustomer==null){
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
        }
        List<BDLCustomerInfo> listinfo = customerInfoService.getCustomerInfoOnly(getCustomer.getCustomerid(),customer);
        CustomerInfoDTO customerDTO = new CustomerInfoDTO();
        customerDTO.setCustomer(getCustomer);
        if(listinfo==null||listinfo.size()==0){
         customerDTO.setListinfo(new ArrayList<BDLCustomerInfo>());
        }else {
            customerDTO.setListinfo(listinfo);
        }
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(customerDTO);
        return res;
    }
    
    
    
    @RequestMapping("/getCustomerListByCustomerId")
    public BDLCustomer getCustomerListByCustomerId(@RequestBody DocumentQueryModel dqm){
       	return bdlCustomerService.findCustomerByPrimaryKey(Integer.valueOf(dqm.getCustomerId()));
    }
    
    
    @RequestMapping("/insertOrUpdateFourCustomer")
    public String insertOrUpdateFourCustomer(@RequestBody BDLFourCustomerListDTO bdlFourCustomerListDTO){
       return bdlCustomerService.insertOrUpdateFourCustomer(bdlFourCustomerListDTO);
    }
    
    @RequestMapping("/selectFourListByCustomerid")
    public List<BDLFourCustomer> selectFourListByCustomerid(@RequestBody BDLFourCustomer bdlFourCustomer){
       return bdlCustomerService.selectFourListByCustomerid(bdlFourCustomer.getCustomerid());
    }
    
    @RequestMapping("/searchFourCustomer")
    public List<BDLFourCustomer> searchFourCustomer(@RequestBody CardDTO cardDTO){
       List<BDLFourCustomer> list = bdlCustomerService.searchFourCustomer(cardDTO);
       return list;
    }
}
