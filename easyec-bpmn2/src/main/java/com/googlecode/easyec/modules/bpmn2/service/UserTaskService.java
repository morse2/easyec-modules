package com.googlecode.easyec.modules.bpmn2.service;

import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;
import com.googlecode.easyec.modules.bpmn2.support.impl.TaskAuditBehavior;
import com.googlecode.easyec.modules.bpmn2.task.NewTask;

import java.util.Map;

/**
 * 流程用户任务业务操作接口类
 *
 * @author JunJie
 */
public interface UserTaskService {

    void claimTask(String taskId, String userId) throws ProcessPersistentException;

    void unclaimTask(String taskId);

    void approveTask(TaskObject task, TaskAuditBehavior behavior) throws ProcessPersistentException;

    void rejectTask(TaskObject task, TaskAuditBehavior behavior) throws ProcessPersistentException;

    void rejectTaskPartially(TaskObject task, TaskAuditBehavior behavior) throws ProcessPersistentException;

    void delegateTask(TaskObject task, String userId, String commentId) throws ProcessPersistentException;

    void resolveTask(TaskObject task, String status, String commentId, Map<String, Object> variables) throws ProcessPersistentException;

    CommentObject createComment(TaskObject task, String type, String comment, String role, String action)
    throws ProcessPersistentException;

    CommentObject createComment(ProcessObject po, String type, String comment, String role, String action)
    throws ProcessPersistentException;

    void createExtraTask(ExtraTaskObject o) throws ProcessPersistentException;

    void setAssignee(String taskId, String userId, boolean notifyUser) throws ProcessPersistentException;

    void saveNewTask(NewTask newTask);

    void setDelegatedUser(String taskId, String delegatedUser) throws ProcessPersistentException;

    void setExtraTaskStatus(String taskId, String status) throws ProcessPersistentException;

    boolean hasApprovalPeople(String taskId);
}
