package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.BpmRole;
import com.googlecode.easyec.modules.bpmn2.domain.enums.RoleScopes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.googlecode.easyec.modules.bpmn2.domain.enums.RoleScopes.ForProcess;

/**
 * 业务流程框架的角色实现类
 *
 * @author JunJie
 */
public class BpmRoleImpl implements BpmRole {

    private static final long serialVersionUID = -5918613094157665041L;
    private String roleCode;
    private String roleType;
    private RoleScopes roleScope;

    private List<String> processDefinitionKeys = new ArrayList<String>();

    @Override
    public String getRoleCode() {
        return roleCode;
    }

    @Override
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public RoleScopes getRoleScope() {
        return roleScope;
    }

    @Override
    public void setRoleScope(RoleScopes roleScope) {
        this.roleScope = roleScope;
    }

    @Override
    public String getRoleType() {
        return roleType;
    }

    @Override
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    @Override
    public List<String> getProcessDefinitionKeys() {
        return ForProcess.equals(roleScope) ? processDefinitionKeys : Collections.<String>emptyList();
    }
}
