package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;

import java.util.Date;

import static com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes.BY_TASK_ANNOTATED;
import static com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes.BY_TASK_APPROVAL;
import static com.googlecode.easyec.modules.bpmn2.utils.ProcessConstant.I18_CONSIGN_ROLE;

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
    private String type;
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
    public String getType() {
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
    public void setType(String type) {
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
        // 如果有EXTRA CONSIGN对象，则标记角色为委托人
        ExtraTaskConsign consign = getExtraTaskConsign();
        if (consign != null) return I18_CONSIGN_ROLE;

        // 如果备注关联任务，则返回任务定义的KEY
        TaskObject task = getTask();
        if (task != null) return task.getTaskKey();

        // 否则返回此备注的type作为任务的角色
        return getType();
    }

    public ExtraTaskConsign getExtraTaskConsign() {
        return extraTaskConsign;
    }

    @Override
    public String getStatus() {
        // 任务审批类型的备注，则返回对应的任务的状态
        if (BY_TASK_APPROVAL.name().equalsIgnoreCase(getType())) {
            return _getExtraTaskStatus();
        }
        // 任务备注类型的备注，则返回委托的状态
        if (BY_TASK_ANNOTATED.name().equalsIgnoreCase(getType())) {
            return _getExtraTaskConsignStatus();
        }

        // 否则返回当前备注的类型作为状态
        return getType();
    }

    private String _getExtraTaskStatus() {
        ExtraTaskObject task = getExtraTask();
        return task != null ? task.getStatus() : getContent();
    }

    private String _getExtraTaskConsignStatus() {
        ExtraTaskConsign consign = getExtraTaskConsign();
        return consign != null ? consign.getStatus() : getContent();
    }
}
