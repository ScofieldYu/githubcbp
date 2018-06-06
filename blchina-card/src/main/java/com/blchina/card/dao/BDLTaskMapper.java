package com.blchina.card.dao;

import java.util.List;

import com.blchina.card.model.BDLTask;

public interface BDLTaskMapper {
    int deleteByPrimaryKey(Integer taskid);

    int insert(BDLTask record);

    int insertSelective(BDLTask record);

    BDLTask selectByPrimaryKey(Integer taskid);

    int updateByPrimaryKeySelective(BDLTask record);

    int updateByPrimaryKey(BDLTask record);

   List<BDLTask> selectTaskListByCardId(Integer cardid);

   int updateTaskStatusComplete(BDLTask task);

}