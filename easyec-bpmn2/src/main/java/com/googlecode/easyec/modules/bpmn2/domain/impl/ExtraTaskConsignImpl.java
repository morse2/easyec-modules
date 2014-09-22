package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign;
import com.googlecode.easyec.spirit.dao.id.annotation.Identifier;

import java.util.Date;

@Identifier("SEQ_EXTRA_TASK_CONSIGN")
public class ExtraTaskConsignImpl implements ExtraTaskConsign {

    private Long uidPk;
    private String taskId;
    private String commentId;
    private String processInstanceId;
    private String consignee;
    private String status;
    private Date createTime;
    private Date finishTime;

    @Override
    public Long getUidPk() {
        return uidPk;
    }

    @Override
    public void setUidPk(Long uidPk) {
        this.uidPk = uidPk;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    @Override
    public String getCommentId() {
        return commentId;
    }

    @Override
    public void setCommentId(String commentId) {
        this.commentId = commentId;
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