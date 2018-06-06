/*
 * @(#)ContractManageServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.dto.*;
import com.blchina.cbp.model.*;
import com.blchina.cbp.service.interfaces.*;
import com.blchina.cbp.util.UploadUtil;
import com.blchina.common.util.base64.Base64Util;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.file.FileUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;
import net.sf.json.JSONObject;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *合同管理Service
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("ContractManageService")
public class ContractManageServiceImpl implements ContractManageService {
    @Autowired
    protected Properties systemConfig;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    ConstractService constractService;
    @Autowired
    OutpocketService outpocketService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderFileService orderFileService;
    @Autowired
    OrderFileExtService orderFileExtService;
    /**
     * 上传合同管理系统
     * @param uploadDTO
     * @return
     */
    @Override
    public Boolean uploadContractManageFile(UploadDTO uploadDTO) {
        try {
            String[] fileString = uploadDTO.getFile();
            List<File> list=new ArrayList<File>();
            String titleId=null;
            String storeId=null;
            if(uploadDTO.getOrderid()!=null){
                CBPOrder order = orderService.getOrderById(uploadDTO.getOrderid());
                if(order==null){
                    return false;
                }
                uploadDTO.setBuyeridcardnum(order.getBuyeridcardnum());
                uploadDTO.setCustomerid(order.getCustomerid());
                uploadDTO.setSapOrderId(order.getSaporderid());
                uploadDTO.setOrganizecode(order.getOrganizecode());
                uploadDTO.setEmployeeid(order.getEmployeeid());
                uploadDTO.setIdcardnum(order.getIdcardnum());
                uploadDTO.setBuyeridcardnum(order.getBuyeridcardnum());
                uploadDTO.setCustomerid(order.getCustomerid());
                storeId=order.getBrandid();
                if(storeId==null||storeId.equals("")){
                    storeId="2002";
                }
            }
            if(uploadDTO.getEmployeeid()!=null){
                titleId = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeTitleId", "{\"employeeid\":" + uploadDTO.getEmployeeid() + "}");
                if(titleId==null||titleId.equals("")){
                    titleId="1";
                }
            }
            if(fileString!=null){
                for(int i=0;i<fileString.length;i++){
                    File file=FileUtil.GenerateImage(fileString[i],systemConfig.getProperty("file.url.image")+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+i+".png","image");
                    list.add(file);
                }
            }
            List<String> urllist=new ArrayList<String>();
            if(uploadDTO.getUuidList()!=null){
                for(int i=0;i<uploadDTO.getUuidList().length;i++){
                    urllist.add(getUrl(uploadDTO.getUuidList()[i]));
                }
            }
            String result = UploadUtil.uploadByFile(systemConfig.getProperty("contract.url")+"upload/fileUpload.do",list,urllist,uploadDTO,titleId,storeId,systemConfig.getProperty("fadada.appSecret"));
            JSONObject getJson=JSONObject.fromObject(result);
            if(String.valueOf(getJson.get("code")).equals("200")){
                getJson.remove("data");
                FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
                if(String.valueOf(fileuuidList.getCode()).equals("200")){
                    Boolean aBoolean = updateUUID(uploadDTO, fileuuidList);
                    return aBoolean;
                }

            }

        } catch (Exception var11) {
            var11.printStackTrace();
            throw new RuntimeException(var11);
        }
        return false;
    }

    /**
     * 订单上传
     * @param orderDTO
     * @return
     */
    @Override
    public Boolean uploadOrderData(CBPOrder order,OrderDTO orderDTO) {
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String secret=systemConfig.getProperty("fadada.appSecret");
            ContractManagerOrderDTO  cmod=new ContractManagerOrderDTO();
            List<ContractManagerOrderDetailDTO> list=new ArrayList<ContractManagerOrderDetailDTO>() ;
            ContractManagerOrderDetailDTO cmodd=new ContractManagerOrderDetailDTO();
            String sign = FddEncryptTool.sha1(EncodeUtil.getMD5(secret.getBytes())+timeStamp);
            cmodd.setCustomerName(order.getCustomername());
            cmodd.setOrderStatus(order.getOrderstatus());
            cmodd.setOrderDate(order.getOrderdate());
            cmodd.setCarType(order.getCartype());
            cmodd.setVinNo(order.getRealvinno());
            if(!StringUtil.isNullorEmpty(order.getDelivertime())&&order.getDelivertime().length()>10){
                cmodd.setPayedTime(order.getDelivertime());
            }
            if(orderDTO!=null){
                cmodd.setEmployeeId(String.valueOf(orderDTO.getEmployeeid()));
                cmodd.setOrderId(orderDTO.getSaporderid());
                cmodd.setPaySum(orderDTO.getDepositSum());
                cmodd.setPayer(orderDTO.getCustomername());
                cmodd.setPhoneNumber(orderDTO.getPhonenumber());
                cmodd.setIdCardNum(orderDTO.getIdcardnum());
                cmodd.setStoreId(orderDTO.getBrandid());
            }else {
                cmodd.setEmployeeId(String.valueOf(order.getEmployeeid()));
                cmodd.setOrderId(order.getSaporderid());
                cmodd.setPhoneNumber(order.getPhonenumber());
                cmodd.setIdCardNum(order.getIdcardnum());
                cmodd.setStoreId(order.getBrandid());

            }

            String employeeName=null;
            if(order.getEmployeeid()!=null){
                employeeName = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeNameById", "{\"employeeid\":" + order.getEmployeeid() + "}");
                if(employeeName==null||employeeName.equals("")){
                    employeeName="111";
                }
            }
            cmodd.setEmployeeName(employeeName);
            cmodd.setCreateTime(timeStamp);
            cmod.setTimestamp(timeStamp);
            list.add(cmodd);
            cmod.setOrderInfoList(list);
            cmod.setSign(sign);
            JSONObject jsonObject=JSONObject.fromObject(cmod);
            String result = HttpUtil.postbody(systemConfig.getProperty("contract.url") + "upload/obtainOrderData.do", jsonObject.toString());
            JSONObject getJson=JSONObject.fromObject(result);
            String code=String.valueOf(getJson.get("code"));
            if(code.equals("200"))
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;

        }
        return false;
    }

    @Override
    public Boolean updateOrderStauts(CBPOrder order) {
        String secret=systemConfig.getProperty("fadada.appSecret");
        try {
            orderService.updateOrderBySapId(order);
            String timeStamp = HttpsUtil.getTimeStamp();
            String sign = FddEncryptTool.sha1(EncodeUtil.getMD5(secret.getBytes())+timeStamp+"");
            JSONObject jsonObject=new JSONObject();
            JSONObject orderStatusList=new JSONObject();
            orderStatusList.put("orderId",order.getSaporderid());
            orderStatusList.put("orderStatus",order.getOrderstatus());
            orderStatusList.put("storeId",order.getBrandid());
            jsonObject.put("timestamp",timeStamp);
            jsonObject.put("sign",sign);
            JSONObject[] list=new JSONObject[]{orderStatusList};
            jsonObject.put("orderUpdateStatusList",list);
            String result = HttpUtil.postbody(systemConfig.getProperty("contract.url") + "upload/updateOrderStatus.do", jsonObject.toString());
            JSONObject getJson=JSONObject.fromObject(result);
            String code=String.valueOf(getJson.get("code"));
            if(code.equals("200"))
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;

        }

        return false;
    }

    @Override
    public Boolean deleteFile(UploadDTO uploadDTO) {
        String type=uploadDTO.getType();
        String filetypeext = uploadDTO.getFiletypeext();
        if(!StringUtil.isNullorEmpty(filetypeext)){
            CBPOrderFileExt extFile=new CBPOrderFileExt();
            extFile.setFiletype(uploadDTO.getType());
            extFile.setOrderid(uploadDTO.getOrderid());
            extFile.setFiletypeext(uploadDTO.getFiletypeext());
            orderFileExtService.deleteFile(extFile);
        }else {
            if(type.equals(CBPConstant.FileEnum.CUSTOMERIDCARD.getType())||type.equals(CBPConstant.FileEnum.BUYERIDCARD.getType())||type.equals(CBPConstant.FileEnum.BUSINLICENSE.getType())||type.equals(CBPConstant.FileEnum.ATTORNEY.getType())) {
                orderFileService.insertOrUpdateFile(uploadDTO,null);
                return true;
            }else if(type.equals(CBPConstant.FileEnum.CONTRACT.getType())){//合同上传
                CBPContract cc=new CBPContract();
                cc.setContractid(uploadDTO.getContractid());
                cc.setContractUUID(null);
                constractService.updateConstract(cc);
                return true;

            }else  if (type.equals(CBPConstant.FileEnum.ADVANCEDPAID.getType())){//垫付证明上传
                Integer orderid = uploadDTO.getOrderid();
                List<CBPOutpocket> cbpOutpockets = outpocketService.selectOutpocket(String.valueOf(orderid));
                for(int i=0;i<cbpOutpockets.size();i++){
                    outpocketService.deleteOutpocket(cbpOutpockets.get(i));
                }
                return true;
            }
        }
        return false;
    }

    public Boolean updateUUID(UploadDTO uploadDTO, FileListDTO fileuuidList){
        String type=uploadDTO.getType();
        String uuid=fileuuidList.getObj()[0].getUuid();
        if(!StringUtil.isNullorEmpty(uploadDTO.getFiletypeext())){//扩展类型上传

             CBPOrderFileExt extFile=new CBPOrderFileExt();
               extFile.setFiletype(uploadDTO.getType());
               extFile.setOrderid(uploadDTO.getOrderid());
               extFile.setFiletypeext(uploadDTO.getFiletypeext());
            orderFileExtService.saveOrupdateFileExt(extFile,fileuuidList);
            return true;
        }else{
            if(type.equals(CBPConstant.FileEnum.CUSTOMERIDCARD.getType())||type.equals(CBPConstant.FileEnum.BUYERIDCARD.getType())||type.equals(CBPConstant.FileEnum.BUSINLICENSE.getType())||type.equals(CBPConstant.FileEnum.ATTORNEY.getType())) {
                Integer customerinfoid = uploadDTO.getCustomerinfoid();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("customerid",uploadDTO.getCustomerid());
                jsonObject.put("buyeridcardnum",uploadDTO.getBuyeridcardnum());
                jsonObject.put("organizecode",uploadDTO.getOrganizecode());
                jsonObject.put("type",uploadDTO.getType());
                jsonObject.put("uuid",uuid);
                int i =orderFileService.insertOrUpdateFile(uploadDTO,uuid);
                if(i==0){
                    return false;
                }
                try {
                    String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/customerInfo/updateCustomerInfoUUID",jsonObject.toString());
                    if(result.equals(CBPConstant.CODE_SUCCESS)){
                        return true;
                    }else {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }else if(type.equals(CBPConstant.FileEnum.CONTRACT.getType())){//合同上传
                CBPContract cc=new CBPContract();
                cc.setContractid(uploadDTO.getContractid());
                cc.setContractUUID(uuid);
                constractService.updateConstract(cc);
                return true;

            }else  if (type.equals(CBPConstant.FileEnum.ADVANCEDPAID.getType())){//垫付证明上传
                Integer orderid = uploadDTO.getOrderid();
                List<CBPOutpocket> cbpOutpockets = outpocketService.selectOutpocket(String.valueOf(orderid));
                for(int i=0;i<cbpOutpockets.size();i++){
                    outpocketService.deleteOutpocket(cbpOutpockets.get(i));
                }
                for(int i=0;i<fileuuidList.getObj().length;i++){
                    CBPOutpocket outpocket=new CBPOutpocket();
                    outpocket.setOrderid(uploadDTO.getOrderid());
                    outpocket.setOutpocketuuid(fileuuidList.getObj()[i].getUuid());
                    outpocketService.saveOutpocket(outpocket);
                }
                return true;
            }

        }
        return true;
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
                    .postbody(systemConfig.getProperty("contract.url")+"upload/obtainFileAddress.do", getDocument);
            System.out.println(response);
            JSONObject getObject = JSONObject.fromObject(response);
            boolean isSuccess = (boolean) getObject.get("isSuccess");
            if(isSuccess){
                return  (String)getObject.get("obj");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return downloadUrl;
    }
}
