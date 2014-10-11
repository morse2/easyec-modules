package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.ProcessRoleCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;

/**
 * Created by JunJie on 2014/10/10.
 */
public interface ProcessRole extends DomainModel, ProcessRoleCtrl {

    String getCode();

    String getName();

    String getType();

    String getDescription();

    boolean isEnabled();

}
