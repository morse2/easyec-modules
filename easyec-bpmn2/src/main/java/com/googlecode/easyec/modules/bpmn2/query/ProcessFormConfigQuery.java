package com.googlecode.easyec.modules.bpmn2.query;

import com.googlecode.easyec.modules.bpmn2.service.ProcessFormConfigService;
import com.googlecode.easyec.spirit.mybatis.query.AbstractDelegateQuery;
import com.googlecode.easyec.spirit.mybatis.service.DelegateService;

import static com.googlecode.easyec.spirit.web.utils.SpringContextUtils.getBean;

/**
 * Created by JunJie on 2014/10/21.
 */
public class ProcessFormConfigQuery extends AbstractDelegateQuery<ProcessFormConfigQuery> {

    public ProcessFormConfigQuery processDefinitionKey(String processDefinitionKey) {
        addSearchTerm("definitionKey", processDefinitionKey);
        return this;
    }

    public ProcessFormConfigQuery processFormType(String processFormType) {
        addSearchTerm("formType", processFormType);
        return this;
    }

    @Override
    protected DelegateService getDelegateService() {
        return getBean(ProcessFormConfigService.class);
    }

    @Override
    protected ProcessFormConfigQuery getSelf() {
        return this;
    }
}
