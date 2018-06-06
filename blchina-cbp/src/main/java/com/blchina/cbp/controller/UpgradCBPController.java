/*
 * @(#)UpgradCBPController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.common.controller.BaseController;
import com.blchina.common.util.Json.JsonUtil;
import com.blchina.common.util.blchina.BlchinaUtil;
import com.blchina.common.util.number.NumberUtil;
import com.blchina.common.util.redis.RedisUtil;
import com.blchina.common.util.string.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *更新操作controller
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/apk")
public class UpgradCBPController extends BaseController {
    private Logger log = Logger.getLogger(UpgradCBPController.class);
    @Autowired
    protected Properties systemConfig;
    @Autowired
    public RedisUtil redisUtil;
    @RequestMapping("/upgradeCBP")
    public void upgradeCBP(@RequestParam(value = "", required = false, defaultValue = "") String version,
                           HttpServletResponse response) {
        log.info("CBP update version=" + version);

        try {
            // 判断参数是否正确
            if (!NumberUtil.isNumber(version)) {
                outWriteUTF8(response, buildErrorJsonResult(CBPConstant.CODE_NULL_PARAM, CBPConstant.MESSAGE_NULL_PARAM));
                return;
            }

            // 从缓存中查询版本号
            int lastVersion = getLastVersionFromRedis(BlchinaUtil.APK_CBP);
            if (lastVersion > Integer.parseInt(version)) {
                String apk = getLastApkInfoFromRedis(BlchinaUtil.APK_CBP);
                if (!StringUtil.isNullorEmpty(apk)) {
                    outWriteUTF8(response, apk);
                    return;
                }
            } else if (lastVersion > 0) {
                outWriteUTF8(response, buildErrorJsonResult(CBPConstant.CODE_FAILURE, "没有安装包更新"));
                return;
            }

            File dir = new File(systemConfig.getProperty("dir.cbp.apk"));
            if (dir == null) {  // 存放文件的父文件夹是否存在
                outWriteUTF8(response, buildErrorJsonResult(CBPConstant.CODE_FAILURE, "安装文件的文件夹不存在"));
                return;
            }

            String[] fileNameArray = dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(BlchinaUtil.APK_CBP) && name.endsWith(BlchinaUtil.APK_SUBFIX);
                }
            });

            // 安装文件是否存在
            if (fileNameArray == null || fileNameArray.length == 0) {
                outWriteUTF8(response, buildErrorJsonResult(CBPConstant.CODE_FAILURE, "安装文件不存在"));
                return;
            }

            int maxVersion = 0;
            String maxFileName = null;
            for (String fileName : fileNameArray) {
                String v = getApkVersion(BlchinaUtil.APK_CBP, fileName);
                if (NumberUtil.isNumber(v)) {
                    int tempV = Integer.parseInt(v);
                    if (tempV > maxVersion) {
                        maxVersion = tempV;
                        maxFileName = fileName;
                    }
                }
            }

            // 有更新
            if (maxVersion > Integer.parseInt(version)) {
                Map<String, String> data = new HashMap<>();
                data.put("lastVersion", maxVersion + "");
                data.put("apkUrl", getCBPApkUrl(maxFileName));
                WeixinResult result = new WeixinResult();
                result.setCode(CBPConstant.CODE_SUCCESS);
                result.setMessage(CBPConstant.MESSAGE_SUCCESS);
                result.setData(data);

                String json = JsonUtil.toJson(result);
                // 存到redis
                redisUtil.setex(getVersionKey(BlchinaUtil.APK_CBP), BlchinaUtil.TTL_APK_VERSION, maxVersion + "");
                redisUtil.set(getApkKey(BlchinaUtil.APK_CBP), json);

                outWriteUTF8(response, json);
            } else {
                outWriteUTF8(response, buildErrorJsonResult(CBPConstant.CODE_FAILURE, "没有安装包更新"));
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        outWriteUTF8(response, buildErrorJsonResult(CBPConstant.CODE_FAILURE, CBPConstant.MESSAGE_FAILURE));
    }

    public String buildErrorJsonResult(String code, String message) {
        WeixinResult result = new WeixinResult();
        result.setCode(code);
        result.setMessage(message);
        return JsonUtil.toJson(result);
    }
    // 获取缓存中最新的版本号
    public int getLastVersionFromRedis(String apkName) {
        String value = redisUtil.get(getVersionKey(apkName));
        return StringUtil.isNullorEmpty(value) ? 0 : Integer.parseInt(value);
    }
    public String getVersionKey(String apkName) {
        return BlchinaUtil.REDIS_APK_VERSION_PRIFIX + apkName;
    }
    public String getCBPApkUrl(String fileName) {
        return systemConfig.getProperty("url.cbp.apk") + fileName;
    }
    // 获取缓存中最新的版本号对应的安装包信息
    public String getLastApkInfoFromRedis(String apkName) {
        return redisUtil.get(getApkKey(apkName));
    }


    public String getApkKey(String apkName) {
        return BlchinaUtil.REDIS_APK_INFO_PRIFIX + apkName;
    }
    public String getApkVersion(String apkName, String fileName) {
        String regex = "^" + apkName + "_V(\\d+)" + BlchinaUtil.APK_SUBFIX + "$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
