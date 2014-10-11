package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.Group;
import com.googlecode.easyec.spirit.dao.id.annotation.Identifier;

import java.util.Date;

@Identifier("SEQ_BPM_GROUP")
public class GroupImpl implements Group {

    private Long uidPk;
    private String name;
    private boolean _default;
    private boolean enable;
    private String createUser;
    private Date createTime;

    public Long getUidPk() {
        return uidPk;
    }

    public void setUidPk(Long uidPk) {
        this.uidPk = uidPk;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public boolean isDefault() {
        return _default;
    }

    @Override
    public void setDefault(boolean _default) {
        this._default = _default;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String getCreateUser() {
        return createUser;
    }

    @Override
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}