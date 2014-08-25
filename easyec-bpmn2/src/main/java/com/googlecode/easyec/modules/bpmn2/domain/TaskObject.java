package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.TaskObjectCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;
import org.activiti.engine.task.DelegationState;

import java.util.Date;
import java.util.List;

/**
 * 流程的任务实体对象类
 *
 * @author JunJie
 */
public interface TaskObject extends DomainModel, TaskObjectCtrl {

    String getTaskId();

    String getTaskName();

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
}
