package com.googlecode.easyec.modules.bpmn2.command;

import com.googlecode.easyec.modules.bpmn2.task.NewTask;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.util.Assert;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 创建一个新的任务的命令类
 *
 * @author JunJie
 */
public class CreateNewTaskCmd implements Command<Task> {

    private NewTask newTask;
    private ExecutionEntity execution;

    public CreateNewTaskCmd(NewTask newTask) {
        this(newTask, null);
    }

    public CreateNewTaskCmd(NewTask newTask, Execution execution) {
        Assert.notNull(newTask, "Task cannot be null.");

        this.newTask = newTask;
        this.execution = (ExecutionEntity) execution;
    }

    @Override
    public Task execute(CommandContext commandContext) {
        // 创建流程任务对象
        TaskEntity task = TaskEntity.create(
            commandContext.getProcessEngineConfiguration().getClock().getCurrentTime()
        );

        // 调用新增流程任务的方法
        if (task.getRevision() == 0) task.insert(execution);

        // 更新新任务属性到流程任务对象上
        _updateNewTaskProperty(task, newTask);

        return task;
    }

    /* 拷贝新任务对象的属性 */
    private void _updateNewTaskProperty(TaskEntity task, NewTask newTask) {
        if (isNotBlank(newTask.getAssignee())) task.setAssignee(newTask.getAssignee());
        if (isNotBlank(newTask.getCategory())) task.setCategory(newTask.getCategory());
        if (newTask.getDelegationState() != null) task.setDelegationState(newTask.getDelegationState());
        if (isNotBlank(newTask.getDescription())) task.setDescription(newTask.getDescription());
        if (newTask.getDueDate() != null) task.setDueDate(newTask.getDueDate());
        if (isNotBlank(newTask.getName())) task.setName(newTask.getName());
        if (isNotBlank(newTask.getOwner())) task.setOwner(newTask.getOwner());
        if (newTask.getPriority() > 0) task.setPriority(newTask.getPriority());

        if (newTask.getParentTask() != null) {
            task.setParentTaskId(newTask.getParentTask().getTaskId());
        }

        // 执行更新任务定义的方法
        if (isNotBlank(newTask.getTaskDefinitionKey())) {
            task.setProcessDefinitionId(execution.getProcessDefinitionId());
            task.setTaskDefinitionKey(newTask.getTaskDefinitionKey());
        }
    }
}
