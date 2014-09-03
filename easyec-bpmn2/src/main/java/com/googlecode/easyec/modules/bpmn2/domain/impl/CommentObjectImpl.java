package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;

import java.util.Date;

/**
 * 流程的注解实体类。
 * <p>
 * 该注解对象可用于记录流程或用户任务的备注信息
 * </p>
 *
 * @author JunJie
 */
public class CommentObjectImpl implements CommentObject {

    private String       id;
    private String       userId;
    private String       content;
    private CommentTypes type;
    private Date         createTime;

    private ExtraTaskObject extraTask;

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
    public ExtraTaskObject getExtraTask() {
        return extraTask;
    }
}
