/*
 * @(#)InsuranceLimitServiceImpl.java       2018年2月6日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.insurance.service.impl;

import com.blchina.insurance.dao.BDLInsuranceLimitMapper;
import com.blchina.insurance.dto.InsuranceDTO;
import com.blchina.insurance.model.BDLInsuranceLimit;
import com.blchina.insurance.service.interfaces.InsuranceLimitService;
import com.blchina.insurance.service.interfaces.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 保额操作
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("InsuranceLimitService")
public class InsuranceLimitServiceImpl implements InsuranceLimitService{

    @Autowired
    private BDLInsuranceLimitMapper  limitMapper;
    @Override
    public List<BDLInsuranceLimit> getInsuranceLimitList(InsuranceDTO insuranceDTO) {
        return limitMapper.getInsuranceLimitList(insuranceDTO);
    }

    @Override
    public List<BDLInsuranceLimit> getAllLimit() {
        return limitMapper.getAllLimit();
    }
}
