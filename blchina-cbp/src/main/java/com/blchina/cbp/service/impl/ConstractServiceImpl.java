/*
 * @(#)ConstractServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.dto.FileListDTO;
import com.blchina.cbp.dto.SapOrderDTO;
import com.blchina.cbp.dto.StatisticalDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.exception.BusinessException;
import com.blchina.cbp.service.interfaces.*;
import com.blchina.cbp.util.UploadUtil;
import com.blchina.common.util.http.HttpUtil;
import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPCloseCauseMapper;
import com.blchina.cbp.dao.CBPContractItemMapper;
import com.blchina.cbp.dao.CBPContractMapper;
import com.blchina.cbp.dao.CBPOrderMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.dto.OrderDTO;
import com.blchina.cbp.model.CBPCloseCause;
import com.blchina.cbp.model.CBPContract;
import com.blchina.cbp.model.CBPContractItem;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.common.util.string.StringUtil;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 合同操作ConstractService
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("ConstractService")
public class ConstractServiceImpl implements ConstractService {
    @Autowired
    private CBPContractMapper cbpContractMapper;
    @Autowired
    private CBPContractItemMapper cbpContractItemMapper;
    @Autowired
    private CBPOrderMapper cbpOrderMapper;
    @Autowired
    private Properties systemConfig;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AuthService authService;
    @Autowired
    private ContractItemService contractItemService;
    @Autowired
    private ContractManageService contractManageService;
    @Autowired
    private CBPCloseCauseMapper cbpCloseCauseMapper;
    @Override
    public int addConstract(CBPContract contract) {
        return cbpContractMapper.insert(contract);
    }

    @Override
    public int updateConstractByOrderId(CBPContract contract) {
        return cbpContractMapper.updateConstractByOrderId(contract);
    }

    @Override
    public int updateConstractByVinNo(String vinno) {
        return cbpContractMapper.updateConstractByVinNo(vinno);
    }
    /**
     * 根据订单id和合同状态获取合同
     * @param constract
     * @return
     */
    @Override
    public CBPContract findConstract(CBPContract constract) {
        return cbpContractMapper.findConstract(constract);
    }

    @Override
    public void updataConstractById(Integer contractid,String uuid) {
        CBPContract cbpContract=new CBPContract();
        cbpContract.setContractid(contractid);
        cbpContract.setContractstatus(String.valueOf(CBPConstant.ContractStatusEnum.FINISH.getType()));
        cbpContract.setContractUUID(uuid);
        cbpContractMapper.updateByPrimaryKeySelective(cbpContract);
    }

    @Override
    public CBPContract getMaxConstract(Integer orderid) {
        return cbpContractMapper.getMaxConstract(orderid);
    }
    @Override
    public Boolean compareContract(CBPContract constract, OrderDTO orderDTO) {
        if(constract==null){
            return false;
        }
        Float floatfirt=Float.parseFloat(constract.getTotalprice());
        Float flatlast=Float.parseFloat(orderDTO.getTotalprice());
        if(floatfirt.floatValue()!=flatlast.floatValue())
            return false;
        if(!constract.getAppearanceinterior().equals(orderDTO.getAppearanceinterior()))
            return false;
        if((constract.getDerivename()==null&&orderDTO.getDerivename()!=null)||((constract.getDerivename()!=null)&&!constract.getDerivename().equals(orderDTO.getDerivename())))
            return false;
        return true;
    }

    @Override
    public void updateConstract(CBPContract contract) {
        cbpContractMapper.updateByPrimaryKeySelective(contract);
    }

   @Override
   public int updateConstractAndContractItem(CBPContractItem cbpContractItem) {
       int status = 0;
       if(cbpContractItem.getContractstatus().equals(CBPConstant.ContractStatusEnum.FINISH.getType()+"")){
           cbpContractItem.setContractstatus(CBPConstant.ContractStatusEnum.SIGNED.getType()+"");
       }else{
           CBPContract cbpContract = cbpContractMapper.selectByPrimaryKey(cbpContractItem.getContractid());
           cbpContract.setContractstatus(cbpContractItem.getContractstatus());
           cbpContract.setContractUUID(cbpContractItem.getContractuuid());
           int key = cbpContractMapper.updateByPrimaryKey(cbpContract);
       }
       //添加CBPContractItem
       int i = cbpContractItemMapper.insertSelective(cbpContractItem);
       //修改CBPContract
       if(i!=0){
           status = 1;
       }
       return status;
   }

   @Override
   public String selectContract(String orderId) {
	  String uuid = "";
	  if(!StringUtil.isNullorEmpty(orderId)){
		 CBPContract contract = cbpContractMapper.getMaxConstract(Integer.valueOf(orderId));
		 uuid = contract.getContractid()+"";
	  }
	  return uuid;
   }

   @Override
   public int updateContract(CBPCloseCause cbpCloseCause) {
	  CBPContract contract = cbpContractMapper.selectByPrimaryKey(cbpCloseCause.getContractid());
	  int status = 0;
	  int i = 0;
	  if(contract!=null){
		 CBPCloseCause cc = cbpCloseCauseMapper.selectByContractId(cbpCloseCause.getContractid());
		 if(CBPConstant.CloseCauseTypeEnum.TYPE4.equals(cbpCloseCause.getClosetype())){
			cbpCloseCause.setDescription(cbpCloseCause.getDescription());
		 }else{
			cbpCloseCause.setDescription("");
		 }
		 if(cc!=null){
			   cbpCloseCause.setClosecauseid(cc.getClosecauseid());
			   i = cbpCloseCauseMapper.updateByPrimaryKeySelective(cbpCloseCause);
			}else{
			   i = cbpCloseCauseMapper.insertSelective(cbpCloseCause);
			}
		 contract.setContractstatus(CBPConstant.ContractStatusEnum.CLOSED.getType()+"");
		 status = cbpContractMapper.updateByPrimaryKey(contract);
		 
		 if(i==0){
			return i;
		 }
	  }
	  return status;
   }

   @Override
   public CBPOrder selectCustomerIdByContractId(String contract_id) {
	  CBPOrder cbpOrder = null;
	  CBPContract cbpContract = cbpContractMapper.selectByPrimaryKey(Integer.parseInt(contract_id));
	  if(cbpContract!=null){
		 Integer orderid = cbpContract.getOrderid();
		 cbpOrder = cbpOrderMapper.selectByPrimaryKey(orderid);
	  }
	  return cbpOrder;
   }

    @Override
    public void updateisChangeByOrderId(Integer orderid) {
        cbpContractMapper.updateisChangeByOrderId(orderid);
    }

    @Override
    public SAPResult autoSign(SapOrderDTO saporderDTO) {
        SAPResult sapResult=new SAPResult();
        OrderDTO orderDTO=saporderDTO.getRecords();
        SAPResponse response = new SAPResponse();
        response.setSAP_ID(orderDTO.getSaporderid());
        response.setBL_ID(orderDTO.getSaporderid());
        String saporderid = orderDTO.getSaporderid();
        String customer_id = null;
        if (saporderid == null) {
            response.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
            response.setDescription(CBPConstant.MESSAGE_FAILURE);
            sapResult.setRecords(response);
            return sapResult;
        }
        CBPOrder order = orderService.getOrderBySapId(saporderid);
        if (order == null) {
            response.setStatus(
                    CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
            response.setDescription(CBPConstant.ORDER_NOEXIST);
            sapResult.setRecords(response);
            return sapResult;
        }
        String signcustomerid=null;
        Integer customerid = order.getCustomerid();
        try {
                signcustomerid = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getSignStoreId", "{\"brandid\":" + order.getBrandid() +"}");
            if(signcustomerid==null||signcustomerid.equals("")){
                response.setStatus(
                        CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
                response.setDescription("公司还没有认证");
                sapResult.setRecords(response);
                return sapResult;
            }
            CBPContract constract=new CBPContract();
            constract.setOrderid(order.getOrderid());
            CBPContract getconstract=getMaxConstract(order.getOrderid());
            order.setOrderstatus(String.valueOf(CBPConstant.OrderStatusEnum.PAID.getType()));
            Boolean getflag = contractManageService.updateOrderStauts(order);
            if(!getflag){
                response.setStatus(
                        CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
                response.setDescription("同步合同管理系统失败");
                sapResult.setRecords(response);
                return sapResult;
            }
            String  docurl=authService.autosign(signcustomerid,getconstract.getContractid());
            if(docurl!=null){
                UploadDTO uploadDTO=new UploadDTO();
                uploadDTO.setEmployeeid(order.getEmployeeid());
                uploadDTO.setOrderid(order.getOrderid());
                uploadDTO.setContractid(getconstract.getContractid());
                uploadDTO.setIdcardnum(order.getIdcardnum());
                uploadDTO.setType(CBPConstant.FileEnum.CONTRACT.getType());
                uploadDTO.setSapOrderId(order.getSaporderid());
                //上传到合同管理系统
                String titleId = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeTitleId", "{\"employeeid\":" + uploadDTO.getEmployeeid() + "}");
                String respone = UploadUtil.uploadByUrl(systemConfig.getProperty("contract.url") + "upload/fileUpload.do", docurl, uploadDTO, titleId, order.getBrandid(),systemConfig.getProperty("fadada.appSecret"),"2");
                JSONObject getJson=JSONObject.fromObject(respone);
                if(!String.valueOf(getJson.get("code")).equals("200")){
                    response.setStatus(
                            CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
                    response.setDescription("文件同步合同管理系统失败");
                    sapResult.setRecords(response);
                    return sapResult;
                }
                getJson.remove("data");
                FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
                updataConstractById(getconstract.getContractid(),fileuuidList.getObj()[0].getUuid());
                CBPContractItem cbpContractItem=new CBPContractItem();
                cbpContractItem.setContractid(getconstract.getContractid());
                cbpContractItem.setContractuuid(fileuuidList.getObj()[0].getUuid());
                cbpContractItem.setContractstatus(String.valueOf(CBPConstant.ContractStatusEnum.FINISH.getType()));
                cbpContractItem.setContractdes("公司自动签署");
                cbpContractItem.setRequesttime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
                contractItemService.saveItem(cbpContractItem);
                response.setStatus(
                        CBPConstant.SAPResponseStatusEnum.SUCCESS.getType());
                response.setDescription(CBPConstant.MESSAGE_SUCCESS);
                sapResult.setRecords(response);
                return sapResult;
            }else {
                response.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
                response.setDescription(CBPConstant.MESSAGE_FAILURE);
                sapResult.setRecords(response);
                return sapResult;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(orderDTO.getSaporderid(),"自动签署失败") ;
        }
    }

    @Override
    public CBPContract findConstractById(String contract_id) {
        return cbpContractMapper.selectByPrimaryKey(Integer.valueOf(contract_id));
    }

   @Override
   public boolean cancelContract(Integer orderid) {
	   Integer cancelContractId = cbpContractMapper.cancelContract(orderid);
	   if(!StringUtil.isNullorEmpty(cancelContractId+"")){
		  boolean cancelFDDContract = cancelFDDContract(cancelContractId.toString());
		  if(cancelFDDContract){
			 //修改合同状态为  已撤销
			 CBPContract selectByPrimaryKey = cbpContractMapper.selectByPrimaryKey(cancelContractId);
			 selectByPrimaryKey.setContractstatus(CBPConstant.ContractStatusEnum.REVOCATION.getType()+"");
			 int i = cbpContractMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
			 //关闭原合同
			 int b=0;
			 List<CBPContract> contractList = cbpContractMapper.selectCBPContractListByOrderId(orderid);
			 if(contractList.isEmpty()){
				System.out.println("----------没有需要关闭的合同------------");
			 }else{
				for(CBPContract cbpContract :contractList){
				   cbpContract.setContractstatus(CBPConstant.ContractStatusEnum.CLOSED.getType()+"");
				   int a = cbpContractMapper.updateByPrimaryKeySelective(cbpContract);
				    if(a!=0){
				       b++;
				    }
				}
			 }
			 if(i!=0&&b==contractList.size()){
				return true;
			 }
		  }else{
			 System.out.println("----------撤销合同失败------------");
		  }
	   }else{
		  System.out.println("----------没有需要撤销的合同------------");
	   }
	   return false;
   }
   /**    
    * 撤销合同    
    */ 
   public boolean cancelFDDContract(String contract_id){
	  String app_id=systemConfig.getProperty("fadada.appid");
      String secret=systemConfig.getProperty("fadada.secret");
      String url=systemConfig.getProperty("fadada.cancelContract");
      String response = invokeCancelContract(app_id, secret, contract_id, url);
      JSONObject getJSON=JSONObject.fromObject(response);
      String result=(String) getJSON.get("result");
      System.out.println("撤销合同接口返回"+response);
      if(result.equals("success")){
          return true;
      }else {
          return false;
      }
   }
   
   
   public String invokeCancelContract(String app_id,String secret, String contract_id, String url) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      try {
          String timeStamp = HttpsUtil.getTimeStamp();
          String msgDigest;
          String sha1 = FddEncryptTool.sha1(app_id+ FddEncryptTool.md5Digest(timeStamp) + FddEncryptTool.sha1(secret + contract_id));
          msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
          params.add(new BasicNameValuePair("contract_id", contract_id));
          params.add(new BasicNameValuePair("app_id", app_id));
          params.add(new BasicNameValuePair("timestamp", timeStamp));
          params.add(new BasicNameValuePair("v", "2.0"));
          params.add(new BasicNameValuePair("msg_digest", msgDigest));
      } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException(e);
      }
      return HttpsUtil.doPost(url, params);
  }

   @Override
   public StatisticalDTO statisticalCloseCause(CBPCloseCause cbpCloseCause) {
	  String totalCount = cbpCloseCauseMapper.selectTotalCount();
	  String totalCountByCloseType = cbpCloseCauseMapper.selectTotalCountByCloseType(cbpCloseCause.getClosetype());
	  StatisticalDTO s = new StatisticalDTO();
	  if(Long.valueOf(totalCount)!=0 && Long.valueOf(totalCountByCloseType)!=0){
		 NumberFormat nt = NumberFormat.getPercentInstance();  
	        //设置百分数精确度2即保留两位小数  
	        nt.setMinimumFractionDigits(0);  
	        float baifen = (float)Long.valueOf(totalCountByCloseType)/(float)Long.valueOf(totalCount);
	        s.setClosetype(cbpCloseCause.getClosetype());
	        s.setPercentage(nt.format(baifen));
	        s.setTotal(totalCount.toString());
	  }
	  return s;
   }
}
