package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.ExtraTaskConsignCtrl;
import com.googlecode.easyec.spirit.domain.GenericPersistentDomainModel;

import java.util.Date;

/**
 * Created by JunJie on 2014/9/2.
 */
public interface ExtraTaskConsign extends ExtraTaskConsignCtrl, GenericPersistentDomainModel<Long> {

    /**
     * 任务委托常量类：已委托状态
     */
    String TASK_CONSIGN_CONSIGNED = "consigned";
    /**
     * 任务委托常量类：完成状态，表示同意
     */
    String TASK_CONSIGN_AGREED = "agreed";
    /**
     * 任务委托常量类：完成状态，表示不同意
     */
    String TASK_CONSIGN_DISAGREED = "disagreed";

    String getTaskId();

    String getCommentId();

    String getProcessInstanceId();

    String getConsignee();

    String getStatus();

    Date getCreateTime();

    Date getFinishTime();
}
