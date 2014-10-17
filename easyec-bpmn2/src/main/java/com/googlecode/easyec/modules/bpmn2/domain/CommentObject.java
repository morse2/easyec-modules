package com.googlecode.easyec.modules.bpmn2.domain;

import com.googlecode.easyec.modules.bpmn2.domain.ctrl.CommentObjectCtrl;
import com.googlecode.easyec.spirit.domain.DomainModel;

import java.nio.charset.Charset;
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

    String getContent(Charset charset);

    String getType();

    Date getCreateTime();

    String getTaskRole();

    String getTaskAction();
}
