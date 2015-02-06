package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessRecallHistory;

public interface ProcessRecallHistoryDao {

    int deleteByPrimaryKey(String processInstanceId);

    int insert(ProcessRecallHistory record);

    ProcessRecallHistory selectByPrimaryKey(String processInstanceId);
}