package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject;

import java.util.Date;

public class ExtraTaskObjectImpl implements ExtraTaskObject {

    private String taskId;
    private String assignee;
    private String status;
    private Date   createTime;
    private String processInstanceId;
    private String delegatedUser;

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

    @Override
    public void setAssignee(String assignee) {
        this.assignee = assignee == null ? null : assignee.trim();
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    @Override
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    @Override
    public String getDelegatedUser() {
        return delegatedUser;
    }

    @Override
    public void setDelegatedUser(String delegatedUser) {
        this.delegatedUser = delegatedUser;
    }
}