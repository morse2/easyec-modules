package com.googlecode.easyec.modules.bpmn2.support.impl;

/**
 * 抽象的备注行为的适配器类。
 * 子类可以继承自此类以获取公共方法
 *
 * @author JunJie
 */
public abstract class CommentBehaviorAdapter {

    private boolean commented;
    private CommentBehavior commentBehavior;

    protected CommentBehaviorAdapter() { /* no op */ }

    protected CommentBehaviorAdapter(CommentBehavior commentBehavior) {
        this.commentBehavior = commentBehavior;
    }

    /**
     * 设置备注行为对象
     *
     * @param commentBehavior 备注行为对象
     */
    protected void setCommentBehavior(CommentBehavior commentBehavior) {
        this.commentBehavior = commentBehavior;
        this.commented = (this.commentBehavior != null);
    }

    /**
     * 返回当前备注的行为对象
     *
     * @return 备注行为对象
     */
    protected CommentBehavior getCommentBehavior() {
        return commentBehavior;
    }

    /**
     * 标记该行为是否要备注
     *
     * @return bool值
     */
    public boolean isCommented() {
        return commented;
    }

    /**
     * 如果标记备注，则返回备注的内容
     *
     * @return 备注内容
     */
    public String getComment() {
        return isCommented() ? commentBehavior.getComment() : null;
    }

    /**
     * 如果标记备注，则返回备注的类型
     *
     * @return 备注类型
     */
    public String getCommentType() {
        return isCommented() ? commentBehavior.getType() : null;
    }

    /**
     * 如果标记备注，则返回自定义的角色
     *
     * @return 备注对应的角色
     */
    public String getCustomRole() {
        return isCommented() ? commentBehavior.getRole() : null;
    }

    /**
     * 如果标记备注，则返回自定义的行为
     *
     * @return 备注对应的行为
     */
    public String getCustomAction() {
        return isCommented() ? commentBehavior.getAction() : null;
    }
}
