package com.googlecode.easyec.modules.bpmn2.support.impl;

import java.util.HashMap;
import java.util.Map;

import static com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign.TASK_CONSIGN_AGREED;
import static com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign.TASK_CONSIGN_DISAGREED;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Created by JunJie on 2014/10/13.
 */
public class TaskResolveBehavior {

    private boolean agree;
    private boolean commented;
    private String status;
    private CommentBehavior commentBehavior;

    private Map<String, Object> variables = new HashMap<String, Object>();

    protected TaskResolveBehavior() { /* no op */ }

    protected TaskResolveBehavior(CommentBehavior commentBehavior) {
        this.commentBehavior = commentBehavior;
    }

    protected void setComment(CommentBehavior commentBehavior) {
        this.commentBehavior = commentBehavior;
        this.commented = (this.commentBehavior != null);
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

    public boolean isCommented() {
        return commented;
    }

    public String getCustomRole() {
        return isCommented() ? commentBehavior.getRole() : null;
    }

    public String getCustomAction() {
        return isBlank(status) ? agree ?
            TASK_CONSIGN_AGREED : TASK_CONSIGN_DISAGREED
            : status;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public static class TaskResolveBehaviorBuilder {

        private TaskResolveBehavior behavior = new TaskResolveBehavior();

        public TaskResolveBehaviorBuilder comment(CommentBehavior commentBehavior) {
            this.behavior.setComment(commentBehavior);
            return this;
        }

        public TaskResolveBehaviorBuilder agree() {
            this.behavior.agree = true;
            return this;
        }

        public TaskResolveBehaviorBuilder disagree() {
            this.behavior.agree = false;
            return this;
        }

        public TaskResolveBehaviorBuilder customStatus(String status) {
            this.behavior.status = status;
            return this;
        }

        public TaskResolveBehaviorBuilder variables(Map<String, Object> variables) {
            if (isNotEmpty(variables)) behavior.variables.putAll(variables);
            return this;
        }

        public TaskResolveBehaviorBuilder variable(String name, Object value) {
            behavior.variables.put(name, value);
            return this;
        }

        public TaskResolveBehavior build() {
            return behavior;
        }
    }
}
