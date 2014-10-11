package com.googlecode.easyec.modules.bpmn2.support.impl;

/**
 * 流程废弃的行为类
 *
 * @author JunJie
 */
public class ProcessDiscardBehavior {

    private boolean commented;
    private CommentBehavior commentBehavior;

    private ProcessDiscardBehavior() { /* no op */ }

    protected ProcessDiscardBehavior(CommentBehavior commentBehavior) {
        this.commentBehavior = commentBehavior;
    }

    protected void setComment(CommentBehavior commentBehavior) {
        this.commentBehavior = commentBehavior;
        this.commented = (this.commentBehavior != null);
    }

    public boolean isCommented() {
        return commented;
    }

    public String getComment() {
        return isCommented() ? commentBehavior.getComment() : null;
    }

    public String getCommentType() {
        return isCommented() ? commentBehavior.getType() : null;
    }

    public static class ProcessDiscardBehaviorBuilder {

        private ProcessDiscardBehavior behavior = new ProcessDiscardBehavior();

        public ProcessDiscardBehaviorBuilder comment(CommentBehavior commentBehavior) {
            behavior.setComment(commentBehavior);
            return this;
        }

        public ProcessDiscardBehavior build() {
            return behavior;
        }
    }
}
