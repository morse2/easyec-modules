package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.Group;
import com.googlecode.easyec.modules.bpmn2.domain.GroupUserRelation;
import com.googlecode.easyec.modules.bpmn2.domain.User;

public class GroupUserRelationPK implements GroupUserRelation {

    private User user;
    private Group group;

    public GroupUserRelationPK() { /* no op */ }

    public GroupUserRelationPK(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }
}