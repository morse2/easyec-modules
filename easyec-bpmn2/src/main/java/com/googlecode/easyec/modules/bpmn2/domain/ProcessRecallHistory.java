package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.spirit.domain.DomainModel;

import java.util.Date;

public interface ProcessRecallHistory extends DomainModel {

    ProcessObject getProcessObject();

    void setProcessObject(ProcessObject processObject);

    String getProcessInstanceId();

    void setProcessInstanceId(String processInstanceId);

    Date getCreateTime();

    void setCreateTime(Date createTime);
}
