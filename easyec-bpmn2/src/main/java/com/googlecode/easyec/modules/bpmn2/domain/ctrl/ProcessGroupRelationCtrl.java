package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import com.googlecode.easyec.modules.bpmn2.domain.Group;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessRole;

/**
 * Created by JunJie on 2014/10/10.
 */
public interface ProcessGroupRelationCtrl {

    void setRole(ProcessRole role);

    void setGroup(Group group);

    void setProcessDefinitionKey(String processDefinitionKey);
}
