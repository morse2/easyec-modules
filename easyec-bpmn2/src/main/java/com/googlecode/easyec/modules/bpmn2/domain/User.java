package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.UserCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;

/**
 * Created by JunJie on 2014/10/10.
 */
public interface User extends DomainModel, UserCtrl {

    String getUserId();

    boolean isEnable();

}
