package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.AttachmentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ctrl.AttachmentObjectCtrl;

/**
 * 流程的附件实体类。
 * <p>
 * 该类用于记录流程中的附件信息
 * </p>
 *
 * @author JunJie
 */
public class AttachmentObjectImpl implements AttachmentObject, AttachmentObjectCtrl {

    private String id;
    private String name;
    private String userId;
    private String description;
    private String taskId;
    private String url;

    /* 附件的内容 */
    private byte[] content;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }
}
