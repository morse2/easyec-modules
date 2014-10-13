package com.googlecode.easyec.modules.bpmn2.support.impl;

import org.springframework.util.Assert;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 任务委托的行为类
 *
 * @author JunJie
 */
public class TaskConsignBehavior extends CommentBehaviorAdapter {

    private String userId;

    protected TaskConsignBehavior() { /* no op */ }

    protected TaskConsignBehavior(CommentBehavior commentBehavior) {
        super(commentBehavior);
    }

    public String getUserId() {
        return userId;
    }

    public static class TaskConsignBehaviorBuilder {

        private TaskConsignBehavior behavior = new TaskConsignBehavior();

        public TaskConsignBehaviorBuilder comment(CommentBehavior commentBehavior) {
            behavior.setCommentBehavior(commentBehavior);
            return this;
        }

        public TaskConsignBehaviorBuilder userId(String userId) {
            behavior.userId = userId;
            return this;
        }

        public TaskConsignBehavior build() {
            Assert.isTrue(isNotBlank(behavior.userId), "Consign user cannot be null.");
            return behavior;
        }
    }
}
