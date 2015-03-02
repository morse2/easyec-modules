package com.googlecode.easyec.bpmn2.test.service;

import com.googlecode.easyec.bpmn2.test.BaseBpmn2Test;
import com.googlecode.easyec.modules.bpmn2.domain.BpmRole;
import com.googlecode.easyec.modules.bpmn2.query.UserTaskQuery;
import com.googlecode.easyec.modules.bpmn2.service.ProcessManagementService;
import com.googlecode.easyec.modules.bpmn2.service.QueryProcessService;
import com.googlecode.easyec.modules.bpmn2.service.UserExistsException;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;
import com.googlecode.easyec.spirit.dao.paging.Page;
import com.googlecode.easyec.spirit.web.controller.formbean.impl.SearchFormBean;
import junit.framework.Assert;
import org.activiti.engine.IdentityService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by JunJie on 2014/10/10.
 */
public class ProcessManagementTest extends BaseBpmn2Test {

    @Resource
    private ProcessManagementService processManagementService;
    @Resource
    private QueryProcessService queryProcessService;
    @Resource
    private IdentityService identityService;

    @Test
    public void addUser() throws UserExistsException, DataPersistenceException {
        org.activiti.engine.identity.User user = identityService.newUser("N195FYJ");
        identityService.saveUser(user);

        List<org.activiti.engine.identity.User> list
            = identityService.createNativeUserQuery()
            .sql("select id_ from ACT_ID_USER where id_ = #{userId}")
            .parameter("userId", "N195FYJ")
            .list();

        System.out.println(list);
    }

    @Test
    public void findAvailableProcessList() {
        SearchFormBean bean = new SearchFormBean();
        bean.addSearchTerm("userId", "Q972EYJ");

        Page page = processManagementService.findProcessDefinitionsByApplicant(bean);
        System.out.println(page);

        List<ProcessDefinition> list = processManagementService.findProcessDefinitionsByApplicant("Q972EYJ");
        System.out.println(list);

        List<ProcessDefinition> definitions = processManagementService.findProcessDefinitionsForProcess("N395FER", "admin");
        System.out.println(definitions);
    }

    @Test
    public void listTasksByFlowAdmin() {
        Page page = queryProcessService.findTasks(
            new UserTaskQuery()
                .administrator("N195FYJ")
        );

        System.out.println(page);
    }

    @Test
    public void listProcessDefinitionsByFlowAdmin() {
        List<ProcessDefinition> list = processManagementService.findProcessDefinitionsByFlowAdmin("N195FYJ");
        System.out.println(list);
    }

    @Test
    public void listMyRoles() {
        List<BpmRole> list = processManagementService.findUserRoles("N195FYJ");
        for (BpmRole role : list) {
            List<String> keys = role.getProcessDefinitionKeys();
            System.out.println(keys);
        }
    }

    @Test
    public void isTaskAutoApprove() {
        boolean b = processManagementService.isTaskApprovedAutomatically(
            "normal_flow", "BGMTask"
        );

        Assert.assertTrue(b);
    }
}
