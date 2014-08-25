package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import java.util.Date;

/**
 * Created by 俊杰 on 2014/7/9.
 */
public interface TaskObjectCtrl {

    void setTaskId(String taskId);

    void setTaskName(String taskName);

    void setAssignee(String assignee);

    void setOwner(String owner);

    void setCreateTime(Date createTime);

    void setEndTime(Date endTime);

    void setFormKey(String formKey);
}
