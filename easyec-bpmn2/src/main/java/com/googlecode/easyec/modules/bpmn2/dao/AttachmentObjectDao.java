package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.AttachmentObject;

import java.util.List;

/**
 * Created by 俊杰 on 2014/7/14.
 */
public interface AttachmentObjectDao {

    List<AttachmentObject> selectByProcessInstanceId(String processInstanceId);
}
