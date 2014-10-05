package com.googlecode.easyec.modules.bpmn2.support.impl;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.collections.MapUtils.isNotEmpty;

/**
 * 流程启动行为的类
 *
 * @author JunJie
 */
public class ProcessStartBehavior {

    private boolean commented;

    private CommentBehavior commentBehavior;

    private Map<String, Object> variables = new HashMap<String, Object>();

    protected ProcessStartBehavior() { /* no op */ }

    protected ProcessStartBehavior(CommentBehavior commentBehavior) {
        setComment(commentBehavior);
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

    public Map<String, Object> getVariables() {
        return variables;
    }

    public static class ProcessStartBehaviorBuilder {

        private ProcessStartBehavior behavior = new ProcessStartBehavior();

        public ProcessStartBehaviorBuilder comment(CommentBehavior commentBehavior) {
            this.behavior.setComment(commentBehavior);
            return this;
        }

        public ProcessStartBehaviorBuilder variable(String name, Object value) {
            this.behavior.variables.put(name, value);
            return this;
        }

        public ProcessStartBehaviorBuilder variables(Map<String, Object> variables) {
            if (isNotEmpty(variables)) this.behavior.variables.putAll(variables);
            return this;
        }

        public ProcessStartBehavior build() {
            return behavior;
        }
    }
}
