package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.ProcessGroupRelationCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;

/**
 * Created by JunJie on 2014/10/10.
 */
public interface ProcessGroupRelation extends DomainModel, ProcessGroupRelationCtrl {

    ProcessRole getRole();

    Group getGroup();

    String getProcessDefinitionKey();
}
