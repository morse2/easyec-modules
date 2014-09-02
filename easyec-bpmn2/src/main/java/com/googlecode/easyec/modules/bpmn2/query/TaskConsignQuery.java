package com.googlecode.easyec.modules.bpmn2.query;

import com.googlecode.easyec.modules.bpmn2.service.QueryProcessService;
import com.googlecode.easyec.spirit.dao.paging.Page;
import com.googlecode.easyec.spirit.query.AbstractQuery;

import java.util.List;

import static com.googlecode.easyec.spirit.web.utils.SpringContextUtils.getBean;

/**
 * 任务委托记录的查询条件类
 *
 * @author JunJie
 */
public class TaskConsignQuery extends AbstractQuery<TaskConsignQuery> {

    public TaskConsignQuery taskId(String taskId) {
        addSearchTerm("taskId", taskId);
        return getSelf();
    }

    public TaskConsignQuery consignee(String consignee) {
        addSearchTerm("consignee", consignee);
        return getSelf();
    }

    public TaskConsignQuery processInstanceId(String processInstanceId) {
        addSearchTerm("processInstanceId", processInstanceId);
        return getSelf();
    }

    public TaskConsignQuery status(String status) {
        addSearchTerm("status", status);
        return getSelf();
    }

    @Override
    protected TaskConsignQuery getSelf() {
        return this;
    }

    @Override
    public Page listPage(int currentPage) {
        setPageNumber(currentPage);
        return getBean(QueryProcessService.class).findTaskConsigns(this);
    }

    @Override
    public Page listPage(int currentPage, int pageSize) {
        setPageNumber(currentPage);
        return getBean(QueryProcessService.class).findTaskConsigns(this, pageSize);
    }

    @Override
    public long count() {
        return getBean(QueryProcessService.class).countTaskConsigns(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> List<U> list() {
        return (List<U>) getBean(QueryProcessService.class).getTaskConsigns(this);
    }
}
