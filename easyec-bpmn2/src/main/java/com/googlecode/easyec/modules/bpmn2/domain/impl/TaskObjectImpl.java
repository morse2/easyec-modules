package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import org.activiti.engine.task.DelegationState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.activiti.engine.task.DelegationState.PENDING;

/**
 * Created by 俊杰 on 2014/7/9.
 */
public class TaskObjectImpl extends TaskDefinitionImpl implements TaskObject {

    private String taskId;
    private String assignee;
    private String owner;
    private Date createTime;
    private String formKey;
    private Date endTime;

    private DelegationState delegationState;
    private ProcessObject processObject;

    private ExtraTaskObject extraTask;

    /* 任务审批类型的备注列表 */
    private List<CommentObject> approvedComments = new ArrayList<CommentObject>();
    /* 任务批注类型的备注列表 */
    private List<CommentObject> annotatedComments = new ArrayList<CommentObject>();

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public String getFormKey() {
        return formKey;
    }

    @Override
    public ProcessObject getProcessObject() {
        return processObject;
    }

    @Override
    public boolean isDelegating() {
        return PENDING.equals(getDelegationState());
    }

    @Override
    public List<CommentObject> getApprovedComments() {
        return approvedComments;
    }

    @Override
    public List<CommentObject> getAnnotatedComments() {
        return annotatedComments;
    }

    @Override
    public boolean isEnd() {
        return this.endTime != null;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public String getDelegatedUserId() {
        return getExtraTask().getDelegatedUser();
    }

    @Override
    public String getStatus() {
        return getExtraTask().getStatus();
    }

    public ExtraTaskObject getExtraTask() {
        return extraTask;
    }

    public DelegationState getDelegationState() {
        return delegationState;
    }

    public void setApprovedComments(List<CommentObject> approvedComments) {
        this.approvedComments = approvedComments;
    }

    public void setAnnotatedComments(List<CommentObject> annotatedComments) {
        this.annotatedComments = annotatedComments;
    }
}
