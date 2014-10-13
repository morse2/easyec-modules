package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.TaskObjectCtrl;

import java.util.Date;
import java.util.List;

/**
 * 流程的任务实体对象类
 *
 * @author JunJie
 */
public interface TaskObject extends TaskDefinition, TaskObjectCtrl {

    String getTaskId();

    String getAssignee();

    String getOwner();

    Date getCreateTime();

    Date getEndTime();

    String getFormKey();

    ProcessObject getProcessObject();

    boolean isDelegating();

    List<CommentObject> getApprovedComments();

    List<CommentObject> getAnnotatedComments();

    boolean isEnd();

    String getDelegatedUserId();

    String getStatus();
}
