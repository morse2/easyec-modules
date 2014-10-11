package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import com.googlecode.easyec.modules.bpmn2.domain.Group;
import com.googlecode.easyec.modules.bpmn2.domain.User;

/**
 * Created by JunJie on 2014/10/10.
 */
public interface GroupUserRelationCtrl {

    void setUser(User user);

    void setGroup(Group group);
}
