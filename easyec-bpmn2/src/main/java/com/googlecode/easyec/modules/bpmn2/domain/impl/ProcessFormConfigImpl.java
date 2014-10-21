package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessFormConfig;

public class ProcessFormConfigImpl implements ProcessFormConfig {

    private static final long serialVersionUID = 8992982908679894918L;
    private Long uidPk;
    private String definitionKey;
    private String formType;
    private String formKey;

    public Long getUidPk() {
        return uidPk;
    }

    public void setUidPk(Long uidPk) {
        this.uidPk = uidPk;
    }

    @Override
    public String getDefinitionKey() {
        return definitionKey;
    }

    @Override
    public void setDefinitionKey(String definitionKey) {
        this.definitionKey = definitionKey == null ? null : definitionKey.trim();
    }

    @Override
    public String getFormType() {
        return formType;
    }

    @Override
    public void setFormType(String formType) {
        this.formType = formType == null ? null : formType.trim();
    }

    @Override
    public String getFormKey() {
        return formKey;
    }

    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey == null ? null : formKey.trim();
    }
}