/*
 * @(#)AdviceController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.exception.BusinessException;
import com.blchina.cbp.exception.WXException;

/**
 * 全局异常
 * @author zhuchenglong
 * @since JDK 1.8
 */
@ControllerAdvice
public class AdviceController {
    Logger logger=Logger.getLogger(AdviceController.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WeixinResult handleIOException(Exception ex) {
        logger.info(ex.toString());
        ex.printStackTrace();
        WeixinResult res=new WeixinResult();
        res.setCode(CBPConstant.CODE_FAILURE);
        res.setMessage(CBPConstant.MESSAGE_FAILURE);
        return res;
    }
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public SAPResult handleSapException(BusinessException ex) {
        SAPResult res=new SAPResult();
        SAPResponse response=new SAPResponse();
        response.setSAP_ID(ex.getSapid());
        response.setBL_ID(ex.getSapid());
        response.setDescription(ex.getMsg());
        response.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
        res.setRecords(response);
        return res;
    }
    
    @ExceptionHandler(WXException.class)
    @ResponseBody
    public WeixinResult winxinException(WXException ex) {
       logger.info(ex.toString());
       WeixinResult res=new WeixinResult();
       res.setCode(CBPConstant.CODE_FAILURE);
       res.setMessage(ex.getMessage());
        return res;
    }
}
