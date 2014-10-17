package com.googlecode.easyec.modules.bpmn2.assignment;

import java.io.Serializable;

/**
 * 任务处理人实体类
 *
 * @author JunJie
 */
public class Assignee implements Serializable {

    private static final long serialVersionUID = -3532262215374279794L;
    private String userId;
    private String delegatedUserId;

    public Assignee(String userId, String delegatedUserId) {
        this.userId = userId;
        this.delegatedUserId = delegatedUserId;
    }

    public Assignee(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDelegatedUserId() {
        return delegatedUserId;
    }
}
