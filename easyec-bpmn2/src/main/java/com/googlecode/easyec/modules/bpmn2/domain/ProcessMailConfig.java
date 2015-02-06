package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.spirit.domain.GenericPersistentDomainModel;

/**
 * 流程发送邮件的配置的实体类
 *
 * @author JunJie
 */
public interface ProcessMailConfig extends GenericPersistentDomainModel<Long> {

    /**
     * 邮件触发类型：任务已分配
     */
    String FIRE_TYPE_TASK_ASSIGNED = "task_assigned";

    /**
     * 邮件触发类型：处理人在任务池中
     */
    String FIRE_TYPE_ASSIGNEE_IN_POOL = "assignee_in_pool";

    /**
     * 邮件触发类型：任务被拒绝
     */
    String FIRE_TYPE_TASK_REJECTED = "task_rejected";

    /**
     * 邮件触发类型：任务被撤回
     */
    String FIRE_TYPE_TASK_REVOKED = "task_revoked";

    /**
     * 邮件触发类型：任务被委托
     */
    String FIRE_TYPE_TASK_CONSIGNED = "task_consigned";

    /**
     * 邮件触发类型：流程被召回
     */
    String FIRE_TYPE_PROCESS_RECALL = "process_recall";

    /**
     * 返回流程定义的KEY
     *
     * @return 流程定义KEY
     */
    String getProcessKey();

    /**
     * 返回邮件触发类型
     *
     * @return 触发类型
     */
    String getFireType();

    /**
     * 返回邮件模板的相对路径
     *
     * @return 邮件模板路径
     */
    String getFileKey();

    /**
     * 返回邮件处理的实现类
     *
     * @return 邮件处理类
     */
    String getMailClass();
}
