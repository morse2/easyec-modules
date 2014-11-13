package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.BpmRoleCtrl;
import com.googlecode.easyec.modules.bpmn2.domain.enums.RoleScopes;
import com.googlecode.easyec.spirit.domain.DomainModel;

/**
 * 业务流程框架的角色类
 *
 * @author JunJie
 */
public interface BpmRole extends DomainModel, BpmRoleCtrl {

    String getRoleCode();

    RoleScopes getRoleScope();

    String getRoleType();
}
