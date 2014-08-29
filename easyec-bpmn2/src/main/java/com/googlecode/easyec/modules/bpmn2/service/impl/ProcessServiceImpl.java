package com.googlecode.easyec.modules.bpmn2.service.impl;

import com.googlecode.easyec.modules.bpmn2.dao.ProcessObjectDao;
import com.googlecode.easyec.modules.bpmn2.domain.AttachmentObject;
import com.googlecode.easyec.modules.bpmn2.domain.ProcessObject;
import com.googlecode.easyec.modules.bpmn2.keys.generator.BusinessKeyGenerator;
import com.googlecode.easyec.modules.bpmn2.service.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.easyec.modules.bpmn2.domain.enums.AttachmentTypes.BY_PROC_ATTACHMENT;
import static com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessStatus.*;
import static com.googlecode.easyec.modules.bpmn2.utils.ProcessConstant.APPLICANT_ID;
import static com.googlecode.easyec.modules.bpmn2.utils.ProcessConstant.PROCESS_ENTITY_ID;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang.ArrayUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 流程操作的业务实现类
 *
 * @author JunJie
 */
public class ProcessServiceImpl implements ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    @Resource
    private ProcessObjectDao processObjectDao;

    // ----- Activiti运行时业务类
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private HistoryService    historyService;
    @Resource
    private RuntimeService    runtimeService;
    @Resource
    private TaskService       taskService;

    private BusinessKeyGenerator businessKeyGenerator;

    public void setBusinessKeyGenerator(BusinessKeyGenerator businessKeyGenerator) {
        this.businessKeyGenerator = businessKeyGenerator;
    }

    @Override
    public void deleteDraft(Long processEntityId) throws ProcessPersistentException {
        ProcessObject entity = _getProcessObject(processEntityId);
        if (!DRAFT.equals(entity.getProcessStatus())) {
            throw new WrongProcessStatusException(
                "This process' status isn't draft. Please check it. "
                + "Process id: [" + processEntityId + "]."
            );
        }

        try {
            processObjectDao.deleteByPrimaryKey(processEntityId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void createDraft(ProcessObject entity) throws ProcessPersistentException {
        try {
            Long uidPk = entity.getUidPk();
            logger.debug("Process entity's Uid PK: [{}].", uidPk);

            if (uidPk == null || processObjectDao.selectCountByPK(uidPk) < 1) {
                // 初始化新建流程的数据
                entity.setProcessStatus(DRAFT);
                entity.setCreateTime(new Date());

                // 获取流程定义对象
                ProcessDefinition definition
                    = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(entity.getProcessDefinitionId())
                    .singleResult();

                if (definition == null) {
                    throw new ProcessNotFoundException(
                        "Cannot find process definition info. Process definition id: ["
                        + entity.getProcessDefinitionId() + "]."
                    );
                }

                // 设置流程定义的KEY
                entity.setProcessDefinitionKey(definition.getKey());

                int i = processObjectDao.insert(entity);
                logger.debug("Effect row of insert BPM_PROC_ENTITY. [{}].", i);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void generateBusinessKey(ProcessObject entity) {
        if (businessKeyGenerator == null) {
            logger.warn("No any BusinessKeyGenerator was configured. So ignore this operation.");

            return;
        }

        // 生成业务流程的KEY
        entity.setBusinessKey(businessKeyGenerator.generateKey(entity));
    }

    @Override
    public void startProcess(ProcessObject entity) throws ProcessPersistentException {
        startProcess(entity, null);
    }

    @Override
    public void startProcess(ProcessObject entity, Map<String, Object> params) throws ProcessPersistentException {
        Long uidPk = entity.getUidPk();
        logger.debug("Process entity's Uid PK: [{}].", uidPk);

        // 重新加载ProcessObject对象数据
        ProcessObject po = processObjectDao.selectByPrimaryKey(
            entity.getUidPk()
        );

        // 设置业务流程的KEY
        po.setBusinessKey(entity.getBusinessKey());

        // 检查流程实体的状态是否已启动
        if (!DRAFT.equals(po.getProcessStatus()) || isNotBlank(po.getProcessInstanceId())) {
            throw new WrongProcessStatusException(
                "The process' status isn't draft. So it doesn't start "
                + "repeat. Please check it. Process id: ["
                + po.getUidPk() + "]."
            );
        }

        Map<String, Object> variables = new HashMap<String, Object>();
        if (isNotEmpty(params)) variables.putAll(params);

        variables.put(PROCESS_ENTITY_ID, po.getUidPk());
        variables.put(APPLICANT_ID, po.getCreateUser());

        try {
            ProcessInstance pi = runtimeService.startProcessInstanceById(
                po.getProcessDefinitionId(), po.getBusinessKey(),
                variables
            );

            // 改变流程实体对象属性
            po.setProcessInstanceId(pi.getId());
            po.setProcessStatus(IN_PROGRESS);
            po.setRequestTime(new Date());

            List<Task> taskList
                = taskService.createTaskQuery()
                .processInstanceId(pi.getProcessInstanceId())
                .list();

            // TODO 未来支持分支任务的节点展现
            if (!taskList.isEmpty()) {
                // 设置流程任务的优先级
                for (Task task : taskList) {
                    taskService.setPriority(task.getId(), entity.getPriority());
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

            _copyProperties(po, entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void addAttachment(ProcessObject entity, AttachmentObject attachment)
    throws WrongProcessValueException, ProcessPersistentException {
        String url = attachment.getUrl();
        byte[] bs = attachment.getContent();
        if (isEmpty(bs) && isBlank(url)) {
            throw new WrongProcessValueException(
                "Both url and content are null value."
            );
        }

        try {
            if (ArrayUtils.isNotEmpty(bs)) {
                taskService.createAttachment(
                    BY_PROC_ATTACHMENT.name(),
                    null,
                    entity.getProcessInstanceId(),
                    attachment.getName(),
                    attachment.getDescription(),
                    new ByteArrayInputStream(bs)
                );
            } else {
                taskService.createAttachment(
                    BY_PROC_ATTACHMENT.name(), null,
                    entity.getProcessInstanceId(),
                    attachment.getName(),
                    attachment.getDescription(),
                    url
                );
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public void setPartialReject(Long processEntityId, boolean partial) throws ProcessPersistentException {
        ProcessObject po = processObjectDao.selectByPrimaryKey(processEntityId);
        if (po == null) {
            throw new ProcessNotFoundException("Process object cannot be found. [" + processEntityId + "].");
        }

        po.setPartialRejected(partial);

        try {
            int i = processObjectDao.updateByPrimaryKey(po);
            logger.debug("Effect rows of updating BPM_PROC_ENTITY. [{}].", i);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new ProcessPersistentException(e);
        }
    }

    @Override
    public boolean isApproved(Long processEntityId) throws ProcessNotFoundException {
        ProcessObject entity = _getProcessObject(processEntityId);
        return !entity.isRejected();
        //        return !entity.isRejected() && !entity.isPartialRejected();
    }

    @Override
    public boolean isRejected(Long processInstanceId) throws ProcessNotFoundException {
        return _getProcessObject(processInstanceId).isRejected();
    }

    @Override
    public boolean isPartialRejected(Long processEntityId) throws ProcessNotFoundException {
        return _getProcessObject(processEntityId).isPartialRejected();
    }

    /* 获取流程对象 */
    private ProcessObject _getProcessObject(Long processEntityId) throws ProcessNotFoundException {
        ProcessObject entity = processObjectDao.selectByPrimaryKey(processEntityId);
        if (entity == null) {
            throw new ProcessNotFoundException(
                "Process object cannot be found in system. Process id: ["
                + processEntityId + "]."
            );
        }

        return entity;
    }

    /* 拷贝流程基本数据给业务对象 */
    private void _copyProperties(ProcessObject source, ProcessObject dest) {
        dest.setProcessDefinitionId(source.getProcessDefinitionId());
        dest.setProcessInstanceId(source.getProcessInstanceId());
        dest.setProcessStatus(source.getProcessStatus());
        dest.setBusinessKey(source.getBusinessKey());
        dest.setRequestTime(source.getRequestTime());

        dest.setRejected(source.isRejected());
        dest.setPartialRejected(source.isPartialRejected());
    }
}
