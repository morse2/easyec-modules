package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.GroupCtrl;
import com.googlecode.easyec.spirit.domain.GenericPersistentDomainModel;

import java.util.Date;

/**
 * Created by JunJie on 2014/10/10.
 */
public interface Group extends GenericPersistentDomainModel<Long>, GroupCtrl {

    String getName();

    boolean isDefault();

    boolean isEnable();

    String getCreateUser();

    Date getCreateTime();
}
