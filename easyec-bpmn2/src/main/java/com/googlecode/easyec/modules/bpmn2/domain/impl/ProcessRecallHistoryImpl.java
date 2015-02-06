package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessRecallHistory;

import java.util.Date;

public class ProcessRecallHistoryImpl implements ProcessRecallHistory {

    private static final long serialVersionUID = 1750281929174310820L;
    private ProcessObject processObject;
    private String processInstanceId;
    private Date createTime;

    @Override
    public ProcessObject getProcessObject() {
        return processObject;
    }

    @Override
    public void setProcessObject(ProcessObject processObject) {
        this.processObject = processObject;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    @Override
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}