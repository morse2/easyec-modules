package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import com.googlecode.easyec.modules.bpmn2.domain.AttachmentObject;
import com.googlecode.easyec.modules.bpmn2.domain.CommentObject;
import com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessPriority;
import com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessStatus;

import java.util.Date;
import java.util.List;

/**
 * 流程实体对象的控制类
 * <p>
 * 子类实现此接口，可以改变流程实体在运行过程中的数据信息
 * </p>
 *
 * @author JunJie
 */
public interface ProcessObjectCtrl {

    /**
     * 设置流程定义的ID
     *
     * @param procDefId 流程定义ID
     */
    void setProcessDefinitionId(String procDefId);

    /**
     * 设置流程定义的key
     *
     * @param processDefinitionKey 流程定义的key
     */
    void setProcessDefinitionKey(String processDefinitionKey);

    /**
     * 设置流程实例的ID
     *
     * @param procInstId 流程实例ID
     */
    void setProcessInstanceId(String procInstId);

    /**
     * 设置业务流程的KEY
     *
     * @param businessKey 业务流程KEY
     */
    void setBusinessKey(String businessKey);

    /**
     * 设置流程实例在运行中的状态
     *
     * @param procStatus 流程当前运行的状态
     */
    void setProcessStatus(ProcessStatus procStatus);

    /**
     * 设置流程的实体类型
     *
     * @param type 实体类型
     */
    void setType(String type);

    /**
     * 设置流程的优先级。
     * 数字越小，级别最高。
     * 数字越大，级别越低。
     *
     * @param priority 优先级
     */
    void setPriority(int priority);

    /**
     * 设置流程的优先级。
     * 优先等级从高到低是
     * P1-P5
     *
     * @param priority 优先级枚举
     */
    void setPriority(ProcessPriority priority);

    /**
     * 设置流程停留的任务名称
     *
     * @param taskName 任务名称
     */
    void setTaskName(String taskName);

    /**
     * 设置流程当前是否被拒绝
     *
     * @param rejected bool值
     */
    void setRejected(boolean rejected);

    /**
     * 设置流程当前是否是部分拒绝
     *
     * @param partialRejected bool值
     */
    void setPartialRejected(boolean partialRejected);

    /**
     * 设置流程的创建人
     *
     * @param createUser 流程创建人
     */
    void setCreateUser(String createUser);

    /**
     * 设置流程的创建时间
     *
     * @param createTime 流程的创建时间
     */
    void setCreateTime(Date createTime);

    void setRequestTime(Date requestTime);

    void setFinishTime(Date finishTime);

    void setApprovedComments(List<CommentObject> approvedComments);

    void setAttachments(List<AttachmentObject> attachments);

    boolean addAttachment(AttachmentObject attachment);
}
