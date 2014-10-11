package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.GroupUserRelationCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;

/**
 * Created by JunJie on 2014/10/10.
 */
public interface GroupUserRelation extends DomainModel, GroupUserRelationCtrl {

    User getUser();

    Group getGroup();
}
