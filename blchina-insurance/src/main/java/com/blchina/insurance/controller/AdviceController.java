/*
 * @(#)AdviceController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.insurance.controller;


import com.blchina.insurance.datamodel.CBPConstant;
import com.blchina.insurance.datamodel.weixin.WeixinResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常
 * @author zhuchenglong
 * @since JDK 1.8
 */
@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WeixinResult handleIOException(Exception ex) {
        ex.printStackTrace();
        WeixinResult res=new WeixinResult();
        res.setCode(CBPConstant.CODE_FAILURE);
        res.setMessage(CBPConstant.MESSAGE_FAILURE);
        return res;
    }

}
