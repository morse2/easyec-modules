package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.spirit.domain.GenericPersistentDomainModel;

public interface ProcessAutoApprovalConfig extends GenericPersistentDomainModel<Long> {

    String getProcessDefinitionKey();

    void setProcessDefinitionKey(String processDefinitionKey);

    String getTaskDefinitionKey();

    void setTaskDefinitionKey(String taskDefinitionKey);

    boolean isOnProcess();

    void setOnProcess(boolean onProcess);

    boolean isEnable();

    void setEnable(boolean enable);
}
