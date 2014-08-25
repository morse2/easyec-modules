package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.AttachmentObjectCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;

/**
 * 流程附件实体类
 *
 * @author JunJie
 */
public interface AttachmentObject extends DomainModel, AttachmentObjectCtrl {

    String getId();

    String getName();

    String getUserId();

    String getDescription();

    String getTaskId();

    String getUrl();

    byte[] getContent();
}
