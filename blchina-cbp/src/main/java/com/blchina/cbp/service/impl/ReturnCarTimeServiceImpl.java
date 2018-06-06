/*
 * @(#)ReturnCarTimeServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPReturnCarTimeMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.dto.CBPReturnCarTimeDTO;
import com.blchina.cbp.dto.ReturnCarTimeDTO;
import com.blchina.cbp.model.CBPReturnCarTime;
import com.blchina.cbp.service.interfaces.ReturnCarTimeService;
import com.blchina.common.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订车时间设置
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("ReturnCarTimeService")
class ReturnCarTimeServiceImpl implements ReturnCarTimeService {
    @Autowired
    private CBPReturnCarTimeMapper cbpReturnCarTimeMapper;

    /**
     * 获取员工预约天数
     *
     * @param employeeid
     * @param today
     * @return
     */
    @Override
    public List<CBPReturnCarTime> getEmployeeReturnTime(Integer employeeid, String today) {
        CBPReturnCarTime cbpReturnCarTime = new CBPReturnCarTime();
        cbpReturnCarTime.setEmployeeid(employeeid);
        cbpReturnCarTime.setDate(today);
        return cbpReturnCarTimeMapper.getEmployeeReturnTime(cbpReturnCarTime);
    }

    /**
     * 获取客户预约天数
     *
     * @param employeeid
     * @param today
     * @return
     */
    @Override
    public List<CBPReturnCarTime> getCustomerReturnTime(Integer employeeid, String today, String endDay) {
        CBPReturnCarTimeDTO cbpReturnCarTime = new CBPReturnCarTimeDTO();
        cbpReturnCarTime.setEmployeeid(employeeid);
        cbpReturnCarTime.setDate(today);
        cbpReturnCarTime.setEndDay(endDay);
        return cbpReturnCarTimeMapper.getCustomerReturnTime(cbpReturnCarTime);
    }

    /**
     * 设置预约时间
     *
     * @param returnCarTimeList
     */
    @Override
    public void addOrUpdateReturnTime(List<CBPReturnCarTime> returnCarTimeList) {
        for (int i = 0; i < returnCarTimeList.size(); i++) {
            if (returnCarTimeList.get(i).getRetcarid() == null) {
                cbpReturnCarTimeMapper.insert(returnCarTimeList.get(i));
            } else {
                cbpReturnCarTimeMapper.updateByPrimaryKeySelective(returnCarTimeList.get(i));
            }
        }
    }

    @Override
    public String setCustomerTime(CBPReturnCarTime returnTime) {
        String flag = "1";
        returnTime.setStatus(String.valueOf(CBPConstant.TimePeriodStatusEnum.RESERVED.getType()));
        Integer retcarid = returnTime.getRetcarid();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            List<CBPReturnCarTime> getTime = cbpReturnCarTimeMapper.gettheReturnTime(returnTime);
            if(getTime.size()!=0){
                String status = getTime.get(0).getStatus();
                if(!StringUtil.isNullorEmpty(status)){
                    if(status.equals(String.valueOf(CBPConstant.TimePeriodStatusEnum.NONRESERVE.getType()))||status.equals(String.valueOf(CBPConstant.TimePeriodStatusEnum.RESERVED.getType()))){
                        return  null;
                    }
                }
            }
            List<CBPReturnCarTime> returnTimeByOrder = cbpReturnCarTimeMapper.getReturnTimeByOrder(returnTime.getOrderid());
            if(returnTimeByOrder.size()!=0){
                flag="2";
            }
            for (int i = 0; i < returnTimeByOrder.size(); i++) {
                String getDate = returnTimeByOrder.get(i).getDate();
                Date parse = sdf.parse(getDate);
                if(parse.getTime()>date.getTime()){

                       returnTimeByOrder.get(i).setStatus(String.valueOf(CBPConstant.TimePeriodStatusEnum.CANRESERVE.getType()));
                        returnTimeByOrder.get(i).setOrderid(null);
                        returnTimeByOrder.get(i).setCustomerid(null);
                        cbpReturnCarTimeMapper.updateByPrimaryKey(returnTimeByOrder.get(i));

                }
            }
            if (retcarid == null) {
                cbpReturnCarTimeMapper.insert(returnTime);
            } else {
                cbpReturnCarTimeMapper.updateByPrimaryKeySelective(returnTime);
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    @Override
    public List<ReturnCarTimeDTO> getDayReturnTime(CBPReturnCarTime returnTime) {
        returnTime.setStatus(String.valueOf(CBPConstant.TimePeriodStatusEnum.RESERVED.getType()));
        return cbpReturnCarTimeMapper.getDayReturnTime(returnTime);
    }

    @Override
    public List<CBPReturnCarTime> getReturnTimeByOrder(Integer orderid) {
        return cbpReturnCarTimeMapper.getReturnTimeByOrder(orderid);
    }

    @Override
    public List<CBPReturnCarTime> getEmployeeReturnTimeAll(Integer employeeid) {
        return cbpReturnCarTimeMapper.getEmployeeReturnTimeAll(employeeid);
    }

   @Override
   public CBPReturnCarTime getMaxDateByOrderId(Integer orderid) {
	  return cbpReturnCarTimeMapper.getMaxDateByOrderId(orderid);
   }
}
