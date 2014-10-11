package com.googlecode.easyec.bpmn2.test.service;

import com.googlecode.easyec.bpmn2.test.BaseBpmn2Test;
import com.googlecode.easyec.modules.bpmn2.domain.User;
import com.googlecode.easyec.modules.bpmn2.service.BpmUserAlreadyExistsException;
import com.googlecode.easyec.modules.bpmn2.service.ProcessManagementService;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by JunJie on 2014/10/10.
 */
public class ProcessManagementTest extends BaseBpmn2Test {

    @Resource
    private ProcessManagementService processManagementService;

    @Test
    public void addUser() throws BpmUserAlreadyExistsException, DataPersistenceException {
        User user = processManagementService.addUser("N195FYJ");
        logger.debug("User id: [" + user.getUserId() + "]");
    }
}
