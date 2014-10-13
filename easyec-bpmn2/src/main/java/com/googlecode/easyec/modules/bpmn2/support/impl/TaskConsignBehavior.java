package com.googlecode.easyec.modules.bpmn2.support.impl;

/**
 * 任务委托的行为类
 *
 * @author JunJie
 */
public class TaskConsignBehavior {

    private String userId;
    private boolean commented;
    private CommentBehavior commentBehavior;

    protected TaskConsignBehavior() { /* no op */ }

    protected TaskConsignBehavior(CommentBehavior commentBehavior) {
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
        return isCommented() ? commentBehavior.getAction() : null;
    }

    public String getUserId() {
        return userId;
    }

    public static class TaskConsignBehaviorBuilder {

        private TaskConsignBehavior behavior = new TaskConsignBehavior();

        public TaskConsignBehaviorBuilder comment(CommentBehavior commentBehavior) {
            behavior.setComment(commentBehavior);
            return this;
        }

        public TaskConsignBehaviorBuilder userId(String userId) {
            behavior.userId = userId;
            return this;
        }

        public TaskConsignBehavior build() {
            return behavior;
        }
    }
}
