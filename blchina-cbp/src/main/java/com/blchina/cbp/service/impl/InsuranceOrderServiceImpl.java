/*   
 * @(#)InsuranceOrderServiceImpl.java       2018年2月2日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import java.util.List;
import java.util.Properties;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.InsuranceOrderInfoDTO;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.InsuranceOrderExtService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPInsuranceOrderMapper;
import com.blchina.cbp.model.CBPInsuranceOrder;
import com.blchina.cbp.service.interfaces.InsuranceOrderService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/InsuranceOrderService")
public class InsuranceOrderServiceImpl implements InsuranceOrderService {
   private static Logger log = Logger.getLogger(InsuranceOrderServiceImpl.class);
   @Autowired
   private CBPInsuranceOrderMapper cbpInsuranceOrderMapper;
   @Autowired
   private WxService wxService;
   @Autowired
   protected Properties systemConfig;
   @Autowired
   private InsuranceOrderExtService extService;
   @Override
   public List<CBPInsuranceOrder> selectInsuranceOrderListByOrderId(Integer orderId) {
	  return cbpInsuranceOrderMapper.selectInsuranceOrderListByOrderId(orderId);
   }

   @Override
   public void saveOrUpdateInsuranceOrder(List<CBPInsuranceOrder> list) {
       for(int i=0;i<list.size();i++){
          if(list.get(i).getInsuranceorderid()==null|| StringUtil.isNullorEmpty(String.valueOf(list.get(i).getInsuranceorderid()))){
             cbpInsuranceOrderMapper.insert(list.get(i));
          }else {
             cbpInsuranceOrderMapper.updateByPrimaryKeySelective(list.get(i));
          }
       }
   }

   @Override
   public Boolean insuranceConfirmReceipt( BDLCard bdlCard, CBPOrder order) {
      Boolean flag=false;
      try {
         //1找到承保顾问，为承保顾问创建卡片任务，发送消息
         String brandid = order.getBrandid();
         String resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
         String[] splitOne = resultOne.split(",");
         String insuranceEmployeeId=splitOne[0];
         String insuranceEmployeeUserid="yuzhicheng";//splitOne[1];
         String contentInsurance="请为"+order.getCustomername()+"（订单编号"+order.getOrderid()+"）办理车险";
         wxService.pushCardToEmployee(insuranceEmployeeUserid,"#",contentInsurance,"车险办理");
         //创建承保顾问卡片
         BDLCard insuranceCard=new BDLCard();
         insuranceCard.setOrderid(order.getOrderid());
         insuranceCard.setEmployeeid(Integer.valueOf(insuranceEmployeeId));
         insuranceCard.setCardtype(CBPConstant.CardTaskTypeEnum.FINANCE.getType());
         insuranceCard.setCustomerid(order.getCustomerid());
         insuranceCard.setCustomername(order.getCustomername());
         JSONObject getCard=JSONObject.fromObject(insuranceCard);
         String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard",getCard.toString());
         JSONObject jsonObject=JSONObject.fromObject(result);
         WeixinResult resOne=(WeixinResult) JSONObject.toBean(jsonObject,WeixinResult.class);
         if(!resOne.getCode().equals(CBPConstant.CODE_SUCCESS)){
            log.info("创建承保顾问卡片失败");
            return false;
         }
         //2给销售顾问发送消息创建卡片任务
         //获取员工所在门店
         String resultTwo =HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserIdAndShop", "{\"employeeid\":" + order.getEmployeeid() + "}");
         String[] split = resultTwo.split(",");
         String employeeUserid=split[0];
         wxService.pushCardToEmployee(employeeUserid,"#",contentInsurance,"车险办理");
         //创建承保顾问卡片
         BDLCard  employeeCard=new BDLCard();
         employeeCard.setOrderid(order.getOrderid());
         employeeCard.setEmployeeid(Integer.valueOf(insuranceEmployeeId));
         employeeCard.setCardtype(CBPConstant.CardTaskTypeEnum.FINANCE.getType());
         employeeCard.setCustomerid(order.getCustomerid());
         employeeCard.setCustomername(order.getCustomername());
         JSONObject getCardTwo=JSONObject.fromObject(insuranceCard);
         String resultEmployee = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard",getCardTwo.toString());
         JSONObject jsonObjectEmployee=JSONObject.fromObject(resultEmployee);
         WeixinResult resTwo=(WeixinResult) JSONObject.toBean(jsonObjectEmployee,WeixinResult.class);
         if(!resTwo.getCode().equals(CBPConstant.CODE_SUCCESS)){
            log.info("创建承保顾问卡片失败");
            return false;
         }
         //3给客户发送消息
         JSONObject jsonObjectCustomer = new JSONObject();
         jsonObjectCustomer.put("customerid", order.getCustomerid());
         jsonObjectCustomer.put("brandid", order.getBrandid());
         String getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid", jsonObjectCustomer.toString());
         String[] splitCustomer = getcus.split(",");
         String openid = splitCustomer[1];
         String contentCustomer="开始为您办理车险";
         wxService.pushCardToCustomer(order.getBrandid(),openid,order.getCustomername(),contentCustomer,"#","车险提醒");
         //4更新卡片为已完成状态
         HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateCardStatusComplete","{\"cardid\":"+bdlCard.getCardid()+"}");
         return true;
      }catch (Exception e){
         log.info("确认收款失败:"+e.toString());
         flag=false;
      }
      return flag;
   }

   @Override
   public CBPInsuranceOrder getInsuranceOrder(Integer orderid) {
      return cbpInsuranceOrderMapper.getInsuranceOrder(orderid);
   }

   @Override
   public void saveOrUpdateInsuranceInfo(InsuranceOrderInfoDTO infoDTO) {
      CBPInsuranceOrder insuranceOrder = getInsuranceOrder(infoDTO.getOrderid());
      if(insuranceOrder==null){
         cbpInsuranceOrderMapper.insert(infoDTO);
      }else {
         cbpInsuranceOrderMapper.updateInsuranceByOrderId(infoDTO);
      }
      extService.saveOrUpdateInsuranceExt(infoDTO);

   }

}
