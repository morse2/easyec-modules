package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.Group;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessGroupRelation;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessRole;

public class ProcessGroupRelationPK implements ProcessGroupRelation {

    private Group group;
    private ProcessRole role;
    private String processDefinitionKey;

    @Override
    public ProcessRole getRole() {
        return role;
    }

    @Override
    public void setRole(ProcessRole role) {
        this.role = role;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    @Override
    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey == null ? null : processDefinitionKey.trim();
    }
}