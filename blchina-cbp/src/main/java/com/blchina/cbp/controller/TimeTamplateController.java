/*
 * @(#)TimeTamplateController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.TimeConfDTO;
import com.blchina.cbp.model.CBPTimeConf;
import com.blchina.cbp.model.CBPTimePeriod;
import com.blchina.cbp.service.interfaces.TimeConfService;
import com.blchina.cbp.service.interfaces.TimeTemplateService;
import com.blchina.common.util.string.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间模板设置controller
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/time")
public class TimeTamplateController {

    @Autowired
    private TimeTemplateService timeTemplateService;
    @Autowired
    private TimeConfService timeConfService;
    /**
     * 获取门店时间模板
     *
     * @param cbpTimeConf
     * @return
     */
    @RequestMapping("/getShopTimeTamplate")
    public WeixinResult getShopTimeTamplate(@RequestBody CBPTimeConf cbpTimeConf) {
        WeixinResult res = new WeixinResult();
        Integer storeid = cbpTimeConf.getStoreid();
        if (StringUtil.isNullorEmpty(String.valueOf(storeid))) {//门店号必须传
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        } else {
            CBPTimeConf shopConfig = timeConfService.getShopConfig(storeid);
            List<CBPTimePeriod> timePeriodByConfig = new ArrayList<CBPTimePeriod>();//时间段
            if (shopConfig == null) {//没有门店预约天数插入默认五天的天数
                shopConfig = new CBPTimeConf();
                shopConfig.setStoreid(storeid);
                shopConfig.setDaynum(CBPConstant.SHOP_DAY);
                shopConfig.setPaperCost(CBPConstant.SHOP_PaperCost);
                shopConfig.setContractCount(CBPConstant.SHOP_ContractCount);
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
            TimeConfDTO timeConfDTO = new TimeConfDTO();
            timeConfDTO.setDaynum(shopConfig.getDaynum());
            timeConfDTO.setPaperCost(shopConfig.getPaperCost());
            timeConfDTO.setContractCount(shopConfig.getContractCount());
            timeConfDTO.setStoreid(shopConfig.getStoreid());
            timeConfDTO.setTimeconfid(shopConfig.getTimeconfid());
            timeConfDTO.setStorename(shopConfig.getStorename());
            timeConfDTO.setPeriodList(timePeriodByConfig);
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            res.setData(timeConfDTO);
            return res;
        }

    }

    /**
     * 设置门店预约时间
     *
     * @param timeConfDTO
     * @return
     */
    @RequestMapping("/setShopTimeTamplate")
    public WeixinResult setShopTimeTamplate(@RequestBody TimeConfDTO timeConfDTO) {
        WeixinResult res = new WeixinResult();
        Integer storeid = timeConfDTO.getStoreid();
        String daynum = timeConfDTO.getDaynum();
        Integer contractCount = timeConfDTO.getContractCount();
        String paperCost = timeConfDTO.getPaperCost();
        try {//由于天数字符串必输传入数字
            Integer day = Integer.valueOf(daynum);
            Integer contract = Integer.valueOf(contractCount);
        } catch (Exception e) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        if (StringUtil.isNullorEmpty(String.valueOf(storeid)) || timeConfDTO.getPeriodList().size() == 0) {//门店号必须传时间模板不能为空
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        } else {
            Integer timeconfid = timeConfDTO.getTimeconfid();
            if (StringUtil.isNullorEmpty(String.valueOf(timeconfid))) {//若果ID为空说明没配置时间模板
                CBPTimeConf shopConfig = new CBPTimeConf();
                shopConfig.setStoreid(storeid);
                shopConfig.setDaynum(daynum);
                shopConfig.setPaperCost(paperCost);
                shopConfig.setContractCount(contractCount);
                shopConfig.setStorename(timeConfDTO.getStorename());
                timeConfService.insertShopConfig(shopConfig);
                timeconfid = shopConfig.getTimeconfid();
            } else {
                timeConfService.updateShopConfig(timeConfDTO);
            }
            //更新前先删除之前门店配置配置
            timeTemplateService.deleteShopTemplateByshop(timeconfid);
            for (int i = 0; i < timeConfDTO.getPeriodList().size(); i++) {
                CBPTimePeriod cbpTimePeriod = timeConfDTO.getPeriodList().get(i);
                 cbpTimePeriod.setTimeperiodid(null);
                  cbpTimePeriod.setTimeconfid(timeconfid);
                    timeTemplateService.inTemplatePeriod(cbpTimePeriod);
            }
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
        }

    }
}
