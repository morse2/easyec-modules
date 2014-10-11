package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessRole;

public interface ProcessRoleDao {

    int deleteByPrimaryKey(String code);

    int insert(ProcessRole record);

    ProcessRole selectByPrimaryKey(String code);

    int updateByPrimaryKey(ProcessRole record);
}