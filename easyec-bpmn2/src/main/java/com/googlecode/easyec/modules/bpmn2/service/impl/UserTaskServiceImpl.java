package com.googlecode.easyec.modules.bpmn2.service.impl;

import com.googlecode.easyec.modules.bpmn2.dao.ExtraTaskObjectDao;
import com.googlecode.easyec.modules.bpmn2.dao.ProcessObjectDao;
import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;
import com.googlecode.easyec.modules.bpmn2.domain.impl.CommentObjectImpl;
import com.googlecode.easyec.modules.bpmn2.domain.impl.ExtraTaskObjectImpl;
import com.googlecode.easyec.modules.bpmn2.service.ProcessPersistentException;
import com.googlecode.easyec.modules.bpmn2.service.UserTaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes.BY_OTHERS;
import static com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes.BY_TASK_APPROVAL;
import static com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessStatus.ARCHIVED;
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
    private ExtraTaskObjectDao extraTaskObjectDao;

    @Resource
    private ProcessObjectDao processObjectDao;

    @Resource
    private HistoryService historyService;

    @Resource
    private TaskService taskService;

    @Override
    public void claimTask(String taskId, String userId) {
        taskService.claim(taskId, userId);
    }

    @Override
    public void unclaimTask(String taskId) {
        taskService.unclaim(taskId);
    }

    @Override
    public void approveTask(TaskObject task, String comment) throws ProcessPersistentException {
        approveTask(task, comment, null);
    }

    @Override
    public void approveTask(TaskObject task, String comment, Map<String, Object> variables)
    throws ProcessPersistentException {
        createComment(task, BY_TASK_APPROVAL, comment);
        _completeUserTask(task, false, variables);
    }

    @Override
    public void rejectTask(TaskObject task, String comment) throws ProcessPersistentException {
        rejectTask(task, comment, null);
    }

    @Override
    public void rejectTask(TaskObject task, String comment, Map<String, Object> variables)
    throws ProcessPersistentException {
        createComment(task, BY_TASK_APPROVAL, comment);
        _completeUserTask(task, true, false, variables);
    }

    @Override
    public void rejectTaskPartially(TaskObject task, String comment) throws ProcessPersistentException {
        rejectTaskPartially(task, comment, null);
    }

    @Override
    public void rejectTaskPartially(TaskObject task, String comment, Map<String, Object> variables)
    throws ProcessPersistentException {
        createComment(task, BY_TASK_APPROVAL, comment);
        _completeUserTask(task, false, true, variables);
    }

    @Override
    public void delegateTask(TaskObject task, String userId) throws ProcessPersistentException {
        try {
            taskService.delegateTask(task.getTaskId(), userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void resolveTask(TaskObject task, CommentTypes type, String comment, Map<String, Object> variables)
    throws ProcessPersistentException {
        try {
            createComment(task, type, comment);
            taskService.resolveTask(task.getTaskId(), variables);
        } catch (ProcessPersistentException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public CommentObject createComment(TaskObject task, CommentTypes type, String comment)
    throws ProcessPersistentException {
        if (isNotBlank(comment)) {
            CommentTypes thisType = type;
            if (thisType == null) {
                thisType = BY_OTHERS;
            }

            try {
                Comment c = taskService.addComment(
                    task.getTaskId(),
                    task.getProcessObject().getProcessInstanceId(),
                    thisType.name(),
                    comment
                );

                if (c != null) {
                    CommentObject obj = new CommentObjectImpl();
                    obj.setCreateTime(c.getTime());
                    obj.setUserId(c.getUserId());
                    obj.setContent(comment);
                    obj.setType(thisType);

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

    /* 将当前任务储存进额外的任务表中 */
    private void _createExtraHistoricTask(TaskObject task) throws ProcessPersistentException {
        ProcessObject po = task.getProcessObject();

        ExtraTaskObject o = new ExtraTaskObjectImpl();
        o.setTaskId(task.getTaskId());
        o.setAssignee(task.getAssignee());
        o.setProcessInstanceId(po.getProcessInstanceId());
        o.setStatus(po.isRejected() ? "rejected" : "approved");
        o.setCreateTime(task.getCreateTime());

        try {
            extraTaskObjectDao.insert(o);
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
            // 为当前任务创建审批状态
            _createExtraHistoricTask(task);
            // TODO 未来支持可配置选择是否一人只需审批一次
            _recursiveCompleteUserTask(task.getTaskId(), po.getProcessInstanceId(), variables);
            // 获取下一个用户任务节点的名称
            List<Task> taskList
                = taskService.createTaskQuery()
                .processInstanceId(po.getProcessInstanceId())
                .list();

            // TODO 未来支持分支任务的节点展现
            if (!taskList.isEmpty()) {
                // 设置流程任务的优先级
                for (Task current : taskList) {
                    taskService.setPriority(current.getId(), po.getPriority());
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
    private void _recursiveCompleteUserTask(String taskId, String processInstanceId, Map<String, Object> variables) {
        // 继续流程的执行
        taskService.complete(taskId, variables);
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

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("processInstanceId", processInstanceId);
            params.put("assignee", task.getAssignee());

            // 如果有历史审批，则系统帮助其自动审批任务
            if (extraTaskObjectDao.countTasksAsReject(params) > 0 ||
                extraTaskObjectDao.countTasksAsApprove(params) > 0) {
                logger.info("The assignee has approved historic tasks. Assignee: [{}].", task.getAssignee());

                _recursiveCompleteUserTask(task.getId(), processInstanceId, variables);
            }
        }
    }
}
