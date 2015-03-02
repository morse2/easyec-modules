package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessAutoApprovalConfig;

import java.util.Map;

public interface ProcessAutoApprovalConfigDao {

    ProcessAutoApprovalConfig selectByPrimaryKey(Long uidPk);

    int countBy(Map<String, Object> params);
}