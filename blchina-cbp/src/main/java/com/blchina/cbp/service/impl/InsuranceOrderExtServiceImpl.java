/*
 * @(#)InsuranceOrderExtServiceImpl.java       2018年2月2日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPInsuranceExtMapper;
import com.blchina.cbp.dto.InsuranceOrderInfoDTO;
import com.blchina.cbp.model.CBPInsuranceExt;
import com.blchina.cbp.service.interfaces.InsuranceOrderExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 保险扩展Service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("InsuranceOrderExtService")
public class InsuranceOrderExtServiceImpl implements InsuranceOrderExtService {
    @Autowired
    private CBPInsuranceExtMapper extMapper;
    @Override
    public List<CBPInsuranceExt> getOrderExtInsurance(Integer orderid) {
        return extMapper.getOrderExtInsurance(orderid);
    }

    /**
     * 保存保险扩展
     * @param infoDTO
     */
    @Override
    public void saveOrUpdateInsuranceExt(InsuranceOrderInfoDTO infoDTO) {
        extMapper.deleteOrderExtInsurance(infoDTO.getOrderid());
        List<CBPInsuranceExt> extList = infoDTO.getExtList();
        for(int i=0;i<extList.size();i++){
            if(extList.get(i)!=null){
                extMapper.insert(extList.get(i));
            }

        }
    }
}
