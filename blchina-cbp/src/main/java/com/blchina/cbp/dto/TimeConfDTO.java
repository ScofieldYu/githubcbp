/*
 * @(#)TimeConfDTO.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.dto;

import com.blchina.cbp.model.CBPTimeConf;
import com.blchina.cbp.model.CBPTimePeriod;

import java.util.List;

/**
 *时间配置DTO
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class TimeConfDTO extends CBPTimeConf{
    private List<CBPTimePeriod>  periodList;

    public List<CBPTimePeriod> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<CBPTimePeriod> periodList) {
        this.periodList = periodList;
    }
}
