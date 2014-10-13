package com.googlecode.easyec.modules.bpmn2.support.impl;

import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;

/**
 * 用于标记备注行为的类
 *
 * @author JunJie
 */
public class CommentBehavior {

    private String comment;
    private String type;
    private String role;
    private String action;

    protected CommentBehavior() { /* no op */ }

    protected CommentBehavior(String comment) {
        this(comment, ((String) null));
    }

    protected CommentBehavior(String comment, CommentTypes type) {
        this(comment, type != null ? type.name() : null);
    }

    protected CommentBehavior(String comment, String type) {
        this.comment = comment;
        this.type = type;
    }

    protected void setRole(String role) {
        this.role = role;
    }

    protected void setAction(String action) {
        this.action = action;
    }

    public String getComment() {
        return comment;
    }

    public String getType() {
        return type;
    }

    public String getRole() {
        return role;
    }

    public String getAction() {
        return action;
    }

    public static class CommentBehaviorBuilder {

        private CommentBehavior behavior = new CommentBehavior();

        public CommentBehaviorBuilder comment(String comment) {
            behavior.comment = comment;
            return this;
        }

        public CommentBehaviorBuilder type(String type) {
            behavior.type = type;
            return this;
        }

        public CommentBehaviorBuilder type(CommentTypes type) {
            if (type != null) behavior.type = type.name();
            return this;
        }

        public CommentBehaviorBuilder role(String role) {
            behavior.setRole(role);
            return this;
        }

        public CommentBehaviorBuilder action(String action) {
            behavior.setAction(action);
            return this;
        }

        public CommentBehavior build() {
            return behavior;
        }
    }
}
