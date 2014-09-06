package com.googlecode.easyec.modules.bpmn2.support;

import com.googlecode.easyec.modules.bpmn2.domain.*;
import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;
import com.googlecode.easyec.modules.bpmn2.mail.MailingException;
import com.googlecode.easyec.modules.bpmn2.mail.SendMailDelegate;
import com.googlecode.easyec.modules.bpmn2.query.ProcessMailConfigQuery;
import com.googlecode.easyec.modules.bpmn2.query.UserTaskQuery;
import com.googlecode.easyec.modules.bpmn2.service.ProcessPersistentException;
import com.googlecode.easyec.modules.bpmn2.service.ProcessService;
import com.googlecode.easyec.modules.bpmn2.service.UserTaskService;
import com.googlecode.easyec.spirit.dao.DataPersistenceException;
import org.apache.commons.lang.ClassUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.core.Ordered;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig.FIRE_TYPE_TASK_ASSIGNED;
import static com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig.FIRE_TYPE_TASK_REJECTED;
import static com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes.BY_TASK_ANNOTATED;
import static org.activiti.engine.impl.identity.Authentication.getAuthenticatedUserId;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * 流程过程操作的业务拦截类。
 * <p>
 * 该类用于桥接和分离BPMN框架与业务层逻辑代码，
 * 使业务代码只需要兼顾业务，而不需要操作流程框架。
 * </p>
 *
 * @author JunJie
 */
@Aspect
public final class ProcessOperateInterceptor implements Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ProcessOperateInterceptor.class);

    private int order;

    /**
     * 设置此拦截类的执行顺序
     *
     * @param order 数值
     */
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    // ----- inject spring beans
    private ProcessService  processService;
    private UserTaskService userTaskService;

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    public void setUserTaskService(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    // ----- business process operation logic here

    /**
     * 执行删除流程数据的后置方法。
     *
     * @param entity 流程实体对象
     * @throws Throwable
     */
    @After("execution(* com.*..*.service.*Service.deleteDraft(..)) && args(entity,..)")
    public void afterDeleteDraft(ProcessObject entity) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("Prepare to delete a draft process entity. \n{");
            logger.debug("\tProcess create user: [{}].", entity.getCreateUser());
            logger.debug("\tProcess definition id: [{}].\n}", entity.getProcessDefinitionId());
        }

        try {
            processService.deleteDraft(entity.getUidPk());
        } catch (ProcessPersistentException e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }
    }

    /**
     * 执行预创建流程数据的后置方法。
     *
     * @param entity 流程实体对象
     * @throws Throwable
     */
    @After("execution(* com.*..*.service.*Service.prepareToNew(..)) && args(entity,..)")
    public void afterPrepareToNew(ProcessObject entity) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("Prepare to create a new draft process entity. \n{");
            logger.debug("\tProcess create user: [{}].", entity.getCreateUser());
            logger.debug("\tProcess definition id: [{}].\n}", entity.getProcessDefinitionId());
        }

        try {
            processService.createDraft(entity);
        } catch (ProcessPersistentException e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }
    }

    /**
     * 创建流程处理的后置方法。
     *
     * @param entity 流程实体对象
     * @param params 流程过程参数
     * @throws Throwable
     */
    @Around(
        value = "execution(* com.*..*.service.*Service.start(..)) && args(entity,params,..)",
        argNames = "jp,entity,params"
    )
    public Object aroundStart(ProceedingJoinPoint jp, ProcessObject entity, Map<String, Object> params)
        throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("After saving business data, then we start this process. {");
            logger.debug("\tProcess entity id: [{}].\n}", entity.getUidPk());
        }

        Object ret;

        try {
            // 如果流程对象主键没设置，则默认创建草稿
            processService.createDraft(entity);
            // 为即将启动的流程设置business key
            processService.generateBusinessKey(entity);
            // 回调被拦截的业务方法
            ret = jp.proceed(jp.getArgs());
            // 启动流程
            processService.startProcess(entity, params);
            // 添加附件
            List<AttachmentObject> attachments = entity.getAttachments();
            for (AttachmentObject attachment : attachments) {
                processService.addAttachment(entity, attachment);
            }

            // 执行邮件发送
            _loopTasksForSendingMail(
                _findNextTasks(entity.getProcessInstanceId()),
                FIRE_TYPE_TASK_ASSIGNED, null, null
            );
        } catch (ProcessPersistentException e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }

        if (logger.isDebugEnabled()) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            logger.debug(
                "The process is started. Instance id: ["
                + entity.getProcessInstanceId() + "], start time is: ["
                + df.format(entity.getRequestTime()) + "]."
            );
        }

        return ret;
    }

    /**
     * 审批通过的后置方法
     *
     * @param task      任务实体对象
     * @param comment   审批内容
     * @param variables 任务参数
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.approve(..)) && args(task,comment,variables,..)",
        argNames = "task,comment,variables"
    )
    public void afterApprove(TaskObject task, String comment, Map<String, Object> variables) throws Throwable {
        afterApprove(task, comment, variables, true);
    }

    /**
     * 审批通过的后置方法
     *
     * @param task      任务实体对象
     * @param comment   审批内容
     * @param variables 任务参数
     * @param commented 标识是否需要创建备注
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.approve(..)) && args(task,comment,variables,commented,..)",
        argNames = "task,comment,variables,commented"
    )
    public void afterApprove(TaskObject task, String comment, Map<String, Object> variables, boolean commented)
        throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("Prepare to approve this task. Task id: [{}].", task.getTaskId());
        }

        try {
            userTaskService.approveTask(task, comment, variables, commented);

            // 执行邮件发送
            _loopTasksForSendingMail(
                _findNextTasks(task.getProcessObject().getProcessInstanceId()),
                FIRE_TYPE_TASK_ASSIGNED, task, comment
            );
        } catch (ProcessPersistentException e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Task has been approved. Task id: [{}].", task.getTaskId());
        }
    }

    /**
     * 拒绝任务的后置方法。
     *
     * @param task      任务实体对象
     * @param comment   拒绝内容
     * @param variables 任务参数
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.reject(..)) && args(task,comment,variables,..)",
        argNames = "task,comment,variables"
    )
    public void afterReject(TaskObject task, String comment, Map<String, Object> variables) throws Throwable {
        afterReject(task, comment, variables, true);
    }

    /**
     * 拒绝任务的后置方法。
     *
     * @param task      任务实体对象
     * @param comment   拒绝内容
     * @param variables 任务参数
     * @param commented 标识是否需要创建备注
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.reject(..)) && args(task,comment,variables,commented,..)",
        argNames = "task,comment,variables,commented"
    )
    public void afterReject(TaskObject task, String comment, Map<String, Object> variables, boolean commented)
        throws Throwable {
        try {
            userTaskService.rejectTask(task, comment, variables, commented);

            // 执行邮件发送
            _loopTasksForSendingMail(
                _findNextTasks(task.getProcessObject().getProcessInstanceId()),
                FIRE_TYPE_TASK_REJECTED, task, comment
            );
        } catch (ProcessPersistentException e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Task has been partially rejected. Task id: [{}].", task.getTaskId());
        }
    }

    /**
     * 部分拒绝任务的后置方法。
     *
     * @param task      任务实体对象
     * @param comment   拒绝内容
     * @param variables 任务参数
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.rejectPartially(..)) && args(task,comment,variables,..)",
        argNames = "task,comment,variables"
    )
    public void afterPartialReject(TaskObject task, String comment, Map<String, Object> variables) throws Throwable {
        afterPartialReject(task, comment, variables, true);
    }

    /**
     * 部分拒绝任务的后置方法。
     *
     * @param task      任务实体对象
     * @param comment   拒绝内容
     * @param variables 任务参数
     * @param commented 标识是否需要创建备注
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.rejectPartially(..)) && args(task,comment,variables,commented,..)",
        argNames = "task,comment,variables,commented"
    )
    public void afterPartialReject(TaskObject task, String comment, Map<String, Object> variables, boolean commented)
        throws Throwable {
        try {
            userTaskService.rejectTaskPartially(task, comment, variables, commented);

            // 执行邮件发送
            _loopTasksForSendingMail(
                _findNextTasks(task.getProcessObject().getProcessInstanceId()),
                FIRE_TYPE_TASK_ASSIGNED, task, comment
            );

            // 邮件通知申请人
            _doSendMail(task, task, comment, FIRE_TYPE_TASK_REJECTED);
        } catch (ProcessPersistentException e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Task has been partially rejected. Task id: [{}].", task.getTaskId());
        }
    }

    /**
     * 选取任务的后置方法。
     *
     * @param task 任务实体对象
     * @throws Throwable
     */
    @After("execution(* com.*..*.service.*Service.claim(..)) && args(task,..)")
    public void afterClaim(TaskObject task) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("Prepare to claim this task. Task id: [{}].", task.getTaskId());
        }

        try {
            userTaskService.claimTask(
                task.getTaskId(),
                getAuthenticatedUserId()
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Task has been claimed. Task id: [{}].", task.getTaskId());
        }
    }

    /**
     * 释放已选取任务的方法。
     *
     * @param task 任务实体对象
     * @throws Throwable
     */
    @After("execution(* com.*..*.service.*Service.unclaim(..)) && args(task,..)")
    public void afterUnclaim(TaskObject task) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("Prepare to unclaim this task. Task id: [{}].", task.getTaskId());
        }

        try {
            userTaskService.unclaimTask(task.getTaskId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Task has been unclaimed. Task id: [{}].", task.getTaskId());
        }
    }

    /**
     * 添加任务备注的前置方法。
     *
     * @param task    任务实体对象
     * @param comment 任务备注对象
     * @throws Throwable
     */
    @Before(
        value = "execution(* com.*..*.service.*Service.addComment(..)) && args(task,comment,..)",
        argNames = "task,comment"
    )
    public void beforeAddComment(TaskObject task, CommentObject comment) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug(
                "Prepare to add a comment. Comment type: ["
                + comment.getType() + "], content: ["
                + comment.getContent() + "]."
            );
        }

        try {
            userTaskService.createComment(task, comment.getType(), comment.getContent());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 执行委托任务操作的后置方法
     *
     * @param task 任务实体对象
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.consign(..)) && args(task,userId,..)",
        argNames = "task,userId"
    )
    public void afterConsignTask(TaskObject task, String userId) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug(
                "Prepare to consign this task. Task id: ["
                + task.getTaskId() + "]."
            );
        }

        try {
            userTaskService.delegateTask(task, userId);
            // TODO 也许需要发送邮件提醒
        } catch (ProcessPersistentException e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }
    }

    /**
     * 被委托人完成任务处理的后置方法。
     *
     * @param task      任务实体对象
     * @param comment   备注内容
     * @param variables 流程变量
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.resolve(..)) && args(task,comment,variables,..)",
        argNames = "task,comment,variables"
    )
    public void afterResolveTask(TaskObject task, String comment, Map<String, Object> variables) throws Throwable {
        afterResolveTask(task, BY_TASK_ANNOTATED, comment, variables);
    }

    /**
     * 被委托人完成任务处理的后置方法。
     *
     * @param task      任务实体对象
     * @param type      备注类型
     * @param comment   备注内容
     * @param variables 流程变量
     * @throws Throwable
     */
    @After(
        value = "execution(* com.*..*.service.*Service.resolve(..)) && args(task,type,comment,variables,..)",
        argNames = "task,type,comment,variables"
    )
    public void afterResolveTask(TaskObject task, CommentTypes type, String comment, Map<String, Object> variables)
        throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("Prepare to resolve this task. Task id: [" + task.getTaskId() + "].");
        }

        try {
            userTaskService.resolveTask(task, type, comment, variables);
        } catch (ProcessPersistentException e) {
            logger.error(e.getMessage(), e);

            throw new DataPersistenceException(e);
        }
    }

    // ----- private method here
    /* 查询下一个节点的用户任务列表 */
    private List<TaskObject> _findNextTasks(String processInstanceId) {
        return new UserTaskQuery().processInstanceId(processInstanceId).list();
    }

    /* 循环任务列表并为之发送邮件 */
    private void _loopTasksForSendingMail(
        List<TaskObject> newTasks, String fireType, TaskObject oldTask, String comment
    ) {
        // 循环任务列表
        for (TaskObject newTask : newTasks) {
            // 判断任务处理人是否为空
            if (isBlank(newTask.getAssignee())) {
                logger.warn("Task has no assignee, so ignore sending mail. task id: [{}].", newTask.getTaskId());

                continue;
            }

            // 执行邮件发送业务
            _doSendMail(newTask, oldTask, comment, fireType);
        }
    }

    /* 执行统一邮件发送的方法 */
    private void _doSendMail(TaskObject newTask, TaskObject oldTask, String comment, String fireType) {
        Long processEntityId = newTask.getProcessObject().getUidPk();
        logger.debug("Process entity id: [{}].", processEntityId);

        // 获取邮件的配置信息
        List<ProcessMailConfig> mailConfigs
            = new ProcessMailConfigQuery()
            .processEntityId(processEntityId)
            .fireType(fireType)
            .list();

        // 如果没有邮件配置信息，则不发送邮件
        if (isEmpty(mailConfigs)) {
            logger.warn(
                "No Process mail configuration was found. Fire type: [" + fireType +
                "], entity id: [" + processEntityId + "]. So ignore operation else."
            );

            return;
        }

        try {
            // 默认获取第一条配置信息
            ProcessMailConfig config = mailConfigs.get(0);
            // 加载预定义的类信息
            Class cls = ClassUtils.getClass(config.getMailClass());
            logger.debug("Class name: [{}].", cls.getName());

            if (!SendMailDelegate.class.isAssignableFrom(cls)) {
                logger.warn(
                    "The class isn't assignable from [" +
                    SendMailDelegate.class.getName() + "], so ignore operation else."
                );

                return;
            }

            SendMailDelegate delegate;

            try {
                // 实例化配置的类
                delegate = (SendMailDelegate) BeanUtils.instantiateClass(cls);
                delegate.sendMail(newTask, oldTask, comment, config.getFileKey());
            } finally {
                delegate = null;
            }
        } catch (ClassNotFoundException e) {
            logger.error("Class is not found that configs in DB. Error msg: [{}].", e.getMessage());
        } catch (BeanInstantiationException e) {
            logger.error("Bean cannot be instantiated. Error msg: [{}].", e.getMessage());
        } catch (MailingException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
