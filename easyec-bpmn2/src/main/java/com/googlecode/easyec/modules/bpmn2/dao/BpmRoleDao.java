package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.BpmRole;

import java.util.List;

/**
 * 业务流程框架的角色数据访问层接口类
 *
 * @author JunJie
 */
public interface BpmRoleDao {

    /**
     * 查询给定USER ID下关联的角色信息
     *
     * @param userId 用户ID
     * @return BPM框架的角色列表
     */
    List<BpmRole> selectByUserId(String userId);
}
