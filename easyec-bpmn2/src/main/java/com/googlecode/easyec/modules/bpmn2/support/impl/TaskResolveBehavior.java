package com.googlecode.easyec.modules.bpmn2.support.impl;

import java.util.HashMap;
import java.util.Map;

import static com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign.TASK_CONSIGN_AGREED;
import static com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign.TASK_CONSIGN_DISAGREED;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * 任务解决的行为类
 *
 * @author JunJie
 */
public class TaskResolveBehavior extends CommentBehaviorAdapter {

    private boolean agree;
    private String status;

    private Map<String, Object> variables = new HashMap<String, Object>();

    protected TaskResolveBehavior() { /* no op */ }

    protected TaskResolveBehavior(CommentBehavior commentBehavior) {
        super(commentBehavior);
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
            this.behavior.setCommentBehavior(commentBehavior);
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
