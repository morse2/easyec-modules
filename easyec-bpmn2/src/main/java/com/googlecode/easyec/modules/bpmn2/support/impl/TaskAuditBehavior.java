package com.googlecode.easyec.modules.bpmn2.support.impl;

import com.googlecode.easyec.modules.bpmn2.domain.TaskObject;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

import static com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskObject.*;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 任务审批行为的实现类
 *
 * @author JunJie
 */
public class TaskAuditBehavior extends CommentBehaviorAdapter {

    private boolean approved;
    private boolean rejected;
    private boolean partialRejected;
    private boolean revoked;
    private boolean selfTask;

    private String status;

    private Map<String, Object> variables = new HashMap<String, Object>();
    private Map<String, Object> localVariables = new HashMap<String, Object>();

    protected TaskAuditBehavior() { /* no op */ }

    protected TaskAuditBehavior(CommentBehavior commentBehavior) {
        super(commentBehavior);
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public boolean isPartialRejected() {
        return partialRejected;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public String getStatus() {
        if (isNotBlank(status)) return status;

        CommentBehavior commentBehavior = getCommentBehavior();
        if (isCommented() && isNotBlank(commentBehavior.getAction())) {
            return commentBehavior.getAction();
        }

        return selfTask ? EXTRA_TASK_STATUS_RESUBMIT
            : rejected ? EXTRA_TASK_STATUS_REJECTED
            : EXTRA_TASK_STATUS_APPROVED;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public Map<String, Object> getLocalVariables() {
        return localVariables;
    }

    public static class TaskAuditBehaviorBuilder {

        private TaskAuditBehavior behavior = new TaskAuditBehavior();

        public TaskAuditBehaviorBuilder comment(CommentBehavior commentBehavior) {
            this.behavior.setCommentBehavior(commentBehavior);
            return this;
        }

        public TaskAuditBehaviorBuilder approve() {
            return approve(null);
        }

        public TaskAuditBehaviorBuilder approve(TaskObject task) {
            behavior.approved = true;
            behavior.rejected = false;
            behavior.partialRejected = false;
            behavior.revoked = false;

            if (task != null) {
                behavior.selfTask = task.getAssignee().equals(task.getProcessObject().getCreateUser());
            }

            return this;
        }

        public TaskAuditBehaviorBuilder reject() {
            behavior.rejected = true;
            behavior.approved = false;
            behavior.partialRejected = false;
            behavior.revoked = false;
            return this;
        }

        public TaskAuditBehaviorBuilder partialReject() {
            behavior.partialRejected = true;
            behavior.approved = false;
            behavior.rejected = false;
            behavior.revoked = false;
            return this;
        }

        public TaskAuditBehaviorBuilder revoked() {
            behavior.revoked = true;
            behavior.approved = false;
            behavior.rejected = false;
            behavior.partialRejected = false;
            return this;
        }

        public TaskAuditBehaviorBuilder status(String status) {
            behavior.status = status;
            return this;
        }

        public TaskAuditBehaviorBuilder variables(Map<String, Object> variables) {
            if (isNotEmpty(variables)) behavior.variables.putAll(variables);
            return this;
        }

        public TaskAuditBehaviorBuilder localVariables(Map<String, Object> variables) {
            if (isNotEmpty(variables)) behavior.localVariables.putAll(variables);
            return this;
        }

        public TaskAuditBehaviorBuilder variable(String name, Object value) {
            behavior.variables.put(name, value);
            return this;
        }

        public TaskAuditBehaviorBuilder localVariable(String name, Object value) {
            behavior.localVariables.put(name, value);
            return this;
        }

        public TaskAuditBehavior build() {
            Assert.isTrue(
                behavior.approved || behavior.rejected || behavior.partialRejected || behavior.revoked,
                "You didn't indicate audit result."
            );

            return behavior;
        }
    }
}
