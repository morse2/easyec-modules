package com.googlecode.easyec.modules.bpmn2.service;

import com.googlecode.easyec.modules.bpmn2.domain.Group;
import com.googlecode.easyec.modules.bpmn2.domain.User;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;

import java.util.List;

/**
 * 流程管理的业务接口类
 *
 * @author JunJie
 */
public interface ProcessManagementService {

    /**
     * 添加一个新用户到流程系统中。
     * 如果用户已存在于流程系统中，
     * 则抛出异常
     *
     * @param userId 用户ID
     * @return 流程用户对象
     * @throws BpmUserAlreadyExistsException
     */
    User addUser(String userId) throws BpmUserAlreadyExistsException, DataPersistenceException;

    /**
     * 设置用户的状态是否可用
     *
     * @param userId 用户ID
     * @param enable 用户状态
     * @throws BpmUserAlreadyExistsException
     */
    void setUserStatus(String userId, boolean enable) throws BpmUserNotFoundException, DataPersistenceException;

    /**
     * 删除存在的流程用户，
     * 如果用户不存在，则
     * 抛出异常
     *
     * @param userId 用户ID
     * @throws BpmUserNotFoundException
     */
    void deleteUser(String userId) throws BpmUserNotFoundException, DataPersistenceException;

    /**
     * 将流程用户添加到指定的流程组中，
     * 如果用户不存在，则抛出异常
     *
     * @param userId 用户ID
     * @param groups 流程组列表
     * @throws BpmUserNotFoundException
     */
    void bindToGroups(String userId, List<Group> groups) throws BpmUserNotFoundException, DataPersistenceException;
}
