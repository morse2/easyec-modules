package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.ExtraTaskConsignCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;

import java.util.Date;

/**
 * Created by JunJie on 2014/9/2.
 */
public interface ExtraTaskConsign extends ExtraTaskConsignCtrl, DomainModel {

    /**
     * 任务委托常量类：未决状态，指还未批复
     */
    String TASK_CONSIGN_PENDING  = "pending";
    /**
     * 任务委托常量类：完成状态，指已经批复
     */
    String TASK_CONSIGN_FINISHED = "finished";

    String getTaskId();

    String getProcessInstanceId();

    String getConsignee();

    String getStatus();

    Date getCreateTime();

    Date getFinishTime();
}