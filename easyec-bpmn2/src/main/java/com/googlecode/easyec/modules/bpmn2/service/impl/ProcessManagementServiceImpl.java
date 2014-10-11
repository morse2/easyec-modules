package com.googlecode.easyec.modules.bpmn2.service.impl;

import com.googlecode.easyec.modules.bpmn2.dao.GroupDao;
import com.googlecode.easyec.modules.bpmn2.dao.UserDao;
import com.googlecode.easyec.modules.bpmn2.domain.Group;
import com.googlecode.easyec.modules.bpmn2.domain.User;
import com.googlecode.easyec.modules.bpmn2.domain.impl.GroupUserRelationPK;
import com.googlecode.easyec.modules.bpmn2.domain.impl.UserImpl;
import com.googlecode.easyec.modules.bpmn2.service.BpmUserAlreadyExistsException;
import com.googlecode.easyec.modules.bpmn2.service.BpmUserNotFoundException;
import com.googlecode.easyec.modules.bpmn2.service.ProcessManagementService;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;
import com.googlecode.easyec.spirit.mybatis.executor.annotation.Batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程管理的业务实现类
 *
 * @author JunJie
 */
@Service("processManagementService")
public class ProcessManagementServiceImpl implements ProcessManagementService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessManagementServiceImpl.class);

    @Resource
    private UserDao userDao;
    @Resource
    private GroupDao groupDao;

    @Override
    public User addUser(String userId) throws BpmUserAlreadyExistsException, DataPersistenceException {
        try {
            // 1. 创建用户信息
            User user = new UserImpl();
            user.setUserId(userId);
            user.setEnable(true);

            int i = userDao.insert(user);
            logger.debug("Effect rows of inserting BPM_USER. [{}].", i);

            // 2. 将用户添加至默认的流程组
            bindToGroups(userId, groupDao.selectDefault());

            return user;
        } catch (DuplicateKeyException e) {
            throw new BpmUserAlreadyExistsException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }
    }

    @Override
    public void setUserStatus(String userId, boolean enable) throws BpmUserNotFoundException, DataPersistenceException {
        // 1. 检查用户ID在系统中是否存在
        _checkUserExists(userId);

        try {
            // 2. 更新用户状态
            User user = userDao.selectByPrimaryKey(userId);
            user.setEnable(enable);

            int i = userDao.updateByPrimaryKey(user);
            logger.debug("Effect rows of updating BPM_USER. [{}].", i);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }
    }

    @Override
    public void deleteUser(String userId) throws BpmUserNotFoundException, DataPersistenceException {
        // 1. 检查用户ID在系统中是否存在
        _checkUserExists(userId);

        try {
            // 2. 删除用户与流程组的关系
            int i = groupDao.deleteAllGroupsByUserId(userId);
            logger.debug("Effect rows of deleting BPM_GROUP_USER_RELATION. [{}].", i);

            // 3. 删除用户信息
            i = userDao.deleteByPrimaryKey(userId);
            logger.debug("Effect rows of deleting BPM_USER. [{}].", i);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }
    }

    @Batch
    @Override
    public void bindToGroups(String userId, List<Group> groups) throws BpmUserNotFoundException, DataPersistenceException {
        // 1. 检查用户ID在系统中是否存在
        _checkUserExists(userId);

        try {
            // 2. 删除那些除了默认流程组外的其余的流程组关联数据
            int i = groupDao.deleteNonDefaultGroupsByUserId(userId);
            logger.debug("Effect rows of deleting BPM_GROUP_USER_RELATION. [{}].", i);

            // 3. 加载用户信息
            User user = userDao.selectByPrimaryKey(userId);

            // 4. 将用户与给定的流程组进行绑定
            for (Group group : groups) {
                groupDao.insertGroupUser(
                    new GroupUserRelationPK(user, group)
                );
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }
    }

    /* 检查用户ID是否在流程系统中存在 */
    private void _checkUserExists(String userId) throws BpmUserNotFoundException {
        if (userDao.selectCount(userId) < 1) {
            String err = "User id doesn't exist in flow system. [" + userId + "].";
            logger.warn(err);

            throw new BpmUserNotFoundException(err);
        }
    }
}
