package com.googlecode.easyec.modules.bpmn2.domain.ctrl;

import java.util.Date;

/**
 * Created by JunJie on 2014/10/10.
 */
public interface GroupCtrl {

    void setName(String name);

    void setDefault(boolean _default);

    void setEnable(boolean enable);

    void setCreateUser(String createUser);

    void setCreateTime(Date createTime);
}
