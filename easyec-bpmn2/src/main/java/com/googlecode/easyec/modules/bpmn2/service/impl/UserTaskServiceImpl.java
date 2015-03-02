package com.googlecode.easyec.modules.bpmn2.service.impl;

import com.googlecode.easyec.modules.bpmn2.command.CreateNewCommentCmd;
import com.googlecode.easyec.modules.bpmn2.command.CreateNewTaskCmd;
import com.googlecode.easyec.modules.bpmn2.dao.CommentObjectDao;
import com.googlecode.easyec.modules.bpmn2.dao.ExtraTaskConsignDao;
import com.googlecode.easyec.modules.bpmn2.dao.ExtraTaskObjectDao;
import com.googlecode.easyec.modules.bpmn2.dao.ProcessObjectDao;
import com.googlecode.easyec.modules.bpmn2.domain.*;
import com.googlecode.easyec.modules.bpmn2.domain.impl.CommentObjectImpl;
import com.googlecode.easyec.modules.bpmn2.domain.impl.ExtraTaskConsignImpl;
import com.googlecode.easyec.modules.bpmn2.domain.impl.ExtraTaskObjectImpl;
import com.googlecode.easyec.modules.bpmn2.query.UserTaskQuery;
import com.googlecode.easyec.modules.bpmn2.service.ProcessManagementService;
import com.googlecode.easyec.modules.bpmn2.service.ProcessPersistentException;
import com.googlecode.easyec.modules.bpmn2.service.QueryProcessService;
import com.googlecode.easyec.modules.bpmn2.service.UserTaskService;
import com.googlecode.easyec.modules.bpmn2.task.NewTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign.TASK_CONSIGN_CONSIGNED;
import static com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject.*;
import static com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig.FIRE_TYPE_TASK_ASSIGNED;
import static com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes.BY_OTHERS;
import static com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessStatus.ARCHIVED;
import static com.googlecode.easyec.modules.bpmn2.utils.MailConfigUtils.sendMail;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 流程用户任务业务操作实现类
 *
 * @author JunJie
 */
public class UserTaskServiceImpl implements UserTaskService {

    private static final Logger logger = LoggerFactory.getLogger(UserTaskServiceImpl.class);

    @Resource
    private ExtraTaskConsignDao extraTaskConsignDao;

    @Resource
    private ExtraTaskObjectDao extraTaskObjectDao;

    @Resource
    private CommentObjectDao commentObjectDao;

    @Resource
    private ProcessObjectDao processObjectDao;

    @Resource
    private ManagementService managementService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private QueryProcessService queryProcessService;

    @Resource
    private ProcessManagementService processManagementService;

    @Override
    public void claimTask(String taskId, String userId) throws ProcessPersistentException {
        try {
            // 获取当前的任务并交给自己处理
            taskService.claim(taskId, userId);
            // 设置当前任务扩展表中的处理人
            _updateExtraTaskAssignee(taskId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void unclaimTask(String taskId) {
        // 将当前任务放回任务池中
        taskService.unclaim(taskId);
        // 设置当前任务扩展表中的处理人
        _updateExtraTaskAssignee(taskId, null);
    }

    @Override
    public void approveTask(TaskObject task, Map<String, Object> variables) throws ProcessPersistentException {
        _completeUserTask(task, false, variables);
    }

    @Override
    public void rejectTask(TaskObject task, Map<String, Object> variables) throws ProcessPersistentException {
        _completeUserTask(task, true, false, variables);
    }

    @Override
    public void rejectTaskPartially(TaskObject task, Map<String, Object> variables) throws ProcessPersistentException {
        _completeUserTask(task, false, true, variables);
    }

    @Override
    public void delegateTask(TaskObject task, String userId, String commentId) throws ProcessPersistentException {
        try {
            ProcessObject po = task.getProcessObject();
            taskService.delegateTask(task.getTaskId(), userId);

            // 记录此任务已委托的历史信息
            Date currentTime = new Date();
            ExtraTaskConsign con = new ExtraTaskConsignImpl();
            con.setConsignee(userId);
            con.setCommentId(commentId);
            con.setTaskId(task.getTaskId());
            con.setProcessInstanceId(po.getProcessInstanceId());
            con.setStatus(TASK_CONSIGN_CONSIGNED);
            con.setCreateTime(currentTime);
            con.setFinishTime(currentTime);

            int i = extraTaskConsignDao.insert(con);
            logger.debug("Effect rows of inserting BPM_HI_TASK_CONSIGN. [{}].", i);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void resolveTask(TaskObject task, String status, String commentId, Map<String, Object> variables)
    throws ProcessPersistentException {
        try {
            // 创建任务委托的历史记录
            Date currentTime = new Date();
            ExtraTaskConsign con = new ExtraTaskConsignImpl();
            con.setConsignee(task.getAssignee());
            con.setCommentId(commentId);
            con.setTaskId(task.getTaskId());
            con.setProcessInstanceId(task.getProcessObject().getProcessInstanceId());
            con.setStatus(status);
            con.setCreateTime(currentTime);
            con.setFinishTime(currentTime);

            int i = extraTaskConsignDao.insert(con);
            logger.debug("Effect rows of inserting BPM_HI_TASK_CONSIGN. [{}].", i);

            // 标记当前流程的任务已经解决
            taskService.resolveTask(task.getTaskId(), variables);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public CommentObject createComment(TaskObject task, String type, String comment, String role, String action)
    throws ProcessPersistentException {
        return _createComment(
            task.getTaskId(),
            task.getProcessObject().getProcessInstanceId(),
            type,
            comment,
            role,
            action
        );
    }

    @Override
    public CommentObject createComment(ProcessObject po, String type, String comment, String role, String action)
    throws ProcessPersistentException {
        return _createComment(null, po.getProcessInstanceId(), type, comment, role, action);
    }

    @Override
    public void createExtraTask(ExtraTaskObject o) throws ProcessPersistentException {
        String taskId = o.getTaskId();
        logger.debug("Task id: [" + taskId + "] want to create or update.");

        if (extraTaskObjectDao.countByTaskId(taskId) < 1) {
            logger.info("No extra-task object was found, and create new one. Task id: [{}].", taskId);

            // 设置新增的记录状态为pending
            o.setStatus(EXTRA_TASK_STATUS_PENDING);

            try {
                int i = extraTaskObjectDao.insert(o);
                logger.debug("Effect rows of inserting BPM_HI_TASK_EXTRA. [{}].", i);
            } catch (Exception e) {
                logger.error(e.getMessage());

                throw new ProcessPersistentException(e);
            }
        }
    }

    @Override
    public void setAssignee(String taskId, String userId, boolean notifyUser) throws ProcessPersistentException {
        try {
            // 更新当前任务的处理人
            taskService.setAssignee(taskId, userId);
            // 更新当前任务扩展表中的处理人
            _updateExtraTaskAssignee(taskId, userId);

            if (notifyUser) {
                // 发送任务提醒邮件
                List<TaskObject> tasks = new UserTaskQuery().taskId(taskId).list();
                if (isNotEmpty(tasks)) {
                    TaskObject task = tasks.get(0);
                    sendMail(task, task, null, FIRE_TYPE_TASK_ASSIGNED);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void saveNewTask(NewTask newTask) {
        Execution execution = null;

        if (isNotBlank(newTask.getProcessInstanceId())) {
            execution = runtimeService.createExecutionQuery()
                .processInstanceId(newTask.getProcessInstanceId())
                .singleResult();
        }

        // 执行新建任务的方法
        Task task = managementService.executeCommand(
            new CreateNewTaskCmd(newTask, execution)
        );

        // 执行发送邮件通知的方法
        List<TaskObject> list = new UserTaskQuery().taskId(task.getId()).list();
        if (isNotEmpty(list)) {
            TaskObject current = list.get(0);
            sendMail(current, current, null, FIRE_TYPE_TASK_ASSIGNED);
        }
    }

    @Override
    public void setDelegatedUser(String taskId, String delegatedUser) throws ProcessPersistentException {
        ExtraTaskObject obj = extraTaskObjectDao.selectByPrimaryKey(taskId);
        if (obj == null) {
            throw new ProcessPersistentException(
                "No Extra-task object was found. Please create extra-task firstly. Task id: [" + taskId + "]."
            );
        }

        obj.setDelegatedUser(obj.getAssignee());
        obj.setAssignee(delegatedUser);

        try {
            int i = extraTaskObjectDao.updateByPrimaryKey(obj);
            logger.debug("Effect rows of updating BPM_HI_TASK_EXTRA. [{}].", i);
        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void setExtraTaskStatus(String taskId, String status) throws ProcessPersistentException {
        ExtraTaskObject obj = extraTaskObjectDao.selectByPrimaryKey(taskId);
        if (obj == null) {
            throw new ProcessPersistentException(
                "No Extra-task object was found. Please create extra-task firstly. Task id: [" + taskId + "]."
            );
        }

        obj.setStatus(status);

        try {
            int i = extraTaskObjectDao.updateByPrimaryKey(obj);
        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public boolean hasApprovalPeople(String taskId) {
        return taskService.createNativeTaskQuery()
            .sql(
                "select count(distinct t.id_) from ACT_RU_TASK t left join ACT_RU_IDENTITYLINK i on t.id_ = i.task_id_" +
                    " where t.id_ = #{taskId,jdbcType=VARCHAR} and (t.assignee_ is not null or (i.type_ = 'candidate' and i.id_ is not null))"
            ).parameter("taskId", taskId)
            .count() > 0;
    }

    /* 创建备注的默认方法 */
    private CommentObject _createComment(String taskId, String processInstanceId, String type, String comment, String role, String action)
        throws ProcessPersistentException {
        if (isNotBlank(comment)) {
            String thisType = type;
            if (thisType == null) {
                thisType = BY_OTHERS.name();
            }

            try {
                Comment c = managementService.executeCommand(
                    new CreateNewCommentCmd(taskId, processInstanceId, type, comment)
                );

                if (c != null) {
                    CommentObject obj = new CommentObjectImpl();
                    obj.setCreateTime(c.getTime());
                    obj.setUserId(c.getUserId());
                    obj.setContent(comment);
                    obj.setType(thisType);
                    obj.setId(c.getId());

                    TaskObject task = null;
                    if (isNotBlank(taskId)) {
                        task = queryProcessService.getTask(taskId);
                    }

                    if (isNotBlank(role)) obj.setTaskRole(role);
                    else if (task != null) obj.setTaskRole(task.getTaskKey());

                    if (isNotBlank(action)) obj.setTaskAction(action);
                    else if (task != null) obj.setTaskAction(task.getStatus());

                    int i = commentObjectDao.insertExtraInfo(obj);
                    logger.debug("Effect rows of inserting BPM_HI_COMMENT_EXTRA. [{}].", i);

                    return obj;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);

                throw new ProcessPersistentException(e);
            }
        }

        return null;
    }

    /* 执行用户任务的完成操作 */
    private void _completeUserTask(TaskObject task, boolean reject, Map<String, Object> variables)
        throws ProcessPersistentException {
        ProcessObject po = task.getProcessObject();

        // 更新流程实体对象的属性
        po.setRejected(reject);
        // 完成流程当前任务的操作
        _completeUserTask(task, po, variables);
    }

    /* 执行用户任务的完成操作 */
    private void _completeUserTask(
        TaskObject task, boolean reject, boolean partialReject, Map<String, Object> variables
    ) throws ProcessPersistentException {
        ProcessObject po = task.getProcessObject();

        // 更新流程实体对象的属性
        po.setRejected(reject);
        po.setPartialRejected(partialReject);
        // 完成流程当前任务的操作
        _completeUserTask(task, po, variables);
    }

    /* 更新任务扩展表中的处理人 */
    private void _updateExtraTaskAssignee(String taskId, String userId) {
        ExtraTaskObject obj = extraTaskObjectDao.selectByPrimaryKey(taskId);
        if (obj != null) {
            obj.setAssignee(userId);

            int i = extraTaskObjectDao.updateByPrimaryKey(obj);
            logger.debug("Effect rows of updating BPM_HI_TASK_EXTRA. [{}].", i);
        }
    }

    /* 更新已存在的任务历史记录的状态 */
    private void _updateExtraHistoricTask(String taskId, boolean self, boolean rejected)
        throws ProcessPersistentException {
        ExtraTaskObject task = extraTaskObjectDao.selectByPrimaryKey(taskId);
        if (task == null) {
            logger.warn("No extra-task object was found. Task id: [{}].", taskId);

            return;
        }

        // 依据流程的拒绝状态来设置当前任务
        // 历史记录是审批通过还是被拒绝
        task.setStatus(
            self ? EXTRA_TASK_STATUS_RESUBMIT :
                rejected
                    ? EXTRA_TASK_STATUS_REJECTED
                    : EXTRA_TASK_STATUS_APPROVED
        );

        try {
            int i = extraTaskObjectDao.updateByPrimaryKey(task);
            logger.debug("Effect rows of updating BPM_HI_TASK_EXTRA. [{}].", i);
        } catch (Exception e) {
            logger.error(e.getMessage());

            throw new ProcessPersistentException(e);
        }
    }

    /* 完成流程当前任务的操作 */
    private void _completeUserTask(TaskObject task, ProcessObject po, Map<String, Object> variables)
        throws ProcessPersistentException {
        try {
            processObjectDao.updateByPrimaryKey(po);
            // 递归用户任务，并且执行必要的判断，是否需要执行自动审批逻辑
            _recursiveCompleteUserTask(task.getTaskId(), task.getAssignee(), po, variables);
            // 获取下一个用户任务节点的名称
            List<Task> taskList
                = taskService.createTaskQuery()
                .processInstanceId(po.getProcessInstanceId())
                .list();

            // TODO 未来支持分支任务的节点展现
            if (!taskList.isEmpty()) {
                for (Task current : taskList) {
                    // 设置流程任务的优先级
                    taskService.setPriority(current.getId(), po.getPriority());

                    // 新建当前任务的扩展信息
                    ExtraTaskObject obj = new ExtraTaskObjectImpl();
                    obj.setProcessInstanceId(po.getProcessInstanceId());
                    obj.setCreateTime(current.getCreateTime());
                    obj.setAssignee(current.getAssignee());
                    obj.setTaskId(current.getId());

                    createExtraTask(obj);
                }

                // 设置当前流程任务的节点名称
                po.setTaskName(taskList.get(0).getName());
            }
            // 无用户任务列表，表示任务已经结束
            else {
                long count = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(po.getProcessInstanceId())
                    .finished()
                    .count();

                // 如果计数器大于0，表示此流程实例的确已结束
                // ，则进行结束时候的操作
                if (count > 0) {
                    HistoricActivityInstance hai = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(po.getProcessInstanceId())
                        .activityType("endEvent")
                        .finished()
                        .singleResult();

                    // 设置结束节点名称
                    if (hai != null) po.setTaskName(hai.getActivityName());

                    // 对流程实体进行最后的操作
                    po.setProcessStatus(ARCHIVED);
                    po.setFinishTime(new Date());
                }
            }

            processObjectDao.updateByPrimaryKey(po);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    /* 递归完成用户任务的方法 */
    private void _recursiveCompleteUserTask(
        String taskId, String assignee, ProcessObject po, Map<String, Object> variables
    )
        throws ProcessPersistentException {
        // 获取流程实例ID
        String processInstanceId = po.getProcessInstanceId();

        // 继续流程的执行
        taskService.complete(taskId, variables);
        // 更新存在的任务历史扩展表的状态
        _updateExtraHistoricTask(taskId, assignee.equals(po.getCreateUser()), po.isRejected());

        // 如果当前任务是被拒绝的，那么系统将跳过自动审批的任务逻辑
        if (po.isRejected()) {
            logger.info("The process is rejected, so system won't check next tasks.");

            return;
        }

        // 查询下个节点的任务的审批人是否在历史任务中已审批过
        List<Task> taskList
            = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .list();

        for (Task task : taskList) {
            if (isBlank(task.getAssignee())) {
                logger.debug("Task has no assignee. Task name: [" + task.getName() + "].");

                continue;
            }

            // 当前任务的处理人是该流程的申请人，则忽略自动审批逻辑
            if (task.getAssignee().equals(po.getCreateUser())) {
                logger.info(
                    "The task assignee is equals with process applicant. So ignore approve logic."
                );

                continue;
            }

            // 判断任务是否需要系统自动进行审批操作
            boolean b = processManagementService.isTaskApprovedAutomatically(
                po.getProcessDefinitionKey(),
                task.getTaskDefinitionKey()
            );
            logger.debug("Should the task be approved automatically? [{}]", b);

            if (!b) {
                logger.debug("Task shouldn't be approved by system. Process key: [{}], task key: [{}]",
                    po.getProcessDefinitionKey(), task.getTaskDefinitionKey());

                continue;
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("processInstanceId", processInstanceId);
            params.put("assignee", task.getAssignee());

            // 如果有历史审批，则系统帮助其自动审批任务
            if (extraTaskObjectDao.countAsReject(processInstanceId) > 0) {
                if (extraTaskObjectDao.countTasksAsReject(params) > 0) {
                    logger.info("The assignee has approved historic tasks. Assignee: [{}].", task.getAssignee());

                    _recursiveCompleteUserTask(task.getId(), task.getAssignee(), po, variables);
                }
            } else if (extraTaskObjectDao.countTasksAsApprove(params) > 0) {
                logger.info("The assignee has approved historic tasks. Assignee: [{}].", task.getAssignee());

                _recursiveCompleteUserTask(task.getId(), task.getAssignee(), po, variables);
            }
        }
    }
}
