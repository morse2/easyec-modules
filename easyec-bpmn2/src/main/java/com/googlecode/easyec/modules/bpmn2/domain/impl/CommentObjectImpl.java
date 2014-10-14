package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;

import java.util.Date;

import static java.nio.charset.Charset.forName;
import static org.apache.commons.lang.StringUtils.isNotBlank;

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
    private String type;
    private Date createTime;
    private String taskRole;
    private String taskAction;
    private byte[] fullMessage;

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
        return new String(getFullMessage(), forName("utf-8"));
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
        if (isNotBlank(content)) {
            setFullMessage(content.getBytes(forName("utf-8")));
        }
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
    public String getTaskRole() {
        return taskRole;
    }

    @Override
    public String getTaskAction() {
        return taskAction;
    }

    @Override
    public void setTaskRole(String taskRole) {
        this.taskRole = taskRole;
    }

    @Override
    public void setTaskAction(String taskAction) {
        this.taskAction = taskAction;
    }

    public byte[] getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(byte[] fullMessage) {
        this.fullMessage = fullMessage;
    }
}
