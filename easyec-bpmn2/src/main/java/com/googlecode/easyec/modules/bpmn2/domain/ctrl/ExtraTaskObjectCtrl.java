package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import java.util.Date;

/**
 * Created by JunJie on 2014/9/3.
 */
public interface ExtraTaskObjectCtrl {

    void setTaskId(String taskId);

    void setAssignee(String assignee);

    void setStatus(String status);

    void setCreateTime(Date createTime);

    void setProcessInstanceId(String processInstanceId);

    void setDelegatedUser(String delegatedUser);
}
