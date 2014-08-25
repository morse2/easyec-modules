package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.spirit.domain.DomainModel;

import java.util.Date;

/**
 * Created by JunJie on 2014/8/24.
 */
public interface ExtraTaskObject extends DomainModel {

    String getTaskId();

    void setTaskId(String taskId);

    String getAssignee();

    void setAssignee(String assignee);

    String getStatus();

    void setStatus(String status);

    Date getCreateTime();

    void setCreateTime(Date createTime);

    String getProcessInstanceId();

    void setProcessInstanceId(String processInstanceId);
}
