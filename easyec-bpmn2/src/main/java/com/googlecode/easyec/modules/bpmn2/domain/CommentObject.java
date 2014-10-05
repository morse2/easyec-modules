package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.CommentObjectCtrl;
import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;
import com.googlecode.easyec.spirit.domain.DomainModel;

import java.util.Date;

/**
 * 流程的备注对象
 *
 * @author JunJie
 */
public interface CommentObject extends DomainModel, CommentObjectCtrl {

    String getId();

    String getUserId();

    String getContent();

    String getType();

    Date getCreateTime();

    TaskObject getTask();

    ExtraTaskObject getExtraTask();

    String getTaskRole();

    String getStatus();
}
