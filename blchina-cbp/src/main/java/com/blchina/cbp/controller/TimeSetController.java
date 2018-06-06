/*
 * @(#)TimeSetController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.CBPReturnCarTimeDTO;
import com.blchina.cbp.dto.ReturnCarTimeDTO;
import com.blchina.cbp.dto.ReturnTimeSetDTO;
import com.blchina.cbp.dto.TimeConfDTO;
import com.blchina.cbp.model.*;
import com.blchina.cbp.service.interfaces.*;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.blchina.common.util.weixin.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间设置controller
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/time")
public class TimeSetController {
    @Autowired
    private Properties systemConfig;
    @Autowired
    private TimeTemplateService timeTemplateService;
    @Autowired
    private TimeConfService timeConfService;
    @Autowired
    private ReturnCarTimeService returnCarTimeService;
    @Autowired
    private MessageService messageServie;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TimeVisibleService timeVisibleService;
    @Autowired
    private WxService wxService;

    /**
     * 获取员工预约时间
     *
     * @param carTime
     * @return
     */
    @RequestMapping("/getEmployeeTime")
    public WeixinResult getEmployeeTime(@RequestBody CBPReturnCarTimeDTO carTime) {
        WeixinResult res = new WeixinResult();
        Integer employeeid = carTime.getEmployeeid();
        Integer orderid = carTime.getOrderid();
        if (StringUtil.isNullorEmpty(String.valueOf(employeeid))) {//必须传员工ID,订单号
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        } else {
            try {
                //获取员工所在门店
                String result =HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserIdAndShop", "{\"employeeid\":" + employeeid + "}");
                JSONObject getJson=JSONObject.fromObject(result);
                WeixinResult getRes=(WeixinResult) JSONObject.toBean(getJson,WeixinResult.class);
                String code = getRes.getCode();
                String getshop=null;
                if(code.equals(CBPConstant.CODE_SUCCESS)){
                    String  data = (String)getRes.getData();
                    String[] split = data.split(",");
                    getshop=split[1];
                }else {
                    res.setCode(CBPConstant.CODE_NULL_PARAM);
                    res.setMessage("获取门店信息失败");
                    return res;
                }
                Integer shop=Integer.valueOf(getshop);
                CBPTimeConf shopConfig = timeConfService.getShopConfig(shop);
                List<CBPTimePeriod> timePeriodByConfig = new ArrayList<CBPTimePeriod>();//时间段
                if (shopConfig == null) {//没有门店预约天数插入默认五天的天数
                    shopConfig = new CBPTimeConf();
                    shopConfig.setStoreid(shop);
                    shopConfig.setDaynum(CBPConstant.SHOP_DAY);
                    CBPTimePeriod period = new CBPTimePeriod();
                    period.setStarttime(CBPConstant.SHOP_TIME_FIRST_START);
                    period.setEndtime(CBPConstant.SHOP_TIME_FIRST_END);
                    timePeriodByConfig.add(period);
                    CBPTimePeriod periodTwo = new CBPTimePeriod();
                    periodTwo.setStarttime(CBPConstant.SHOP_TIME_TWO_START);
                    periodTwo.setEndtime(CBPConstant.SHOP_TIME_TWO_END);
                    timePeriodByConfig.add(periodTwo);
                    CBPTimePeriod periodThree = new CBPTimePeriod();
                    periodThree.setStarttime(CBPConstant.SHOP_TIME_THREE_START);
                    periodThree.setEndtime(CBPConstant.SHOP_TIME_THREE_END);
                    timePeriodByConfig.add(periodThree);
                } else {
                    //获取门店时间模板配置
                    timePeriodByConfig = timeTemplateService.getTimePeriodByConfig(shopConfig.getTimeconfid());
                }

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String today = sdf.format(date);
                //获取员工预约时间
                List<CBPReturnCarTime> employeeReturnTime =null;
                String type = carTime.getType();
                if(type!=null&&type.equals("1")){
                    employeeReturnTime= returnCarTimeService.getEmployeeReturnTimeAll(employeeid);
                }else {
                    employeeReturnTime=returnCarTimeService.getEmployeeReturnTime(employeeid, today);
                }


                Integer daynum = Integer.valueOf(shopConfig.getDaynum());//门店设置的可预约天数
                if (type == null || !type.equals("1")) {//是否特殊口进来，如果不是特殊口剔除可预约天后的记录
                    Calendar d = Calendar.getInstance();//创建实例 默认是当前时刻
                    int nowDay = d.get(Calendar.DAY_OF_MONTH);
                    d.set(Calendar.DATE, nowDay + daynum);
                    String day = sdf.format(d.getTime());
                    for (int i = 0; i < employeeReturnTime.size(); i++) {
                        CBPReturnCarTime cbpReturnCarTime = employeeReturnTime.get(i);
                        String dt = cbpReturnCarTime.getDate();
                        if (sdf.parse(dt).getTime() > sdf.parse(day).getTime()) {
                            employeeReturnTime.remove(i);
                        }

                    }
                }
                Calendar c = Calendar.getInstance();//创建实例 默认是当前时刻
                for (int i = 0; i < daynum; i++) {//
                    int nowDay = c.get(Calendar.DAY_OF_MONTH);
                    c.set(Calendar.DATE, nowDay + 1);
                    String day = sdf.format(c.getTime());
                    for (int j = 0; j < timePeriodByConfig.size(); j++) {//创建当天
                        Boolean flag = true;
                        for (int x = 0; x < employeeReturnTime.size(); x++) {//有预约时间
                            if (day.equals(employeeReturnTime.get(x).getDate())) {
                                String starttime = employeeReturnTime.get(x).getStarttime();
                                if (starttime.equals(timePeriodByConfig.get(j).getStarttime())) {
                                    flag = false;
                                    break;
                                }

                            }
                        }
                        if (flag) {//没有创建
                            CBPReturnCarTime cct = new CBPReturnCarTime();
                            cct.setEmployeeid(employeeid);
                            cct.setDate(day);
                            cct.setStarttime(timePeriodByConfig.get(j).getStarttime());
                            cct.setEndtime(timePeriodByConfig.get(j).getEndtime());
                            cct.setStatus(String.valueOf(CBPConstant.TimePeriodStatusEnum.CANRESERVE.getType()));
                            employeeReturnTime.add(cct);
                        }

                    }
                }


                //给预约时间模板排序
                Collections.sort(employeeReturnTime);
                ReturnCarTimeDTO returnCarTimeDTO = new ReturnCarTimeDTO();
                returnCarTimeDTO.setReturnCarlist(employeeReturnTime);
                if(!StringUtil.isNullorEmpty(String.valueOf(orderid))){
                    //获取消息状态如果已经设置不让查询设置
                    CBPDeliverMessage cbpDeliverMessage = new CBPDeliverMessage();
                    cbpDeliverMessage.setOrderid(orderid);
                    cbpDeliverMessage.setMessagetype(CBPConstant.MessageTypeEnum.SALE_FIVEINTERNAL.getType());
                    CBPDeliverMessage messageStatus = messageServie.getMessageStatus(cbpDeliverMessage);
                    if (messageStatus == null || messageStatus.getStatus().equals(CBPConstant.MessageStatusEnum.UNSET.getType())) {//若果没有该订单消息或者已经设置不让操作

                        if (type == null || !type.equals("1")) {//不是特殊入口才添加不可见时间
                            List<CBPTimeVisible> visibleByOrderid = timeVisibleService.getVisibleByOrderid(orderid);
                            if (visibleByOrderid.size()!=0){
                                List<String> noVisibleList=new ArrayList<String>();
                                returnCarTimeDTO.setNoVisibleList(noVisibleList);
                            }
                            for (int i = 0; i < visibleByOrderid.size(); i++) {
                                returnCarTimeDTO.getNoVisibleList().add(visibleByOrderid.get(i).getDate());
                            }
                        } else {
                            res.setCode(CBPConstant.CODE_SUCCESS);
                            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                            res.setData(returnCarTimeDTO);
                            return res;
                        }
                } else {
                        if(type != null && type.equals("1")){
                            res.setCode(CBPConstant.CODE_SUCCESS);
                            res.setMessage("获取时间成功");
                            res.setData(returnCarTimeDTO);
                            return res;
                        }
                        res.setCode(CBPConstant.CODE_LOSE);
                        res.setMessage("已经设置不能再次设置");
                        res.setData(returnCarTimeDTO);
                        return res;
                    }


                }
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                res.setData(returnCarTimeDTO);
                return res;

            } catch (Exception e) {
                res.setCode(CBPConstant.CODE_FAILURE);
                res.setMessage(CBPConstant.MESSAGE_FAILURE);
                e.printStackTrace();
            }
        }
        return res;

    }


    /**
     * 设置员工预约时间
     *
     * @param returnTimeSetDTO
     * @return
     */
    @RequestMapping("/setEmployeeTime")
    @Transactional
    public WeixinResult setEmployeeTime(@RequestBody ReturnTimeSetDTO returnTimeSetDTO) {
        WeixinResult res = new WeixinResult();
        List<CBPReturnCarTime> listReturmTime = returnTimeSetDTO.getListReturmTime();
        Integer orderid = returnTimeSetDTO.getOrderid();
        if (listReturmTime.size() == 0) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        if(orderid==null){//不推送
            returnCarTimeService.addOrUpdateReturnTime(listReturmTime);
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
        }
        CBPOrder orderById = orderService.getOrderById(orderid);
        if(orderById==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        try {
            Boolean flag=true;
            List<CBPReturnCarTime> returnTimeByOrder = returnCarTimeService.getReturnTimeByOrder(orderid);
            if(returnTimeByOrder.size()==0){//设置过了不能再次推送
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("customerid", orderById.getCustomerid());
                    jsonObject.put("brandid", orderById.getBrandid());
                    String getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid", jsonObject.toString());
                    String[] split = getcus.split(",");
                    String customername = split[0];
                    String openid = split[1];
                    flag = wxService.pushCustomerStart(orderById.getBrandid(), orderById.getSaporderid(), openid, customername, orderById.getOrderid(), orderById.getEmployeeid());
                }catch(Exception e){
                    res.setCode(CBPConstant.CODE_FAILURE);
                    res.setMessage("客户没有关注公众号");
                    return res;
                }
                }
           if(flag){
               returnCarTimeService.addOrUpdateReturnTime(listReturmTime);
               List<CBPTimeVisible> listVisibleTime = returnTimeSetDTO.getListVisibleTime();
               if(listVisibleTime!=null&&listVisibleTime.size()!=0){
                   for(int j=0;j<listVisibleTime.size();j++){
                       listVisibleTime.get(j).setCustomerid(Integer.valueOf(orderById.getCustomerid()));
                       listVisibleTime.get(j).setOrderid(orderid);
                   }
                   //添加设置不可见时间
                   timeVisibleService.addVisibleList(listVisibleTime, orderid);
               }
               //更新消息为已设置
               messageServie.updateMessageByOrderid(orderid);
               res.setCode(CBPConstant.CODE_SUCCESS);
               res.setMessage(CBPConstant.MESSAGE_SUCCESS);
           }else {
               res.setCode(CBPConstant.CODE_FAILURE);
               res.setMessage(CBPConstant.MESSAGE_FAILURE);
           }
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
            return res;
        }

        return res;
    }

    /**
     * 获取员工预约时间
     *
     * @param carTime
     * @return
     */
    @RequestMapping("/getCustomerTime")
    public WeixinResult getCustomerTime(@RequestBody CBPReturnCarTimeDTO carTime) {
        WeixinResult res = new WeixinResult();
        Integer employeeid = carTime.getEmployeeid();
        Integer orderid = carTime.getOrderid();
        if (StringUtil.isNullorEmpty(String.valueOf(employeeid)) || StringUtil.isNullorEmpty(String.valueOf(orderid))) {//必须传员工ID,订单号
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        } else {
            try {
                //获取员工所在门店
                String result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeShop", "{\"employeeid\":" + employeeid + "}");
                JSONObject jsonObject = JSONObject.fromObject(result);
                WeixinResult resultWeixin = (WeixinResult) JSONObject.toBean(jsonObject, WeixinResult.class);
                String getdata = (String) resultWeixin.getData();
                String getshop=getdata.split(",")[0];
                String employeePhonenumber=getdata.split(",")[1];
                Integer shop = Integer.valueOf(getshop);
                CBPTimeConf shopConfig = timeConfService.getShopConfig(shop);
                List<CBPTimePeriod> timePeriodByConfig = new ArrayList<CBPTimePeriod>();//时间段
                if (shopConfig == null) {//没有门店预约天数插入默认五天的天数
                    shopConfig = new CBPTimeConf();
                    shopConfig.setStoreid(shop);
                    shopConfig.setDaynum(CBPConstant.SHOP_DAY);
                    CBPTimePeriod period = new CBPTimePeriod();
                    period.setStarttime(CBPConstant.SHOP_TIME_FIRST_START);
                    period.setEndtime(CBPConstant.SHOP_TIME_FIRST_END);
                    timePeriodByConfig.add(period);
                    CBPTimePeriod periodTwo = new CBPTimePeriod();
                    periodTwo.setStarttime(CBPConstant.SHOP_TIME_TWO_START);
                    periodTwo.setEndtime(CBPConstant.SHOP_TIME_TWO_END);
                    timePeriodByConfig.add(periodTwo);
                    CBPTimePeriod periodThree = new CBPTimePeriod();
                    periodThree.setStarttime(CBPConstant.SHOP_TIME_THREE_START);
                    periodThree.setEndtime(CBPConstant.SHOP_TIME_THREE_END);
                    timePeriodByConfig.add(periodThree);
                } else {
                    //获取门店时间模板配置
                    timePeriodByConfig = timeTemplateService.getTimePeriodByConfig(shopConfig.getTimeconfid());
                }
                //获取订单设置开始时间
                CBPOrder cbpOrder = orderService.selectByPrimaryKey(orderid);
                if (cbpOrder == null) {
                    res.setCode(CBPConstant.CODE_NULL_PARAM);
                    res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                    return res;
                }
                Integer daynum = Integer.valueOf(shopConfig.getDaynum());//门店设置的可预约天数
                String delivertime = cbpOrder.getDelivertime();
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String today = sdf.format(date);
                Date startTime = sdf.parse(delivertime);
                long passDay = (date.getTime() - startTime.getTime()) / (1000 * 60 * 60 * 24);
                int pass = new Long(passDay).intValue();
                if (pass > daynum.intValue()) {
                    res.setCode(CBPConstant.CODE_LOSE);
                    res.setMessage("已超过可设置预约天数");
                    return res;
                }
                //获取员工预约时间
                Calendar d = Calendar.getInstance();//创建实例 默认是当前时刻
                d.setTime(startTime);
                int nowDate = d.get(Calendar.DAY_OF_MONTH);
                d.set(Calendar.DATE, nowDate + daynum + 1);//最大预约天数
                String endDay = sdf.format(d.getTime());
                //获取销售顾问预约时间表
                List<CBPReturnCarTime> employeeReturnTime = returnCarTimeService.getCustomerReturnTime(employeeid, today, endDay);
                //获取客户不可见时间
                List<CBPTimeVisible> visibleList = timeVisibleService.getVisibleByOrderid(orderid);
                Calendar c = Calendar.getInstance();//创建实例 默认是当前时刻
                for (int i = 0; i < (daynum - pass); i++) {
                    int nowDay = c.get(Calendar.DAY_OF_MONTH);
                    c.set(Calendar.DATE, nowDay + 1);
                    String day = sdf.format(c.getTime());
                    Boolean isVisible = true;
                    for (int k = 0; k < visibleList.size(); k++) {
                        if (visibleList.get(k).getDate().equals(day)) {
                            for (int s = 0; s < employeeReturnTime.size(); s++) {
                                if (employeeReturnTime.get(s).getDate().equals(day)) {
                                    employeeReturnTime.remove(s);
                                    s=s-1;
                                }
                            }
                            isVisible = false;
                            break;
                        }
                    }
                    if (isVisible) {
                        for (int j = 0; j < timePeriodByConfig.size(); j++) {//创建当天
                            Boolean flag = true;
                            for (int x = 0; x < employeeReturnTime.size(); x++) {//有预约时间
                                if (day.equals(employeeReturnTime.get(x).getDate())) {
                                    String starttime = employeeReturnTime.get(x).getStarttime();
                                    if (starttime.equals(timePeriodByConfig.get(j).getStarttime())) {
                                        flag = false;
                                        break;
                                    }

                                }
                            }
                            if (flag) {//没有创建
                                CBPReturnCarTime cct = new CBPReturnCarTime();
                                cct.setEmployeeid(employeeid);
                                cct.setDate(day);
                                cct.setStarttime(timePeriodByConfig.get(j).getStarttime());
                                cct.setEndtime(timePeriodByConfig.get(j).getEndtime());
                                cct.setStatus(String.valueOf(CBPConstant.TimePeriodStatusEnum.CANRESERVE.getType()));
                                employeeReturnTime.add(cct);
                            }

                        }
                    }
                }

                //给预约时间模板排序
                Collections.sort(employeeReturnTime);
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setMessage(employeePhonenumber);
                res.setData(employeeReturnTime);

            } catch (Exception e) {
                res.setCode(CBPConstant.CODE_FAILURE);
                res.setMessage(CBPConstant.MESSAGE_FAILURE);
                e.printStackTrace();
            }
        }
        return res;

    }

    /**
     * 设置客户预约时间
     *
     * @param returnTime
     * @return
     */
    @RequestMapping("/setCustomerTime")
    @Transactional
    public WeixinResult setCustomerTime(@RequestBody CBPReturnCarTime returnTime) {
        WeixinResult res = new WeixinResult();
        Integer orderid = returnTime.getOrderid();
        Integer employeeid = returnTime.getEmployeeid();
        String date = returnTime.getDate();
        String starttime = returnTime.getStarttime();
        String endtime = returnTime.getEndtime();
        if (orderid == null || employeeid == null || date == null || starttime == null || endtime == null) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        CBPOrder orderById = orderService.getOrderById(orderid);
        if(orderById==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        Integer customerid = orderById.getCustomerid();
        //获取客户信息
        String getcus = null;
        String userid = null;
        String customerName=orderById.getCustomername();
        returnTime.setCustomerid(customerid);
        try {
            String result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserId", "{\"employeeid\":" + employeeid + "}");
            JSONObject getJson = JSONObject.fromObject(result);
            WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
            String code = getRes.getCode();
            if (code.equals(CBPConstant.CODE_SUCCESS)) {
                userid = (String) getRes.getData();
            }
        } catch (Exception e) {
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
            return res;
        }
        String flag = returnCarTimeService.setCustomerTime(returnTime);
        if(flag==null){
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage("该时段不可预约");
        } else if (flag.equals("1")||flag.equals("2")) {
            //推送消息
            wxService.pushViewMessage(userid, customerName,date, starttime, endtime, orderid, employeeid,orderById.getBrandid(),flag);
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        }
        return res;
    }

    /**
     * 销售顾问设置客户预约时间
     * @param returnTime
     * @return
     */
    @RequestMapping("/setCustomerTimeByEmployee")
    @Transactional
    public WeixinResult setCustomerTimeByEmployee(@RequestBody CBPReturnCarTime returnTime) {
        WeixinResult res = new WeixinResult();
        Integer orderid = returnTime.getOrderid();
        Integer employeeid = returnTime.getEmployeeid();
        String date = returnTime.getDate();
        String starttime = returnTime.getStarttime();
        String endtime = returnTime.getEndtime();
        if (orderid == null || employeeid == null || date == null || starttime == null || endtime == null) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        CBPOrder orderById = orderService.getOrderById(orderid);
        if(orderById==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        String flag = returnCarTimeService.setCustomerTime(returnTime);
        //推送消息
        if (flag!=null) {
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("customerid",orderById.getCustomerid());
                jsonObject.put("brandid",orderById.getBrandid());
                String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObject.toString());
                String[] split = getcus.split(",");
                String customername=split[0];
                String openid=split[1];
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                Date parse = sdf.parse(date);
                SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
                String getDate = format.format(parse);
                String content=getDate+starttime+"至"+endtime;
                wxService.pushCustomerTime(orderById.getBrandid(),openid,content);
            }catch (Exception e){
                e.printStackTrace();
                res.setCode(CBPConstant.CODE_FAILURE);
                res.setMessage("客户没有关注公众号");
                return res;
            }

            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        } else {
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage("已经他人预约不能再次设置");
        }
        return res;
    }

    /**
     * 获取某天预约时间
     *
     * @param returnTime
     * @return
     */
    @RequestMapping("/getDayReturnTime")
    public WeixinResult getDayReturnTime(@RequestBody CBPReturnCarTime returnTime) {
        WeixinResult res = new WeixinResult();
        Integer employeeid = returnTime.getEmployeeid();
        String date = returnTime.getDate();
        if (employeeid == null || date == null) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        List<ReturnCarTimeDTO> dayReturnTime = returnCarTimeService.getDayReturnTime(returnTime);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(dayReturnTime);
        return res;
    }

    /**
     * 获取员工预约时间base入口
     *
     * @param carTime
     * @return
     */
    @RequestMapping("/getEmployeeTimeBase")
    public WeixinResult getEmployeeTimeBase(@RequestBody CBPReturnCarTimeDTO carTime) throws Exception {
        WeixinResult res = new WeixinResult();
        String code = carTime.getCode();
            if(code==null){
                res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return res;
            }
       String  userId = wxService.getUserId(code);
            if(userId==null){
                res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return res;
            }

        String result=null;
        Integer shop=null;
        try {
                result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeByUserid", "{\"email\":\"" + userId + "\"}");
                String[] split = result.split(",");
                Integer  employeeid=Integer.valueOf(split[0]);
                shop=Integer.valueOf(split[1]);
                CBPTimeConf shopConfig = timeConfService.getShopConfig(shop);
                List<CBPTimePeriod> timePeriodByConfig = new ArrayList<CBPTimePeriod>();//时间段
                if (shopConfig == null) {//没有门店预约天数插入默认五天的天数
                    shopConfig = new CBPTimeConf();
                    shopConfig.setStoreid(shop);
                    shopConfig.setDaynum(CBPConstant.SHOP_DAY);
                    CBPTimePeriod period = new CBPTimePeriod();
                    period.setStarttime(CBPConstant.SHOP_TIME_FIRST_START);
                    period.setEndtime(CBPConstant.SHOP_TIME_FIRST_END);
                    timePeriodByConfig.add(period);
                    CBPTimePeriod periodTwo = new CBPTimePeriod();
                    periodTwo.setStarttime(CBPConstant.SHOP_TIME_TWO_START);
                    periodTwo.setEndtime(CBPConstant.SHOP_TIME_TWO_END);
                    timePeriodByConfig.add(periodTwo);
                    CBPTimePeriod periodThree = new CBPTimePeriod();
                    periodThree.setStarttime(CBPConstant.SHOP_TIME_THREE_START);
                    periodThree.setEndtime(CBPConstant.SHOP_TIME_THREE_END);
                    timePeriodByConfig.add(periodThree);
                } else {
                    //获取门店时间模板配置
                    timePeriodByConfig = timeTemplateService.getTimePeriodByConfig(shopConfig.getTimeconfid());
                }

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String today = sdf.format(date);
                //获取员工预约时间
                List<CBPReturnCarTime> employeeReturnTime = returnCarTimeService.getEmployeeReturnTime(employeeid, today);
                String type = carTime.getType();
                Integer daynum = Integer.valueOf(shopConfig.getDaynum());//门店设置的可预约天数
                if (type == null || !type.equals("1")) {//是否特殊口进来，如果不是特殊口剔除可预约天后的记录
                    Calendar d = Calendar.getInstance();//创建实例 默认是当前时刻
                    int nowDay = d.get(Calendar.DAY_OF_MONTH);
                    d.set(Calendar.DATE, nowDay + daynum);
                    String day = sdf.format(d.getTime());
                    for (int i = 0; i < employeeReturnTime.size(); i++) {
                        CBPReturnCarTime cbpReturnCarTime = employeeReturnTime.get(i);
                        String dt = cbpReturnCarTime.getDate();
                        if (sdf.parse(dt).getTime() > sdf.parse(day).getTime()) {
                            employeeReturnTime.remove(i);
                        }

                    }
                }
                Calendar c = Calendar.getInstance();//创建实例 默认是当前时刻
                for (int i = 0; i < daynum; i++) {//
                    int nowDay = c.get(Calendar.DAY_OF_MONTH);
                    c.set(Calendar.DATE, nowDay + 1);
                    String day = sdf.format(c.getTime());
                    for (int j = 0; j < timePeriodByConfig.size(); j++) {//创建当天
                        Boolean flag = true;
                        for (int x = 0; x < employeeReturnTime.size(); x++) {//有预约时间
                            if (day.equals(employeeReturnTime.get(x).getDate())) {
                                String starttime = employeeReturnTime.get(x).getStarttime();
                                if (starttime.equals(timePeriodByConfig.get(j).getStarttime())) {
                                    flag = false;
                                    break;
                                }

                            }
                        }
                        if (flag) {//没有创建
                            CBPReturnCarTime cct = new CBPReturnCarTime();
                            cct.setEmployeeid(employeeid);
                            cct.setDate(day);
                            cct.setStarttime(timePeriodByConfig.get(j).getStarttime());
                            cct.setEndtime(timePeriodByConfig.get(j).getEndtime());
                            cct.setStatus(String.valueOf(CBPConstant.TimePeriodStatusEnum.CANRESERVE.getType()));
                            employeeReturnTime.add(cct);
                        }

                    }
                }

                //给预约时间模板排序
                Collections.sort(employeeReturnTime);
                ReturnCarTimeDTO returnCarTimeDTO = new ReturnCarTimeDTO();
                returnCarTimeDTO.setReturnCarlist(employeeReturnTime);
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                res.setData(returnCarTimeDTO);
                return res;

            } catch (Exception e) {
                res.setCode(CBPConstant.CODE_FAILURE);
                res.setMessage(CBPConstant.MESSAGE_FAILURE);
                e.printStackTrace();
            }

        return res;

    }

    /**
     * 设置员工预约时间
     *
     * @param returnTimeSetDTO
     * @return
     */
    @RequestMapping("/setEmployeeTimeBase")
    @Transactional
    public WeixinResult setEmployeeTimeBase(@RequestBody ReturnTimeSetDTO returnTimeSetDTO) {
        WeixinResult res = new WeixinResult();
        List<CBPReturnCarTime> listReturmTime = returnTimeSetDTO.getListReturmTime();
        String customername = returnTimeSetDTO.getCustomername();
        Long phonenumber = returnTimeSetDTO.getPhonenumber();
        if (listReturmTime.size() == 0) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        if (customername != null && phonenumber != null) {//给客户
            String result = null;
            try {
                result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerIdAndOpenIDByphone", "{\"phonenumber\":" + phonenumber + "}");
                if (result == null) {
                    res.setCode(CBPConstant.CODE_NULL_PARAM);
                    res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                    return res;
                } else {
                    String[] split = result.split(",");
                    String customerid = split[0];
                    String openid = split[1];
                    CBPOrder order = new CBPOrder();
                    order.setCustomerid(Integer.valueOf(customerid));
                    order.setOrderstatus(String.valueOf(CBPConstant.OrderStatusEnum.WAITINGALLPAY.getType()));
                    List<CBPOrder> orderList = orderService.getOrderListByCustomerIdAndStatus(order);
                    if (orderList.size() == 0) {
                        res.setCode(CBPConstant.CODE_NULL_PARAM);
                        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                        return res;
                    } else if (orderList.size() == 1) {
                        CBPOrder cbpOrder = orderList.get(0);
                        //获取消息状态如果已经设置不让查询设置
                        CBPDeliverMessage cbpDeliverMessage = new CBPDeliverMessage();
                        cbpDeliverMessage.setOrderid(cbpOrder.getOrderid());
                        cbpDeliverMessage.setMessagetype(CBPConstant.MessageTypeEnum.SALE_FIVEINTERNAL.getType());
                        CBPDeliverMessage messageStatus = messageServie.getMessageStatus(cbpDeliverMessage);
                        if (messageStatus == null || messageStatus.getStatus().equals(CBPConstant.MessageStatusEnum.UNSET.getType())) {
                            Boolean flag = wxService.pushCustomerStart(cbpOrder.getBrandid(), cbpOrder.getSaporderid(), openid, customername, cbpOrder.getOrderid(), cbpOrder.getEmployeeid());
                            if (!flag) {
                                res.setCode(CBPConstant.CODE_FAILURE);
                                res.setMessage("推送消息失败");
                                return res;
                            }
                            List<CBPTimeVisible> listVisibleTime = returnTimeSetDTO.getListVisibleTime();
                            if(listVisibleTime!=null&&listVisibleTime.size()!=0){
                                for (int i = 0; i < listVisibleTime.size(); i++) {
                                    listVisibleTime.get(i).setCustomerid(Integer.valueOf(customerid));
                                    listVisibleTime.get(i).setOrderid(cbpOrder.getOrderid());
                                }
                                //添加设置不可见时间
                                timeVisibleService.addVisibleList(listVisibleTime, cbpOrder.getOrderid());
                            }
                            //更新消息为已设置
                            messageServie.updateMessageByOrderid(cbpOrder.getOrderid());
                            res.setCode(CBPConstant.CODE_SUCCESS);
                            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                            return res;

                        } else {
                            res.setCode(CBPConstant.CODE_LOSE);
                            res.setMessage("已经设置不能再次设置");
                            return res;
                        }

                    } else {
                        res.setCode(CBPConstant.CODE_LOSE);
                        res.setMessage("请选择具体哪个订单");
                        res.setData(orderList);
                        return res;

                    }

                }

            } catch (Exception e) {
                res.setCode(CBPConstant.CODE_FAILURE);
                res.setMessage(CBPConstant.MESSAGE_FAILURE);
                return res;
            }
        }
        returnCarTimeService.addOrUpdateReturnTime(listReturmTime);
        //更新消息为已设置
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 销售顾问设置客户预约时间
     *
     * @param returnTime
     * @return
     */
    @RequestMapping("/setCustomerTimeBase")
    @Transactional
    public WeixinResult setCustomerTimeBase(@RequestBody CBPReturnCarTimeDTO returnTime) {
        WeixinResult res = new WeixinResult();
        Integer orderid = returnTime.getOrderid();
        Integer employeeid = returnTime.getEmployeeid();
        String date = returnTime.getDate();
        String starttime = returnTime.getStarttime();
        String endtime = returnTime.getEndtime();
        String customername = returnTime.getCustomername();
        Long phonenumber = returnTime.getPhonenumber();
        if (phonenumber == null || customername == null || employeeid == null || date == null || starttime == null || endtime == null) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        //获取客户信息
        String result = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerIdAndOpenIDByphone", "{\"phonenumber\":" + phonenumber + "}");

            if (result == null) {
                res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return res;
            } else {
                String[] split = result.split(",");
                String customerid = split[0];
                CBPOrder order = new CBPOrder();
                order.setCustomerid(Integer.valueOf(customerid));
                order.setOrderstatus(String.valueOf(CBPConstant.OrderStatusEnum.WAITINGALLPAY.getType()));
                List<CBPOrder> orderList = orderService.getOrderListByCustomerIdAndStatus(order);
                if (orderList.size() == 0) {
                    res.setCode(CBPConstant.CODE_NULL_PARAM);
                    res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                    return res;
                } else if (orderList.size() == 1) {
                    returnTime.setOrderid(orderList.get(0).getOrderid());
                    String flag = returnCarTimeService.setCustomerTime(returnTime);
                    //推送消息
                    if (flag!=null) {
                        res.setCode(CBPConstant.CODE_SUCCESS);
                        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
                    } else {
                        res.setCode(CBPConstant.CODE_FAILURE);
                        res.setMessage(CBPConstant.MESSAGE_FAILURE);
                    }
                    return res;
                } else {
                    res.setCode(CBPConstant.CODE_LOSE);
                    res.setMessage("请选择具体哪个订单");
                    res.setData(orderList);
                    return res;
                }
            }

        } catch (Exception e) {
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
            return res;
        }
    }
}
