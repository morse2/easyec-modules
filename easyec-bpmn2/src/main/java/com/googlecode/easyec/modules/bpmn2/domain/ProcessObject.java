package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.ProcessObjectCtrl;
import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;
import com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessStatus;
import com.googlecode.easyec.spirit.domain.GenericPersistentDomainModel;

import java.util.Date;
import java.util.List;

/**
 * 流程实体对象。
 * <p>
 * 此模型对象用于基本流程数据的扩展
 * </p>
 *
 * @author JunJie
 */
public interface ProcessObject extends GenericPersistentDomainModel<Long>, ProcessObjectCtrl {

    /**
     * 返回流程定义的ID
     *
     * @return 流程定义ID
     */
    String getProcessDefinitionId();

    /**
     * 返回流程定义的KEY
     *
     * @return 流程定义KEY
     */
    String getProcessDefinitionKey();

    /**
     * 返回流程实例的ID
     *
     * @return 流程实例ID
     */
    String getProcessInstanceId();

    /**
     * 返回流程实例的业务流程KEY
     *
     * @return 业务流程KEY
     */
    String getBusinessKey();

    /**
     * 返回流程实例在运行中的状态
     *
     * @return 流程当前运行的状态
     */
    ProcessStatus getProcessStatus();

    /**
     * 返回当前流程实例的类型
     *
     * @return 流程类型
     */
    String getType();

    /**
     * 返回当前流程的优先级
     *
     * @return 流程优先级
     */
    int getPriority();

    /**
     * 返回流程停留的任务名称
     *
     * @return 任务名称
     */
    String getTaskName();

    /**
     * 判断流程当前是否被拒绝
     *
     * @return bool值
     */
    boolean isRejected();

    /**
     * 判断流程当前是否是部分拒绝。
     * <p>
     * 如果流程是部分拒绝，
     * 那么{@link #isRejected()}属性也必然返回真
     * </p>
     *
     * @return bool值
     */
    boolean isPartialRejected();

    /**
     * 返回流程的创建人
     *
     * @return 流程的创建人，即流程的申请人
     */
    String getCreateUser();

    /**
     * 返回流程的创建时间
     *
     * @return 流程的创建时间
     */
    Date getCreateTime();

    /**
     * 返回流程正式开始的时间
     *
     * @return 流程开始执行时间
     */
    Date getRequestTime();

    /**
     * 返回流程完成的时间
     *
     * @return 流程结束时间
     */
    Date getFinishTime();

    List<CommentObject> getComments();

    List<CommentObject> getApprovedComments();

    @SuppressWarnings("unchecked")
    List<CommentObject> getComments(List<CommentTypes> types);

    List<AttachmentObject> getAttachments();
}
