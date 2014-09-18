package com.googlecode.easyec.modules.bpmn2.service;

import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;
import com.googlecode.easyec.modules.bpmn2.task.NewTask;
import org.activiti.engine.task.Task;

import java.util.Map;

/**
 * 流程用户任务业务操作接口类
 *
 * @author JunJie
 */
public interface UserTaskService {

    void claimTask(String taskId, String userId) throws ProcessPersistentException;

    void unclaimTask(String taskId);

    void approveTask(TaskObject task, String comment) throws ProcessPersistentException;

    void approveTask(TaskObject task, String comment, boolean commented) throws ProcessPersistentException;

    void approveTask(TaskObject task, String comment, Map<String, Object> variables) throws ProcessPersistentException;

    void approveTask(TaskObject task, String comment, Map<String, Object> variables, boolean commented)
    throws ProcessPersistentException;

    void rejectTask(TaskObject task, String comment) throws ProcessPersistentException;

    void rejectTask(TaskObject task, String comment, boolean commented) throws ProcessPersistentException;

    void rejectTask(TaskObject task, String comment, Map<String, Object> variables) throws ProcessPersistentException;

    void rejectTask(TaskObject task, String comment, Map<String, Object> variables, boolean commented)
    throws ProcessPersistentException;

    void rejectTaskPartially(TaskObject task, String comment) throws ProcessPersistentException;

    void rejectTaskPartially(TaskObject task, String comment, boolean commented)
    throws ProcessPersistentException;

    void rejectTaskPartially(TaskObject task, String comment, Map<String, Object> variables)
    throws ProcessPersistentException;

    void rejectTaskPartially(TaskObject task, String comment, Map<String, Object> variables, boolean commented)
    throws ProcessPersistentException;

    void delegateTask(TaskObject task, String userId) throws ProcessPersistentException;

    void resolveTask(TaskObject task, boolean agree, CommentTypes type, String comment, Map<String, Object> variables)
    throws ProcessPersistentException;

    CommentObject createComment(TaskObject task, CommentTypes type, String comment) throws ProcessPersistentException;

    CommentObject createComment(ProcessObject po, CommentTypes type, String comment)
    throws ProcessPersistentException;

    void createExtraTask(ExtraTaskObject o) throws ProcessPersistentException;

    void setAssignee(String taskId, String userId) throws ProcessPersistentException;

    void saveNewTask(NewTask newTask);

    void setDelegatedUser(String taskId, String delegatedUser) throws ProcessPersistentException;
}
