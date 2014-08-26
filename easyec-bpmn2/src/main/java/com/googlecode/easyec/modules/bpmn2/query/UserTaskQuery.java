package com.googlecode.easyec.modules.bpmn2.query;

import com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessStatus;
import com.googlecode.easyec.modules.bpmn2.service.QueryProcessService;
import com.googlecode.easyec.spirit.dao.paging.Page;
import com.googlecode.easyec.spirit.query.AbstractQuery;
import com.googlecode.easyec.spirit.web.controller.sorts.Sort;

import java.util.Date;
import java.util.List;

import static com.googlecode.easyec.spirit.web.utils.SpringContextUtils.getBean;

/**
 * 查询用户任务的条件对象类
 *
 * @author JunJie
 */
public class UserTaskQuery extends AbstractQuery<UserTaskQuery> {

    public UserTaskQuery processDefinitionId(String processDefinitionId) {
        addSearchTerm("processDefinitionId", processDefinitionId);
        return getSelf();
    }

    public UserTaskQuery processDefinitionKey(String processDefinitionKey) {
        addSearchTerm("processDefinitionKey", processDefinitionKey);
        return getSelf();
    }

    public UserTaskQuery processInstanceId(String processInstanceId) {
        addSearchTerm("processInstanceId", processInstanceId);
        return getSelf();
    }

    public UserTaskQuery processStatus(ProcessStatus status) {
        addSearchTerm("processStatus", status);
        return getSelf();
    }

    public UserTaskQuery businessKey(String businessKey) {
        addSearchTerm("businessKey", businessKey);
        return getSelf();
    }

    public UserTaskQuery businessKeyLike(String businessKey) {
        addSearchTerm("businessKeyLike", businessKey);
        return getSelf();
    }

    public UserTaskQuery administrator(String userId) {
        addSearchTerm("administrator", userId);
        return getSelf();
    }

    public UserTaskQuery taskId(String taskId) {
        addSearchTerm("taskId", taskId);
        return getSelf();
    }

    public UserTaskQuery taskAssignee(String userId) {
        addSearchTerm("taskAssignee", userId);
        return getSelf();
    }

    public UserTaskQuery rejected() {
        addSearchTerm("rejected", true);
        return getSelf();
    }

    public UserTaskQuery notRejected() {
        addSearchTerm("rejected", false);
        return getSelf();
    }

    public UserTaskQuery applicantId(String applicantId) {
        addSearchTerm("applicant", applicantId);
        return getSelf();
    }

    public UserTaskQuery requestTimeStart(Date date) {
        addSearchTerm("requestTimeStart", date);
        return getSelf();
    }

    public UserTaskQuery requestTimeEnd(Date date) {
        addSearchTerm("requestTimeEnd", date);
        return getSelf();
    }

    public UserTaskQuery finishTimeStart(Date date) {
        addSearchTerm("finishTimeStart", date);
        return getSelf();
    }

    public UserTaskQuery finishTimeEnd(Date date) {
        addSearchTerm("finishTimeEnd", date);
        return getSelf();
    }

    public UserTaskQuery candidateUser(String userId) {
        addSearchTerm("candidateUser", userId);
        return getSelf();
    }

    public UserTaskQuery owner(String userId) {
        addSearchTerm("owner", userId);
        return getSelf();
    }

    public UserTaskQuery delegated(boolean or, boolean resolved) {
        addSearchTerm("delegated", true);
        addSearchTerm("delegatedOrResolved", or);
        addSearchTerm("resolved", resolved);
        return getSelf();
    }

    public UserTaskQuery claimedTask() {
        addSearchTerm("claimed", true);
        return getSelf();
    }

    public UserTaskQuery unclaimedTask() {
        addSearchTerm("claimed", false);
        return getSelf();
    }

    public UserTaskQuery noAssignee() {
        addSearchTerm("noAssignee", true);
        return getSelf();
    }

    public UserTaskQuery inTaskPool() {
        addSearchTerm("inTaskPool", true);
        return getSelf();
    }

    public UserTaskQuery orderByPriority(Sort.SortTypes direction) {
        addSort("RES.PRIORITY_", direction);
        return getSelf();
    }

    public UserTaskQuery orderByRequestTime(Sort.SortTypes direction) {
        addSort("ENT.REQUEST_TIME", direction);
        return getSelf();
    }

    @Override
    protected UserTaskQuery getSelf() {
        return this;
    }

    @Override
    public Page listPage(int currentPage) {
        setPageNumber(currentPage);
        return _getQueryProcessService().findTasks(this);
    }

    @Override
    public Page listPage(int currentPage, int pageSize) {
        setPageNumber(currentPage);
        return _getQueryProcessService().findTasks(this, pageSize);
    }

    @Override
    public long count() {
        return _getQueryProcessService().countTasks(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> List<U> list() {
        return (List<U>) _getQueryProcessService().getTasks(this);
    }

    private QueryProcessService _getQueryProcessService() {
        return getBean(QueryProcessService.class);
    }
}
