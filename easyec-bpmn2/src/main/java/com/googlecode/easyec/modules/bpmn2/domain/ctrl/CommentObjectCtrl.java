package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import com.googlecode.easyec.modules.bpmn2.domain.enums.CommentTypes;

import java.util.Date;

/**
 * Created by 俊杰 on 2014/7/17.
 */
public interface CommentObjectCtrl {

    void setId(String id);

    void setUserId(String userId);

    void setContent(String content);

    void setType(CommentTypes type);

    void setCreateTime(Date createTime);
}
