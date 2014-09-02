package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign;

import java.util.Date;

public class ExtraTaskConsignImpl implements ExtraTaskConsign {

    private String taskId;
    private String processInstanceId;
    private String consignee;
    private String status;
    private Date   createTime;
    private Date   finishTime;

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
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
    public String getConsignee() {
        return consignee;
    }

    @Override
    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
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
    public Date getFinishTime() {
        return finishTime;
    }

    @Override
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}