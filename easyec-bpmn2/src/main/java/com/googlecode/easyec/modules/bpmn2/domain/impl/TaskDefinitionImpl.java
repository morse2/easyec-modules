package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.TaskDefinition;

/**
 * Created by JunJie on 2014/9/19.
 */
public class TaskDefinitionImpl implements TaskDefinition {

    private String taskKey;
    private String taskName;

    @Override
    public String getTaskKey() {
        return taskKey;
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
