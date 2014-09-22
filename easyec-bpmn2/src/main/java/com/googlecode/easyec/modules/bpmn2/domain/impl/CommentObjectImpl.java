package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;

import java.util.Date;

import static com.googlecode.easyec.modules.bpmn2.utils.ProcessConstant.I18_APPLICANT_ROLE;

/**
 * 流程的注解实体类。
 * <p>
 * 该注解对象可用于记录流程或用户任务的备注信息
 * </p>
 *
 * @author JunJie
 */
public class CommentObjectImpl implements CommentObject {

    private String id;
    private String userId;
    private String content;
    private CommentTypes type;
    private Date createTime;

    private TaskObject task;
    private ExtraTaskObject extraTask;
    private ExtraTaskConsign extraTaskConsign;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public CommentTypes getType() {
        return type;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void setType(CommentTypes type) {
        this.type = type;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public TaskObject getTask() {
        return task;
    }

    @Override
    public ExtraTaskObject getExtraTask() {
        return extraTask;
    }

    @Override
    public String getTaskRole() {
        TaskObject task = getTask();
        if (task == null) return I18_APPLICANT_ROLE;
        return task.getTaskKey();
    }

    public ExtraTaskConsign getExtraTaskConsign() {
        return extraTaskConsign;
    }

    @Override
    public String getStatus() {
        switch (getType()) {
            case BY_TASK_APPROVAL:
                return _getExtraTaskStatus();
            case BY_TASK_ANNOTATED:
                return _getExtraTaskConsignStatus();
        }

        return null;
    }

    private String _getExtraTaskStatus() {
        ExtraTaskObject task = getExtraTask();
        return task != null ? task.getStatus() : null;
    }

    private String _getExtraTaskConsignStatus() {
        ExtraTaskConsign consign = getExtraTaskConsign();
        return consign != null ? consign.getStatus() : null;
    }
}
