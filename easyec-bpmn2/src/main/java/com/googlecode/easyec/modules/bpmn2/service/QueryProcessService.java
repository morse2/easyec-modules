package com.googlecode.easyec.modules.bpmn2.service;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.query.ProcessQuery;
import com.googlecode.easyec.modules.bpmn2.query.UserTaskHistoricQuery;
import com.googlecode.easyec.modules.bpmn2.query.UserTaskQuery;
import com.googlecode.easyec.spirit.dao.paging.Page;

import java.util.List;
import java.util.Map;

/**
 * 查询流程的业务接口类
 *
 * @author JunJie
 */
public interface QueryProcessService {

    Page findRequests(ProcessQuery query);

    Page findRequests(ProcessQuery query, int pageSize);

    long countRequests(ProcessQuery query);

    List<ProcessObject> getRequests(ProcessQuery query);

    Page findTasks(UserTaskQuery query);

    Page findTasks(UserTaskQuery query, int pageSize);

    List<TaskObject> getTasks(UserTaskQuery query);

    long countTasks(UserTaskQuery query);

    Page findHistoricTasks(UserTaskHistoricQuery query);

    Page findHistoricTasks(UserTaskHistoricQuery query, int pageSize);

    List<TaskObject> getHistoricTasks(UserTaskHistoricQuery query);

    long countHistoricTasks(UserTaskHistoricQuery query);

    TaskObject getHistoricTask(String taskId);

    TaskObject getTask(String taskId);

    ProcessObject getProcess(Long uidPk);

    Map<String, Object> getVariables(TaskObject task);

    Map<String, Object> getVariables(TaskObject task, List<String> variableNames);

    Object getVariable(TaskObject task, String variableName);

    Map<String, Object> getVariables(ProcessObject entity);

    Map<String, Object> getVariables(ProcessObject entity, List<String> variableNames);

    Object getVariable(ProcessObject entity, String variableName);
}
