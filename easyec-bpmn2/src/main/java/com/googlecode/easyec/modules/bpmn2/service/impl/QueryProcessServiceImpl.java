package com.googlecode.easyec.modules.bpmn2.service.impl;

import com.googlecode.easyec.modules.bpmn2.dao.ExtraTaskConsignDao;
import com.googlecode.easyec.modules.bpmn2.dao.ProcessObjectDao;
import com.googlecode.easyec.modules.bpmn2.dao.TaskObjectDao;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.query.ProcessQuery;
import com.googlecode.easyec.modules.bpmn2.query.TaskConsignQuery;
import com.googlecode.easyec.modules.bpmn2.query.UserTaskHistoricQuery;
import com.googlecode.easyec.modules.bpmn2.query.UserTaskQuery;
import com.googlecode.easyec.modules.bpmn2.service.QueryProcessService;
import com.googlecode.easyec.spirit.dao.paging.Page;
import com.googlecode.easyec.spirit.service.EcService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 流程过程查询方法集合的业务类
 *
 * @author JunJie
 */
public class QueryProcessServiceImpl extends EcService implements QueryProcessService {

    @Resource
    private ExtraTaskConsignDao extraTaskConsignDao;

    @Resource
    private ProcessObjectDao processObjectDao;
    @Resource
    private TaskObjectDao    taskObjectDao;

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService    taskService;

    @Override
    public Page findRequests(ProcessQuery query) {
        return findRequests(query, getPageSize());
    }

    @Override
    public Page findRequests(ProcessQuery query, int pageSize) {
        return processObjectDao.find(createPage(query.getSearchFormBean(), pageSize));
    }

    @Override
    public List<ProcessObject> getRequests(ProcessQuery query) {
        return processObjectDao.find(extractQuery(query));
    }

    @Override
    public long countRequests(ProcessQuery query) {
        return processObjectDao.count(extractQuery(query));
    }

    @Override
    public Page findTasks(UserTaskQuery query) {
        return findTasks(query, getPageSize());
    }

    @Override
    public Page findTasks(UserTaskQuery query, int pageSize) {
        return taskObjectDao.find(createPage(query.getSearchFormBean(), pageSize));
    }

    @Override
    public List<TaskObject> getTasks(UserTaskQuery query) {
        return taskObjectDao.find(extractQuery(query));
    }

    @Override
    public long countTasks(UserTaskQuery query) {
        return taskObjectDao.countTasks(extractQuery(query));
    }

    @Override
    public Page findHistoricTasks(UserTaskHistoricQuery query) {
        return findHistoricTasks(query, getPageSize());
    }

    @Override
    public Page findHistoricTasks(UserTaskHistoricQuery query, int pageSize) {
        return taskObjectDao.findHistoric(createPage(query.getSearchFormBean(), pageSize));
    }

    @Override
    public List<TaskObject> getHistoricTasks(UserTaskHistoricQuery query) {
        return taskObjectDao.findHistoric(extractQuery(query));
    }

    @Override
    public long countHistoricTasks(UserTaskHistoricQuery query) {
        return taskObjectDao.countHistoricTasks(extractQuery(query));
    }

    @Override
    public Page findTaskConsigns(TaskConsignQuery query) {
        return findTaskConsigns(query, getPageSize());
    }

    @Override
    public Page findTaskConsigns(TaskConsignQuery query, int pageSize) {
        return extraTaskConsignDao.find(createPage(query.getSearchFormBean(), pageSize));
    }

    @Override
    public List<ExtraTaskConsign> getTaskConsigns(TaskConsignQuery query) {
        return extraTaskConsignDao.find(extractQuery(query));
    }

    @Override
    public long countTaskConsigns(TaskConsignQuery query) {
        return extraTaskConsignDao.countTaskConsigns(extractQuery(query));
    }

    @Override
    public TaskObject getHistoricTask(String taskId) {
        return taskObjectDao.selectHistoric(taskId);
    }

    @Override
    public TaskObject getTask(String taskId) {
        TaskObject task = taskObjectDao.selectByPrimaryKey(taskId);
        return task == null ? getHistoricTask(taskId) : task;
    }

    @Override
    public ProcessObject getProcess(Long uidPk) {
        return processObjectDao.selectByPrimaryKey(uidPk);
    }

    @Override
    public Map<String, Object> getVariables(TaskObject task) {
        return taskService.getVariables(task.getTaskId());
    }

    @Override
    public Map<String, Object> getVariables(TaskObject task, List<String> variableNames) {
        return taskService.getVariables(task.getTaskId(), variableNames);
    }

    @Override
    public Object getVariable(TaskObject task, String variableName) {
        return taskService.getVariable(task.getTaskId(), variableName);
    }

    @Override
    public Map<String, Object> getVariables(ProcessObject entity) {
        return runtimeService.getVariables(entity.getProcessInstanceId());
    }

    @Override
    public Map<String, Object> getVariables(ProcessObject entity, List<String> variableNames) {
        return runtimeService.getVariables(entity.getProcessInstanceId(), variableNames);
    }

    @Override
    public Object getVariable(ProcessObject entity, String variableName) {
        return runtimeService.getVariable(entity.getProcessInstanceId(), variableName);
    }
}
