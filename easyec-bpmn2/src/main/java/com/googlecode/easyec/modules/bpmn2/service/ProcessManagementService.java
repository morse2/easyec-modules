package com.googlecode.easyec.modules.bpmn2.service;

import com.googlecode.easyec.modules.bpmn2.domain.BpmRole;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;
import com.googlecode.easyec.spirit.dao.paging.Page;
import com.googlecode.easyec.spirit.web.controller.formbean.impl.AbstractSearchFormBean;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * 流程管理的业务接口类
 *
 * @author JunJie
 */
public interface ProcessManagementService {

    /**
     * 查询流程管理员可见的流程信息
     *
     * @param userId 用户ID
     * @return 流程定义列表
     */
    List<ProcessDefinition> findProcessDefinitionsByFlowAdmin(String userId);

    /**
     * 查询给定角色类型对应的可见流程信息
     *
     * @param userId   用户ID
     * @param roleType 流程角色类型
     * @return 流程定义列表
     */
    List<ProcessDefinition> findProcessDefinitionsForProcess(String userId, String roleType);

    /**
     * 查询流程申请人可见的流程信息
     *
     * @param userId 用户ID
     * @return 流程定义列表
     */
    List<ProcessDefinition> findProcessDefinitionsByApplicant(String userId);

    /**
     * 查询流程申请人可见的流程信息
     *
     * @param bean 查询条件对象
     * @return 分页结果对象
     */
    Page findProcessDefinitionsByApplicant(AbstractSearchFormBean bean);

    /**
     * 查找给定用户所包含的业务流程的角色信息
     *
     * @param userId 用户ID
     * @return 业务流程角色信息列表
     */
    List<BpmRole> findUserRoles(String userId);

    /**
     * 添加一个新用户到流程系统中。
     * 如果用户已存在于流程系统中，
     * 则抛出异常
     *
     * @param userId 用户ID
     * @return 流程用户对象
     * @throws UserExistsException
     */
    User addUser(String userId) throws UserExistsException, DataPersistenceException;

    /**
     * 删除存在的流程用户，
     * 如果用户不存在，则
     * 抛出异常
     *
     * @param userId 用户ID
     */
    void deleteUser(String userId) throws DataPersistenceException;
}
