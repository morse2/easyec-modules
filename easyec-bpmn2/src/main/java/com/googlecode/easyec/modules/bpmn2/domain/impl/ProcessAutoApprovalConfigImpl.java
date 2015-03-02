package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessAutoApprovalConfig;

public class ProcessAutoApprovalConfigImpl implements ProcessAutoApprovalConfig {

    private static final long serialVersionUID = -8970045818892715487L;
    private Long uidPk;
    private String processDefinitionKey;
    private String taskDefinitionKey;
    private boolean onProcess;
    private boolean enable;

    public Long getUidPk() {
        return uidPk;
    }

    public void setUidPk(Long uidPk) {
        this.uidPk = uidPk;
    }

    @Override
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    @Override
    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey == null ? null : processDefinitionKey.trim();
    }

    @Override
    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    @Override
    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey == null ? null : taskDefinitionKey.trim();
    }

    @Override
    public boolean isOnProcess() {
        return onProcess;
    }

    @Override
    public void setOnProcess(boolean onProcess) {
        this.onProcess = onProcess;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}