/*   
 * @(#)CardController.java       2018年2月2日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.card.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.card.datamodel.CBPConstant;
import com.blchina.card.datamodel.weixin.WeixinResult;
import com.blchina.card.dto.BDLCardListDTO;
import com.blchina.card.dto.BatchDeleteDTO;
import com.blchina.card.dto.CardDTO;
import com.blchina.card.dto.CardDetailsDTO;
import com.blchina.card.dto.PagePojo;
import com.blchina.card.model.BDLCard;
import com.blchina.card.model.BDLTask;
import com.blchina.card.service.interfaces.CardService;
import com.blchina.card.service.interfaces.TaskService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.sf.json.JSONObject;

/**
 * 卡片操作controller
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/card")
public class CardController {


    @Autowired
    private CardService cardService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private Properties systemConfig;


    @RequestMapping("/getCardListByCardDTO")
    public String getCardListByCardDTO(@RequestBody CardDTO cardDTO) {
        PageHelper.startPage(cardDTO.getCurrentPage(), 5);
        List<BDLCard> list = cardService.selectCardListByCustomerName(cardDTO);
        PagePojo pojo = new PagePojo(list);
        JSONObject getCardJSON = JSONObject.fromObject(pojo);
        return getCardJSON.toString();
    }


    @RequestMapping("/saveCard")
    public WeixinResult saveCard(@RequestBody BDLCard card) {
        WeixinResult res = new WeixinResult();
        card.setCardstatus(CBPConstant.CardStatus.WAITTING.getType());
        int i = cardService.saveCard(card);
        if (i == 1) {
            BDLTask task = new BDLTask();
            task.setCardid(card.getCardid());
            task.setTaskstatus(CBPConstant.CardStatus.WAITTING.getType());
            //销售顾问 购车下单
            if (card.getCardtype().equals(CBPConstant.CardTypeEnum.BUYCARORDER.getType())) {
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.BUYCARORDERFORCUSTROMER.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.BUYCARORDERFORCUSTROMER.getType());
                taskService.save(task);
                //销售顾问 订单维护
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.ORDERMAINTENANCE.getType())) {
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.ORDERMAINTENANCEFORCUSTROMER.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.ORDERMAINTENANCEFORCUSTROMER.getType());
                taskService.save(task);
                //销售顾问 签订合同
            } else if(card.getCardtype().equals(CBPConstant.CardTypeEnum.SIGNCONTRACT.getType())){
               	//销售顾问 办理贷款
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.TRANSACTLOAN.getType())) {
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.TOUCHFINANCIALMANAGER.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.TOUCHFINANCIALMANAGER.getType());
                taskService.save(task);
                //销售顾问 车辆置换
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.CARPERMUTE.getType())) {
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.APPOINTMENTUSERDCARASSESS.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.APPOINTMENTUSERDCARASSESS.getType());
                taskService.save(task);
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.CARPERMUTEFORCUSTROMER.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.CARPERMUTEFORCUSTROMER.getType());
                taskService.save(task);
                //销售顾问 办理车险
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.TRANSACTINSURANCE.getType())) {
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.TRANSACTINSURANCEFORCUSTROMER.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.TRANSACTINSURANCEFORCUSTROMER.getType());
                taskService.save(task);
                //销售顾问 验车上牌
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.CHECKCARBAND.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.COLLECTPAYMENTPURCHASETAXDATA.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.COLLECTPAYMENTPURCHASETAXDATA.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.COLLECTTRANSACTTEMPORARYLICENSEDATA.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.COLLECTTRANSACTTEMPORARYLICENSEDATA.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.COLLECTCHECKCARBANDDATA.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.COLLECTCHECKCARBANDDATA.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.APPOINTMENTCHECKCARBAND.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.APPOINTMENTCHECKCARBAND.getType());
               taskService.save(task);
               //销售顾问 加装精品
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.INSTALLBOUTIQUE.getType())) {
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.APPOINTMENTINSTALLBOUTIQUETIME.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.APPOINTMENTINSTALLBOUTIQUETIME.getType());
                taskService.save(task);
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
                taskService.save(task);
                task.setDescription(CBPConstant.TaskTypeDescriptionEnum.CUSTOMERAFFIRM.getType());
                task.setTasktype(CBPConstant.TaskTypeEnum.CUSTOMERAFFIRM.getType());
                taskService.save(task);
                //集团财务（财务收银）确认收定金
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMCOLLECTDEPOSIT.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
               	//集团财务（财务收银）确认收尾款
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMCOLLECTBALANCEPAYMENT.getType())) {
              task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
              task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
              taskService.save(task);
              	//集团财务（财务收银）确认收全款
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMCOLLECTFULL.getType())) {
              task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
              task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
              taskService.save(task);
              	//集团财务（财务收银）确认金融分期收款
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMCOLLECTFINANCIALSTAGE.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
             //集团财务（财务收银）确认二手车收款
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMCOLLECTUSERDCAR.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
               //承保顾问	办理车险
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.TRANSACTINSURANCEFORINSURANCE.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.TRANSACTINSURANCEFORCUSTROMER.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.TRANSACTINSURANCEFORCUSTROMER.getType());
               taskService.save(task);
               //评估师	车辆置换
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.CARPERMUTEFORASSESSMENT.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.APPOINTMENTUSERDCARASSESS.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.APPOINTMENTUSERDCARASSESS.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SETUPUSERDCARASSESSPRICE.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SETUPUSERDCARASSESSPRICE.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.CARPERMUTEFORCUSTROMER.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.CARPERMUTEFORCUSTROMER.getType());
               taskService.save(task);
               //二手车财务	确认收款
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMCOLLECTMONEY.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
               //金融经理	办理金融分期
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.TRANSACTFINANCIALSTAGE.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.APPOINTMENTFAMILYVISIT.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.APPOINTMENTFAMILYVISIT.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.TRANSACTFINANCIALSTAGEFORCUSTROMER.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.TRANSACTFINANCIALSTAGEFORCUSTROMER.getType());
               taskService.save(task);
               //精品顾问	准备精品
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.PREPAREBOUTIQUE.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.PREPAREBOUTIQUEFORCUSTROMER.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.PREPAREBOUTIQUEFORCUSTROMER.getType());
               taskService.save(task);
             //精品顾问	加装精品
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.INSTALLBOUTIQUEFORBOUTIQUE.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SELECTCUSTOMERDATA.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SELECTCUSTOMERDATA.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SELECTAPPOINTMENTINSTALLBOUTIQUETIME.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SELECTAPPOINTMENTINSTALLBOUTIQUETIME.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.CUSTOMERAFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.CUSTOMERAFFIRM.getType());
               taskService.save(task);
               //验车专员   验车上牌
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.CHECKCARBANDFORCHECK.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SELECTCUSTOMERPICKNUMWAY.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SELECTCUSTOMERPICKNUMWAY.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SELECTAPPOINTMENTCHECKCARBANDTIME.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SELECTAPPOINTMENTCHECKCARBANDTIME.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SELECTCUSTOMERDATA.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SELECTCUSTOMERDATA.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.TRANSACTTEMPORARYLICENSEFORCUSTROMER.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.TRANSACTTEMPORARYLICENSEFORCUSTROMER.getType());
               taskService.save(task);
               //验车专员   缴纳购置税
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.PAYMENTPURCHASETAX.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SELECTCUSTOMERDATA.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SELECTCUSTOMERDATA.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.PAYMENTPURCHASETAXFORCUSTROMER.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.PAYMENTPURCHASETAXFORCUSTROMER.getType());
               taskService.save(task);
             //验车专员   办理临牌
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.TRANSACTTEMPORARYLICENSE.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SELECTTRANSACTTEMPORARYLICENSENUM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SELECTTRANSACTTEMPORARYLICENSENUM.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.SELECTCUSTOMERDATA.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.SELECTCUSTOMERDATA.getType());
               taskService.save(task);
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.TRANSACTTEMPORARYLICENSEFORCUSTROMER.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.TRANSACTTEMPORARYLICENSEFORCUSTROMER.getType());
               taskService.save(task);
               //计划员  确认车辆排产
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMSCHEDULED.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
             //计划员  确认车辆发送
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMSENT.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
             //计划员  确认车辆到港
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMPORTED.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
             //计划员  确认车辆在途
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMROADING.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
             //计划员  确认车辆到店
            } else if (card.getCardtype().equals(CBPConstant.CardTypeEnum.AFFIRMARRIVED.getType())) {
               task.setDescription(CBPConstant.TaskTypeDescriptionEnum.AFFIRM.getType());
               task.setTasktype(CBPConstant.TaskTypeEnum.AFFIRM.getType());
               taskService.save(task);
            }
        }
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping("/queryCardByEmployee")
    public WeixinResult queryCardByEmployee(@RequestBody CardDTO cardDTO) {
        WeixinResult res = new WeixinResult();
        BDLCard card = new BDLCard();
        card.setEmployeeid(cardDTO.getEmployeeid());
        card.setCardstatus(cardDTO.getCardStatus());
        PageHelper.startPage(cardDTO.getCurrentPage(), 5);
        List<BDLCard> cardList = cardService.queryCardByEmployee(card);
        PagePojo pojo = new PagePojo(cardList);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(pojo);
        return res;
    }

    /**
     * 更新卡片状态
     *
     * @param card
     * @return
     */
    @RequestMapping("/updateCardStatusDoing")
    public WeixinResult updateCardStatusDoing(@RequestBody BDLCard card) {
        WeixinResult res = new WeixinResult();
        Integer cardid = card.getCardid();
        if (cardid == null) {
            res.setCode(CBPConstant.CODE_NULL_EMP);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        BDLCard bdlCard = cardService.selectCardByCardId(cardid);
        if (bdlCard == null) {
            res.setCode(CBPConstant.CODE_NULL_EMP);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        if (bdlCard.getCardstatus().equals(CBPConstant.CardStatus.WAITTING.getType())) {
            card.setCardstatus(CBPConstant.CardStatus.DOING.getType());
            cardService.updateCardStatus(card);
        }
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 更新卡片状态
     *
     * @param card
     * @return
     */
    @RequestMapping("/updateCardStatusComplete")
    public WeixinResult updateCardStatusComplete(@RequestBody BDLCard card) {
        WeixinResult res = new WeixinResult();
        Integer cardid = card.getCardid();
        if (cardid == null) {
            res.setCode(CBPConstant.CODE_NULL_EMP);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        BDLCard bdlCard = cardService.selectCardByCardId(cardid);
        if (bdlCard == null) {
            res.setCode(CBPConstant.CODE_NULL_EMP);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        card.setCardstatus(CBPConstant.CardStatus.COMPLETE.getType());
        cardService.updateCardStatus(card);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping("/getCardDetailsByCardId")
    public String getCardDetailsByCardId(@RequestBody BDLCard bdlCard) {
        CardDetailsDTO cdd = new CardDetailsDTO();
        List<BDLTask> list = taskService.selectTaskListByCardId(bdlCard);
        cdd.setTaskList(list);
        JSONObject getCardJSON = JSONObject.fromObject(cdd);
        return getCardJSON.toString();
    }

    @RequestMapping("/getCardByCardId")
    public String getCardByCardId(@RequestBody BDLCard bdlCard) {
        BDLCard card = cardService.selectCardByCardId(bdlCard.getCardid());
        JSONObject getCardJSON = JSONObject.fromObject(card);
        return getCardJSON.toString();
    }


    @RequestMapping("/getCardByOrderIdAndCardType")
    public String getCardByOrderIdAndCardType(@RequestBody BDLCard bdlCard) {
        BDLCard card = cardService.getCardByOrderIdAndCardType(bdlCard);
        JSONObject getCardJSON = JSONObject.fromObject(card);
        return getCardJSON.toString();
    }
    
    @RequestMapping("/updateCardTime")
    public String updateCardTime(@RequestBody BDLCard bdlCard) {
        int status = cardService.updateCardById(bdlCard);
        if (status == 0) {
            return "error";
        } else {
            return "success";
        }
    }


    /**
     * 更新卡片状态2.0(验车专员批量)
     *
     * @param card
     * @return
     * @throws Exception
     */
    @RequestMapping("/batchForCheckCarPerson")
    public WeixinResult batchForCheckCarPerson(@RequestBody BDLCardListDTO list) throws Exception {
        WeixinResult res = new WeixinResult();
        res.setCode(CBPConstant.CODE_NULL_PARAM);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        if (list.getBdlCardList().isEmpty()) {
            return res;
        }
        //验车专员
        boolean flag = cardService.batchUpdateCardStatus(list.getBdlCardList());
        if (flag) {
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        }
        return res;
    }


    @RequestMapping("/batchDeleteCard")
    public WeixinResult batchDeleteCard(@RequestBody JSONObject jsonObject) {
        WeixinResult res = new WeixinResult();
        res.setCode(CBPConstant.CODE_NULL_PARAM);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        String string = jsonObject.getString("cardid");
        if (string != null) {
            int status = cardService.batchDeleteCard(string);
            if (status != 0) {
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            }
        }
        return res;
    }

    @RequestMapping("/updateCardTaskList")
    public WeixinResult updateCardTaskList(@RequestBody BatchDeleteDTO bdd) {
        WeixinResult res = new WeixinResult();
        res.setCode(CBPConstant.CODE_NULL_PARAM);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        if (bdd != null && !bdd.getTaskList().isEmpty()) {
           WeixinResult flag = taskService.updateCardTaskList(bdd.getTaskList());
           return flag;
        }
        return res;
        
    }
   /**
    * 
    * {方法的功能/动作描述}    根据子任务描述更改为完成状态
    *    
    * @param      {引入参数名}   {引入参数说明}   
    * @return      {返回参数名}   {返回参数说明}    
    * @exception   {说明在某情况下,将发生什么异常}   
    *
    * @author zhangtong
    * @param bdlCard   订单id
    * @return 
    * @since JDK 1.7
    */
    @RequestMapping("/updateTaskStatusComplete")
    public WeixinResult updateTaskStatusComplete(@RequestBody JSONObject jsonObject){
    	WeixinResult res = new WeixinResult();
    	if (jsonObject.get("orderid")==null||jsonObject.get("tasktype")==null) {
    		res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
    		return res;
		}
    	Integer orderid = (Integer) jsonObject.get("orderid");
    	Integer employeeid = (Integer) jsonObject.get("employeeid");
    	String tasktype =  jsonObject.get("tasktype").toString();
    	BDLCard bdlCard = new BDLCard();
    	bdlCard.setOrderid(orderid);
    	bdlCard.setEmployeeid(employeeid);
		List<BDLCard> cardList = cardService.selectCardByOrderIdAndEmployeeId(bdlCard);
		if (cardList.size()==0) {
    		res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
		}
		for (BDLCard card : cardList) {
			BDLTask task = new BDLTask();
	    	task.setCardid(card.getCardid());
	    	if (tasktype.equals(CBPConstant.TaskTypeEnum.INSTALLBOUTIQUEFORCUSTOMER.getType())) {
	    		task.setTasktype(CBPConstant.TaskTypeEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
	    		task.setDescription(CBPConstant.TaskTypeDescriptionEnum.INSTALLBOUTIQUEFORCUSTOMER.getType());
	    		task.setTaskstatus(CBPConstant.CardStatus.COMPLETE.getType());
			}else if (tasktype.equals(CBPConstant.TaskTypeEnum.APPOINTMENTFAMILYVISIT.getType())) {
				task.setTasktype(CBPConstant.TaskTypeEnum.APPOINTMENTFAMILYVISIT.getType());
	    		task.setDescription(CBPConstant.TaskTypeDescriptionEnum.APPOINTMENTFAMILYVISIT.getType());
	    		task.setTaskstatus(CBPConstant.CardStatus.COMPLETE.getType());
			}else if (tasktype.equals(CBPConstant.TaskTypeEnum.APPOINTMENTINSTALLBOUTIQUETIME.getType())) {
				task.setTasktype(CBPConstant.TaskTypeEnum.APPOINTMENTINSTALLBOUTIQUETIME.getType());
	    		task.setDescription(CBPConstant.TaskTypeDescriptionEnum.APPOINTMENTINSTALLBOUTIQUETIME.getType());
	    		task.setTaskstatus(CBPConstant.CardStatus.COMPLETE.getType());
			}
	    	int i = taskService.updateTaskStatusComplete(task);
		}
		
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		return res;
    	
    }
    /**
     * 
     * {方法的功能/动作描述}    判断该任务下的子任务是否都为完成
     *    
     * @param      {引入参数名}   {引入参数说明}   
     * @return      {返回参数名}   {返回参数说明}    
     * @exception   {说明在某情况下,将发生什么异常}   
     *
     * @author zhangtong
     * @param bdlCard
     * @return 
     * @since JDK 1.7
     */
    @RequestMapping("/getTaskStatusByCardId")
    public WeixinResult getTaskStatusByCardId(@RequestBody BDLCard bdlCard){
    	WeixinResult res = new WeixinResult();
    	Integer cardid = bdlCard.getCardid();
    	List<BDLTask> taskList = taskService.selectTaskListByCardId(bdlCard);
    	for (BDLTask bdlTask : taskList) {
			if (!bdlTask.equals(CBPConstant.CardStatus.COMPLETE)) {
				res.setCode(CBPConstant.CODE_FAILURE);
		        res.setMessage(CBPConstant.MESSAGE_FAILURE);
		        return res;
			}
		}
    	res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		return res;
    }
}
