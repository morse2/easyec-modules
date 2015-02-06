package com.googlecode.easyec.modules.bpmn2.support.impl;

/**
 * 流程召回的行为类
 *
 * @author JunJie
 */
public class ProcessRecallBehavior extends CommentBehaviorAdapter {

    protected ProcessRecallBehavior() { /* no op */ }

    protected ProcessRecallBehavior(CommentBehavior commentBehavior) {
        super(commentBehavior);
    }

    public static class ProcessRecallBehaviorBuilder {

        private ProcessRecallBehavior behavior = new ProcessRecallBehavior();

        public ProcessRecallBehaviorBuilder comment(CommentBehavior commentBehavior) {
            behavior.setCommentBehavior(commentBehavior);
            return this;
        }

        public ProcessRecallBehavior build() {
            return behavior;
        }
    }
}
