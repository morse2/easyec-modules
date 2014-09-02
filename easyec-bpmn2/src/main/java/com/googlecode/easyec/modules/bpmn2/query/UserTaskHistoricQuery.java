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
 * 查询历史用户任务的条件对象类
 *
 * @author JunJie
 */
public class UserTaskHistoricQuery extends AbstractQuery<UserTaskHistoricQuery> {

    public UserTaskHistoricQuery processDefinitionId(String processDefinitionId) {
        addSearchTerm("processDefinitionId", processDefinitionId);
        return getSelf();
    }

    public UserTaskHistoricQuery processDefinitionKey(String processDefinitionKey) {
        addSearchTerm("processDefinitionKey", processDefinitionKey);
        return getSelf();
    }

    public UserTaskHistoricQuery processInstanceId(String processInstanceId) {
        addSearchTerm("processInstanceId", processInstanceId);
        return getSelf();
    }

    public UserTaskHistoricQuery processStatus(ProcessStatus status) {
        addSearchTerm("processStatus", status);
        return getSelf();
    }

    public UserTaskHistoricQuery businessKey(String businessKey) {
        addSearchTerm("businessKey", businessKey);
        return getSelf();
    }

    public UserTaskHistoricQuery businessKeyLike(String businessKey) {
        addSearchTerm("businessKeyLike", businessKey);
        return getSelf();
    }

    public UserTaskHistoricQuery administrator(String userId) {
        addSearchTerm("administrator", userId);
        return getSelf();
    }

    public UserTaskHistoricQuery taskId(String taskId) {
        addSearchTerm("taskId", taskId);
        return getSelf();
    }

    public UserTaskHistoricQuery taskAssignee(String userId) {
        removeSearchTerm("includeConsign");
        addSearchTerm("taskAssignee", userId);
        return getSelf();
    }

    public UserTaskHistoricQuery rejected() {
        addSearchTerm("rejected", true);
        return getSelf();
    }

    public UserTaskHistoricQuery notRejected() {
        addSearchTerm("rejected", false);
        return getSelf();
    }

    public UserTaskHistoricQuery applicantId(String applicantId) {
        addSearchTerm("applicant", applicantId);
        return getSelf();
    }

    public UserTaskHistoricQuery requestTimeStart(Date date) {
        addSearchTerm("requestTimeStart", date);
        return getSelf();
    }

    public UserTaskHistoricQuery requestTimeEnd(Date date) {
        addSearchTerm("requestTimeEnd", date);
        return getSelf();
    }

    public UserTaskHistoricQuery finishTimeStart(Date date) {
        addSearchTerm("finishTimeStart", date);
        return getSelf();
    }

    public UserTaskHistoricQuery finishTimeEnd(Date date) {
        addSearchTerm("finishTimeEnd", date);
        return getSelf();
    }

    public UserTaskHistoricQuery candidateUser(String userId) {
        addSearchTerm("candidateUser", userId);
        return getSelf();
    }

    public UserTaskHistoricQuery owner(String userId) {
        addSearchTerm("owner", userId);
        return getSelf();
    }

    public UserTaskHistoricQuery delegated(boolean or, boolean resolved) {
        addSearchTerm("delegated", true);
        addSearchTerm("delegatedOrResolved", or);
        addSearchTerm("resolved", resolved);
        return getSelf();
    }

    public UserTaskHistoricQuery claimedTask() {
        addSearchTerm("claimed", true);
        return getSelf();
    }

    public UserTaskHistoricQuery unclaimedTask() {
        addSearchTerm("claimed", false);
        return getSelf();
    }

    public UserTaskHistoricQuery includeConsign(String consignee) {
        addSearchTerm("includeConsign", true);
        addSearchTerm("taskAssignee", consignee);
        return getSelf();
    }

    public UserTaskHistoricQuery orderByPriority(Sort.SortTypes direction) {
        addSort("RES.PRIORITY_", direction);
        return getSelf();
    }

    public UserTaskHistoricQuery orderByRequestTime(Sort.SortTypes direction) {
        addSort("ENT.REQUEST_TIME", direction);
        return getSelf();
    }

    public UserTaskHistoricQuery orderByTaskEndTime(Sort.SortTypes direction) {
        addSort("RES.END_TIME_", direction);
        return getSelf();
    }

    @Override
    protected UserTaskHistoricQuery getSelf() {
        return this;
    }

    @Override
    public Page listPage(int currentPage) {
        setPageNumber(currentPage);
        return _getQueryProcessService().findHistoricTasks(this);
    }

    @Override
    public Page listPage(int currentPage, int pageSize) {
        setPageNumber(currentPage);
        return _getQueryProcessService().findHistoricTasks(this, pageSize);
    }

    @Override
    public long count() {
        return _getQueryProcessService().countHistoricTasks(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> List<U> list() {
        return (List<U>) _getQueryProcessService().getHistoricTasks(this);
    }

    private QueryProcessService _getQueryProcessService() {
        return getBean(QueryProcessService.class);
    }
}
