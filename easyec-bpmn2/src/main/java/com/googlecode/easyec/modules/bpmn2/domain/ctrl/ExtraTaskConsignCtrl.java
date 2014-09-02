package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import java.util.Date;

/**
 * Created by JunJie on 2014/9/2.
 */
public interface ExtraTaskConsignCtrl {

    void setTaskId(String taskId);

    void setProcessInstanceId(String processInstanceId);

    void setConsignee(String consignee);

    void setStatus(String status);

    void setCreateTime(Date createTime);

    void setFinishTime(Date finishTime);
}
