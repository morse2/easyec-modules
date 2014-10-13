package com.googlecode.easyec.modules.bpmn2.support.impl;

/**
 * 流程废弃的行为类
 *
 * @author JunJie
 */
public class ProcessDiscardBehavior extends CommentBehaviorAdapter {

    protected ProcessDiscardBehavior() { /* no op */ }

    protected ProcessDiscardBehavior(CommentBehavior commentBehavior) {
        super(commentBehavior);
    }

    public static class ProcessDiscardBehaviorBuilder {

        private ProcessDiscardBehavior behavior = new ProcessDiscardBehavior();

        public ProcessDiscardBehaviorBuilder comment(CommentBehavior commentBehavior) {
            behavior.setCommentBehavior(commentBehavior);
            return this;
        }

        public ProcessDiscardBehavior build() {
            return behavior;
        }
    }
}
