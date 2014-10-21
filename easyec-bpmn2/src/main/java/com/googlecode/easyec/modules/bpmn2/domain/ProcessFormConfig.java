package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.spirit.domain.GenericPersistentDomainModel;

/**
 * Created by JunJie on 2014/10/21.
 */
public interface ProcessFormConfig extends GenericPersistentDomainModel<Long> {

    String getDefinitionKey();

    void setDefinitionKey(String definitionKey);

    String getFormType();

    void setFormType(String formType);

    String getFormKey();

    void setFormKey(String formKey);
}
