package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import com.googlecode.easyec.modules.bpmn2.domain.enums.RoleScopes;

/**
 * 业务流程框架的角色属性的控制类
 *
 * @author JunJie
 */
public interface BpmRoleCtrl {

    void setRoleCode(String roleCode);

    void setRoleScope(RoleScopes roleType);

    void setRoleType(String roleType);
}
