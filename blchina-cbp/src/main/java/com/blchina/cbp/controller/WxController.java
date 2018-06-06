/*
 * @(#)WxController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.SapOrderDTO;
import com.blchina.cbp.model.*;
import com.blchina.cbp.service.interfaces.*;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.http.HttpsConnection;
import com.blchina.common.util.redis.RedisUtil;
import com.blchina.common.util.string.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Weixin controller
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/wx")
public class WxController {
    @Autowired
    private WxService wxService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TimeConfService timeConfService;
    @Autowired
    private TimeTemplateService timeTemplateService;
    @Autowired
    private Properties  systemConfig;
    @Autowired
    private MessageService messageService;
    @Autowired
    private  LogisticsService logisticsService;

    /**
     * 预约通知
     * @param saporderDTO
     * @return
     */
    @RequestMapping("/pushCarStart")
    public SAPResult pushCarStart(@RequestBody SapOrderDTO saporderDTO) {
        SAPResult sapResult=new SAPResult();
        SAPResponse res=new SAPResponse();
        String saporderid = saporderDTO.getRecords().getSaporderid();
        if(saporderid==null){
            res.setSAP_ID(saporderid);
            res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
            res.setDescription("参数不正确");
            sapResult.setRecords(res);
            return sapResult;
        }
        CBPOrder order = orderService.getOrderBySapId(saporderid);
        if(order==null){
            res.setSAP_ID(saporderid);
            res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
            res.setDescription("参数不正确");
            sapResult.setRecords(res);
            return sapResult;
        }
        if(!StringUtil.isNullorEmpty(order.getDelivertime())){
            res.setSAP_ID(saporderid);
            res.setStatus(CBPConstant.SAPResponseStatusEnum.SUCCESS.getType());
            res.setDescription("成功");
            sapResult.setRecords(res);
            return sapResult;
        }
        Integer employeeid = order.getEmployeeid();
        try {
            String result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserIdAndShop", "{\"employeeid\":" + employeeid + "}");
            JSONObject getJson=JSONObject.fromObject(result);
            WeixinResult getRes=(WeixinResult) JSONObject.toBean(getJson,WeixinResult.class);
            String code = getRes.getCode();
            if(code.equals(CBPConstant.CODE_SUCCESS)){
                String  data = (String)getRes.getData();
                String[] split = data.split(",");
                String userid=split[0];
                String getshop=split[1];
                Integer shop = Integer.valueOf(getshop);
                CBPTimeConf shopConfig = timeConfService.getShopConfig(shop);
                String day=CBPConstant.SHOP_DAY;
                if(shopConfig!=null){
                    day=String.valueOf(shopConfig.getDaynum());
                }
                Boolean flag = wxService.pushCarStart(userid, order.getOrderid(),day,order.getSaporderid(),employeeid);
                if(flag){
                        order.setDelivertime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        orderService.updateOrderBySapId(order);
                        //创建消息
                        CBPDeliverMessage cbpDeliverMessage=new CBPDeliverMessage();
                        cbpDeliverMessage.setOrderid(order.getOrderid());
                        cbpDeliverMessage.setStatus(CBPConstant.MessageStatusEnum.UNSET.getType());
                        cbpDeliverMessage.setMessagetype(CBPConstant.MessageTypeEnum.SALE_FIVEINTERNAL.getType());
                        messageService.saveOrUpdateMessage(cbpDeliverMessage);
                        res.setSAP_ID(saporderid);
                        res.setBL_ID(saporderid);
                        res.setStatus(CBPConstant.SAPResponseStatusEnum.SUCCESS.getType());
                        res.setDescription("成功");
                        sapResult.setRecords(res);
                        return sapResult;
                }
            }
            res.setSAP_ID(saporderid);
            res.setSAP_ID(saporderid);
            res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
            res.setDescription("参数不正确");
            sapResult.setRecords(res);
            return sapResult;

        } catch (Exception e) {
            res.setSAP_ID(saporderid);
            res.setSAP_ID(saporderid);
            res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
            res.setDescription("参数不正确");
            sapResult.setRecords(res);
            return sapResult;
        }
    }

    /**
     * 设置销售顾问设置客户五天后时间
     *
     * @param cbporder
     * @return
     */
    @RequestMapping("/noticeSet")
    public WeixinResult setCustomerTime(@RequestBody CBPOrder cbporder) {
        WeixinResult res = new WeixinResult();
        Integer orderid = cbporder.getOrderid();
        if (orderid == null) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        CBPOrder order = orderService.getOrderById(orderid);
        if(order==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        String userid=null;
        try {
            String result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserId", "{\"employeeid\":" + order.getEmployeeid() + "}");
            JSONObject getJson=JSONObject.fromObject(result);
            WeixinResult getRes=(WeixinResult) JSONObject.toBean(getJson,WeixinResult.class);
            String code = getRes.getCode();
            if(code.equals(CBPConstant.CODE_SUCCESS)){
                userid=(String) getRes.getData();
            }else {
                res.setCode(CBPConstant.CODE_FAILURE);
                res.setMessage(CBPConstant.MESSAGE_FAILURE);
                return res;
            }
            //获取客户信息
            Boolean flag= wxService.pustSetTime(userid,order.getSaporderid(),orderid,order.getEmployeeid());
            if(flag){
                //推送消息
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                return res;
            }else {
                res.setCode(CBPConstant.CODE_FAILURE);
                res.setMessage(CBPConstant.MESSAGE_FAILURE);
                return res;
            }
        }catch (Exception e){
            e.printStackTrace();
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
        }
        return res;
    }

    /**
     * 根据code获取员工id
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping("/getEmployeeidByCode")
    public WeixinResult getEmployeeidByCode(@RequestBody JSONObject jsonObject){
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
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            res.setData(employeeByCode);
            return res;
        }
    }
}
