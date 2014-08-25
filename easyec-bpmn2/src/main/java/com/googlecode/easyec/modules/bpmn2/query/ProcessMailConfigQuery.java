package com.googlecode.easyec.modules.bpmn2.query;

import com.googlecode.easyec.modules.bpmn2.service.ProcessMailConfigService;
import com.googlecode.easyec.spirit.mybatis.query.AbstractDelegateQuery;
import com.googlecode.easyec.spirit.mybatis.service.DelegateService;

import static com.googlecode.easyec.spirit.web.utils.SpringContextUtils.getBean;

/**
 * 流程邮件配置信息查询对象类
 *
 * @author JunJie
 */
public class ProcessMailConfigQuery extends AbstractDelegateQuery<ProcessMailConfigQuery> {

    public ProcessMailConfigQuery processEntityId(Long processEntityId) {
        addSearchTerm("processEntityId", processEntityId);
        return getSelf();
    }

    public ProcessMailConfigQuery fireType(String fireType) {
        addSearchTerm("fireType", fireType);
        return getSelf();
    }

    @Override
    protected ProcessMailConfigQuery getSelf() {
        return this;
    }

    @Override
    protected DelegateService getDelegateService() {
        return getBean(ProcessMailConfigService.class);
    }
}
