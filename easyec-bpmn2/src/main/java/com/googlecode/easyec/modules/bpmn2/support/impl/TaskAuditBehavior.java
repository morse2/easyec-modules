package com.googlecode.easyec.modules.bpmn2.support.impl;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.collections.MapUtils.isNotEmpty;

/**
 * 任务审批行为的实现类
 *
 * @author JunJie
 */
public class TaskAuditBehavior {

    private boolean approved;
    private boolean rejected;
    private boolean partialRejected;
    private boolean commented = true;

    private String status;

    private CommentBehavior commentBehavior;

    private Map<String, Object> variables = new HashMap<String, Object>();
    private Map<String, Object> localVariables = new HashMap<String, Object>();

    protected TaskAuditBehavior() { /* no op */ }

    protected TaskAuditBehavior(CommentBehavior commentBehavior) {
        this.commentBehavior = commentBehavior;
    }

    public String getComment() {
        return commentBehavior != null
            ? commentBehavior.getComment()
            : null;
    }

    public String getCommentType() {
        return commentBehavior != null
            ? commentBehavior.getType()
            : null;
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

    public boolean isCommented() {
        return commented;
    }

    public String getStatus() {
        return status;
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
            behavior.commentBehavior = commentBehavior;
            return this;
        }

        public TaskAuditBehaviorBuilder approve() {
            behavior.approved = true;
            behavior.rejected = false;
            behavior.partialRejected = false;
            return this;
        }

        public TaskAuditBehaviorBuilder reject() {
            behavior.rejected = true;
            behavior.approved = false;
            behavior.partialRejected = false;
            return this;
        }

        public TaskAuditBehaviorBuilder partialReject() {
            behavior.partialRejected = true;
            behavior.approved = false;
            behavior.rejected = false;
            return this;
        }

        public TaskAuditBehaviorBuilder commented() {
            behavior.commented = true;
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
                behavior.approved || behavior.rejected || behavior.partialRejected,
                "You didn't indicate audit result."
            );

            return behavior;
        }
    }
}
