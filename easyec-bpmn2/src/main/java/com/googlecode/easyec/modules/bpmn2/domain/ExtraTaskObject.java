package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.ExtraTaskObjectCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;

import java.util.Date;

/**
 * Created by JunJie on 2014/8/24.
 */
public interface ExtraTaskObject extends DomainModel, ExtraTaskObjectCtrl {

    /**
     * 任务扩展表状态：未决，指未审批
     */
    String EXTRA_TASK_STATUS_PENDING  = "pending";
    /**
     * 任务扩展表状态：拒绝
     */
    String EXTRA_TASK_STATUS_REJECTED = "rejected";
    /**
     * 任务扩展表状态：通过
     */
    String EXTRA_TASK_STATUS_APPROVED = "approved";
    /**
     * 任务扩展表状态：重新提交
     */
    String EXTRA_TASK_STATUS_RESUBMIT   = "resubmit";

    String getTaskId();

    String getAssignee();

    String getStatus();

    Date getCreateTime();

    String getProcessInstanceId();

    String getDelegatedUser();

}
