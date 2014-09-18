package com.googlecode.easyec.modules.bpmn2.task;

import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessPriority;
import org.activiti.engine.task.DelegationState;

import java.io.Serializable;
import java.util.Date;

import static com.googlecode.easyec.modules.bpmn2.domain.enums.ProcessPriority.P5;

/**
 * 新任务对象类。
 * 该类主要用于手动创建新的任务时使用
 *
 * @author JunJie
 */
public class NewTask implements Serializable {

    private String processInstanceId;
    private String assignee;
    private String category;
    private DelegationState delegationState;
    private String description;
    private Date dueDate;
    private String name;
    private String owner;
    private TaskObject parentTask;
    private ProcessPriority priority = P5;
    private String taskDefinitionKey;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DelegationState getDelegationState() {
        return delegationState;
    }

    public void setDelegationState(DelegationState delegationState) {
        this.delegationState = delegationState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public TaskObject getParentTask() {
        return parentTask;
    }

    public void setParentTask(TaskObject parentTask) {
        this.parentTask = parentTask;
    }

    public int getPriority() {
        return priority.getPriority();
    }

    public void setPriority(ProcessPriority priority) {
        if (priority != null) this.priority = priority;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }
}
