/*
 * @(#)UploadConstroller.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.FileAddressDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPCar;
import com.blchina.cbp.model.CBPOrderFileExt;
import com.blchina.cbp.service.interfaces.*;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * 文件上传
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
public class UploadConstroller {
    @Autowired
    private ContractManageService contractManageService;
    @Autowired
    OrderFileExtService orderFileExtService;
    @Autowired
    protected Properties systemConfig;
    @Autowired
    CarService carService;
    /**
     * 上传文件
     * @return
     */
    @RequestMapping("/upload")
    public WeixinResult doupload(@RequestBody UploadDTO uploadDTO){
        WeixinResult res=new WeixinResult();
        String type = uploadDTO.getType();
        res.setCode(CBPConstant.CODE_NULL_PARAM);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        if(uploadDTO.getOrderid()==null){
            return res;
        }
        Boolean flag=null;
        String[] file = uploadDTO.getFile();
        String[] uuidList = uploadDTO.getUuidList();
        if((file==null||file.length==0)&&(uuidList==null||uuidList.length==0)){
            flag=contractManageService.deleteFile(uploadDTO);
        }else {
            flag = contractManageService.uploadContractManageFile(uploadDTO);
        }
        if(flag){
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        }else {
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
        }
        return res;
    }
    /**
     * 文件查看
     * @return
     */
    @RequestMapping("/getFileExt")
    public WeixinResult getFileExt(@RequestBody UploadDTO uploadDTO){
         WeixinResult res=new WeixinResult();
        Integer orderid = uploadDTO.getOrderid();
        String type = uploadDTO.getType();
        String filetypeext = uploadDTO.getFiletypeext();
        if(StringUtil.isNullorEmpty(filetypeext)||orderid==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        List<CBPOrderFileExt> listExt=orderFileExtService.selectFileExt(uploadDTO);
        for(int i=0;i<listExt.size();i++){
            String fileuuid = listExt.get(i).getFileuuid();
            if(!StringUtil.isNullorEmpty(fileuuid)){
                String url = getUrl(fileuuid);
                listExt.get(i).setUrl(url);
            }
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("fileList",listExt);
        CBPCar car=carService.selectCarByOrderId(uploadDTO.getOrderid());
        if(car!=null){
            jsonObject.put("carType",car.getCardofo());
        }else {
            jsonObject.put("carType","C");
        }
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(jsonObject);
        return res;
    }
    public String getUrl(String uuid){
        String downloadUrl = "";
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String sign = FddEncryptTool.sha1(EncodeUtil.getMD5(
                    systemConfig.getProperty("fadada.appSecret").getBytes()) + timeStamp);
            FileAddressDTO fileAddressDTO = new FileAddressDTO();
            fileAddressDTO.setUuid(uuid);
            fileAddressDTO.setSign(sign);
            //fileAddressDTO.setSuffix(".pdf");
            fileAddressDTO.setTimestamp(timeStamp);
            JSONObject getJSON = JSONObject.fromObject(fileAddressDTO);
            String getDocument = getJSON.toString();
            String response = HttpUtil
                    .postbody(systemConfig.getProperty("contract.url")+"upload/getFileViewUrl.do", getDocument);
            System.out.println(response);
            JSONObject getObject = JSONObject.fromObject(response);
            boolean isSuccess = (boolean) getObject.get("isSuccess");
            if(isSuccess){
                Object data= getObject.get("data");
                JSONObject getData=JSONObject.fromObject(data);
                return  (String) getData.get("viewUrl");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return downloadUrl;
    }
}
