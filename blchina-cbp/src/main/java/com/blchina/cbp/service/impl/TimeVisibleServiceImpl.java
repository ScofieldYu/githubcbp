/*
 * @(#)TimeVisibleServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPTimeVisibleMapper;
import com.blchina.cbp.model.CBPTimeVisible;
import com.blchina.cbp.service.interfaces.TimeVisibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *针对客户不可见时间设置
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("TimeVisibleService")
class TimeVisibleServiceImpl implements TimeVisibleService {
    @Autowired
    private CBPTimeVisibleMapper cbpTimeVisibleMapper;
    /**
     * 查询客户不显示时间
     * @param orderid
     * @return
     */
    @Override
    public List<CBPTimeVisible> getVisibleByOrderid(Integer orderid) {
        return cbpTimeVisibleMapper.getVisibleByOrderid(orderid);
    }

    /**
     * 添加设置不可见时间list
     * @param visibleList
     */
    @Override
    public void addVisibleList(List<CBPTimeVisible> visibleList,Integer orderid) {
        for(int i=0;i<visibleList.size();i++){
            visibleList.get(i).setOrderid(orderid);
            cbpTimeVisibleMapper.insert(visibleList.get(i));
        }


    }

}
