/*
 * @(#)AuthService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.CustmerDTO;
import com.blchina.cbp.model.BDLCustomer;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *认证service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface AuthService {
    public WeixinResult seltauth(BDLCustomer customer);

    WeixinResult companyauth(CustmerDTO customer);

    String autosign(String signcustomerid, Integer contractid);

    String uploadFile(File contracturl, String constactid);
}
