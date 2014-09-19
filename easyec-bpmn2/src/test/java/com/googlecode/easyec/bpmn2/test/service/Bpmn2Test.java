package com.googlecode.easyec.bpmn2.test.service;

import com.googlecode.easyec.bpmn2.test.BaseBpmn2Test;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskDefinition;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.impl.AttachmentObjectImpl;
import com.googlecode.easyec.modules.bpmn2.domain.impl.ProcessObjectImpl;
import com.googlecode.easyec.modules.bpmn2.query.ProcessMailConfigQuery;
import com.googlecode.easyec.modules.bpmn2.query.ProcessQuery;
import com.googlecode.easyec.modules.bpmn2.query.UserTaskQuery;
import com.googlecode.easyec.modules.bpmn2.service.*;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;
import com.googlecode.easyec.spirit.dao.paging.Page;
import junit.framework.Assert;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig.FIRE_TYPE_TASK_ASSIGNED;
import static com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes.BY_TASK_ANNOTATED;
import static com.googlecode.easyec.spirit.web.controller.sorts.Sort.SortTypes.ASC;
import static com.googlecode.easyec.spirit.web.controller.sorts.Sort.SortTypes.DESC;

/**
 * Test class
 *
 * @author JunJie
 */
public class Bpmn2Test extends BaseBpmn2Test {

    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;

    @Autowired
    private ProcessService processService;
    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private QueryProcessService queryProcessService;

    @Autowired
    private ProcessDelegateService processDelegateService;

    @Before
    public void before() {
        Authentication.setAuthenticatedUserId("kermit");
    }

    @Test
    public void viewProcessDefinition() {
        List<ProcessDefinition> list
            = repositoryService.createProcessDefinitionQuery()
            .processDefinitionKey("rpp_flow")
            .latestVersion()
            .list();

        Assert.assertTrue(!list.isEmpty());
    }

    @Test
    @Rollback(false)
    public void deleteProcessDefinition() {
        repositoryService.deleteDeployment("901");
    }

    @Test
    public void countTasks() {
        long i = new UserTaskQuery()
            .candidateUser("V427SKS")
            .unclaimedTask()
            .count();

        System.out.println(i);

        Page page = new UserTaskQuery()
            .owner("V427SKS")
            .delegated(true, false)
            .orderByPriority(ASC)
            .orderByRequestTime(DESC)
            .listPage(1);

        System.out.println(page);
    }

    @Test
    @Rollback(false)
    public void prepareToNew() throws ProcessPersistentException {
        ProcessObject entity = new ProcessObjectImpl("myProcess:1:3503", "JUNJIE");

        processService.createDraft(entity);
        Assert.assertNotNull(entity.getUidPk());
    }

    @Test
    public void findMyDrafts() {
        Page page = new ProcessQuery()
            .applicantId("D671CBC")
            .listPage(1);

        Assert.assertTrue(page.getTotalSize() > 0);
    }

    @Test
    public void findMyRequests() {
        List<ProcessObject> list
            = new ProcessQuery()
            .applicantId("D671CBC")
            .customJoin("IVC ivc", "ivc.uidpk")
            .customWhere("ivc.depot = 'SHA' and ivc.region <> #{region}")
            .customTerm("region", "SRH")
            .list();

        System.out.println(list);
    }

    @Test
    @Rollback(false)
    public void startProcessWithParameters() throws ProcessPersistentException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("MyName", "JunJie");

        Page page = new ProcessQuery()
            .applicantId("D671CBC")
            .listPage(1);

        List<?> records = page.getRecords();
        if (!records.isEmpty()) {
            ProcessObject entity = (ProcessObject) records.get(0);
            processService.startProcess(entity, params);
        }
    }

    @Test
    public void findMyTasks() throws WrongProcessValueException {
        Page page = queryProcessService.findTasks(
            new UserTaskQuery().taskAssignee("W674HKL")
            .customJoin("IVC ivc", "ivc.uidpk")
            .customWhere("ivc.depot = 'SHA' and ivc.region <> #{region}")
            .customTerm("region", "SRH")
        );

        Assert.assertNotNull(page);
    }

    @Test
    @Rollback(false)
    public void addComment() throws WrongProcessValueException, ProcessPersistentException {
        Page page = queryProcessService.findTasks(
            new UserTaskQuery()
                .taskAssignee("kermit")
                .orderByPriority(ASC)
        );

        List<?> records = page.getRecords();
        Assert.assertTrue(!records.isEmpty());

        TaskObject task = (TaskObject) records.get(0);

        userTaskService.createComment(
            task, BY_TASK_ANNOTATED,
            "This is a task's comment."
        );
    }

    @Test
    public void getTaskName() {
        HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
            .processInstanceId("1701")
            .singleResult();

        System.out.println(hpi);

        HistoricActivityInstance hai = historyService.createHistoricActivityInstanceQuery()
            .processInstanceId("5001")
            .finished()
            .activityType("endEvent")
            .singleResult();

        System.out.println(hai);
    }

    @Test
    public void groupByTask() {
        List<TaskDefinition> list = queryProcessService.groupByTaskDefinition(
            new UserTaskQuery().candidateUser("P499HXF").unclaimedTask()
        );

        System.out.println(list);
    }

    @Test
    @Rollback(false)
    public void addAttachment() throws WrongProcessValueException, ProcessPersistentException {
        Page page = new ProcessQuery()
            .applicantId("D671CBC")
            .listPage(1);

        Assert.assertTrue(page.getTotalSize() > 0);

        List<?> list = page.getRecords();
        ProcessObject entity = (ProcessObject) list.get(0);

        AttachmentObjectImpl a = new AttachmentObjectImpl();
        a.setName("baidu.link");
        a.setDescription("This is a baidu page link.");
        a.setUrl("http://www.baidu.com");

        processService.addAttachment(entity, a);
    }

    @Test
    public void findProcessVariables() throws WrongProcessValueException {
        Page page = new UserTaskQuery()
            .taskAssignee("kermit")
            .listPage(1);

        Assert.assertTrue(page.getTotalSize() > 0);

        List<?> list = page.getRecords();
        TaskObject task = (TaskObject) list.get(0);

        taskService.setVariable(task.getTaskId(), "MyTask1", task.getTaskId());

        Map<String, Object> variables = runtimeService.getVariables("3701");
        System.out.println(variables);
    }

    @Test
    public void invokeByAOP() throws DataPersistenceException, WrongProcessValueException {
        ProcessObject entity = new ProcessObjectImpl();
        entity.setCreateUser("JUNJIE");
        entity.setProcessDefinitionId("myProcess:7:1903");

        processDelegateService.prepareToNew(entity);
        processDelegateService.start(entity, null, null);

        _approveOrReject(entity, "kermit", true);
        _approveOrReject(entity, "gonzo", true);
        _approveOrReject(entity, "kermit", true);
    }

    @Test
    public void findTaskList() {
        List<ProcessMailConfig> list
            = new ProcessMailConfigQuery()
            .fireType(FIRE_TYPE_TASK_ASSIGNED)
            .processEntityId(508L)
            .list();

        System.out.println(list);
    }

    private void _approveOrReject(ProcessObject entity, String userId, boolean approve)
    throws DataPersistenceException {
        Page page = new UserTaskQuery()
            .processInstanceId(entity.getProcessInstanceId())
            .taskAssignee(userId)
            .listPage(1);

        Assert.assertTrue(page.getTotalSize() > 0);
        TaskObject task = page.<TaskObject>getRecords().get(0);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("task1", task.getTaskId());

        if (approve) processDelegateService.approve(task, "Test approve.", variables, null);
        else processDelegateService.reject(task, "Test reject", variables, null);
    }
}
