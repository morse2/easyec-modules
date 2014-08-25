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
 * 查询流程信息的条件对象类
 *
 * @author JunJie
 */
public class ProcessQuery extends AbstractQuery<ProcessQuery> {

    public ProcessQuery processDefinitionId(String processDefinitionId) {
        addSearchTerm("processDefinitionId", processDefinitionId);
        return getSelf();
    }

    public ProcessQuery processDefinitionKey(String processDefinitionKey) {
        addSearchTerm("processDefinitionKey", processDefinitionKey);
        return getSelf();
    }

    public ProcessQuery processInstanceId(String processInstanceId) {
        addSearchTerm("processInstanceId", processInstanceId);
        return getSelf();
    }

    public ProcessQuery processStatus(ProcessStatus status) {
        addSearchTerm("status", status);
        return getSelf();
    }

    public ProcessQuery businessKey(String businessKey) {
        addSearchTerm("businessKey", businessKey);
        return getSelf();
    }

    public ProcessQuery businessKeyLike(String businessKey) {
        addSearchTerm("businessKeyLike", businessKey);
        return getSelf();
    }

    public ProcessQuery requestTimeStart(Date date) {
        addSearchTerm("requestTimeStart", date);
        return getSelf();
    }

    public ProcessQuery requestTimeEnd(Date date) {
        addSearchTerm("requestTimeEnd", date);
        return getSelf();
    }

    public ProcessQuery finishTimeStart(Date date) {
        addSearchTerm("finishTimeStart", date);
        return getSelf();
    }

    public ProcessQuery finishTimeEnd(Date date) {
        addSearchTerm("finishTimeEnd", date);
        return getSelf();
    }

    public ProcessQuery applicantId(String userId) {
        addSearchTerm("applicantId", userId);
        return getSelf();
    }

    public ProcessQuery administrator(String userId) {
        addSearchTerm("administrator", userId);
        return getSelf();
    }

    public ProcessQuery rejected(boolean withStatus) {
        addSearchTerm("withStatus", withStatus);
        addSearchTerm("rejected", true);
        return getSelf();
    }

    public ProcessQuery notRejected() {
        addSearchTerm("rejected", false);
        return getSelf();
    }

    public ProcessQuery orderByPriority(Sort.SortTypes direction) {
        addSort("BPE.PROC_PRIORITY", direction);
        return getSelf();
    }

    public ProcessQuery orderByRequestTime(Sort.SortTypes direction) {
        addSort("BPE.REQUEST_TIME", direction);
        return getSelf();
    }

    public ProcessQuery orderByCreateTime(Sort.SortTypes direction) {
        addSort("BPE.CREATE_TIME", direction);
        return getSelf();
    }

    public ProcessQuery orderByFinishTime(Sort.SortTypes direction) {
        addSort("BPE.FINISH_TIME", direction);
        return getSelf();
    }

    @Override
    protected ProcessQuery getSelf() {
        return this;
    }

    @Override
    public Page listPage(int currentPage) {
        setPageNumber(currentPage);
        return _getQueryProcessService().findRequests(this);
    }

    @Override
    public Page listPage(int currentPage, int pageSize) {
        setPageNumber(currentPage);
        return _getQueryProcessService().findRequests(this, pageSize);
    }

    @Override
    public long count() {
        return _getQueryProcessService().countRequests(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> List<U> list() {
        return (List<U>) _getQueryProcessService().getRequests(this);
    }

    private QueryProcessService _getQueryProcessService() {
        return getBean(QueryProcessService.class);
    }
}
