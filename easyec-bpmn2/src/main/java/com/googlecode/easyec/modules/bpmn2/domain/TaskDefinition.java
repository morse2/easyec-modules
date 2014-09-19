package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.spirit.domain.DomainModel;

/**
 * Created by JunJie on 2014/9/19.
 */
public interface TaskDefinition extends DomainModel {

    String getTaskKey();

    void setTaskKey(String taskKey);

    String getTaskName();

    void setTaskName(String taskName);
}
