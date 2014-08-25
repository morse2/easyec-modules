package com.googlecode.easyec.modules.bpmn2.domain.impl;

import com.googlecode.easyec.modules.bpmn2.domain.ProcessMailConfig;
import com.googlecode.easyec.modules.bpmn2.domain.ctrl.ProcessMailConfigCtrl;
import com.googlecode.easyec.spirit.dao.id.annotation.Identifier;

@Identifier("SEQ_BPM_PROC_MAIL_CONF")
public class ProcessMailConfigImpl implements ProcessMailConfig, ProcessMailConfigCtrl {

    private static final long serialVersionUID = -581151595312286694L;
    private Long   uidPk;
    private String processKey;
    private String fireType;
    private String fileKey;
    private String mailClass;

    public Long getUidPk() {
        return uidPk;
    }

    public void setUidPk(Long uidPk) {
        this.uidPk = uidPk;
    }

    @Override
    public String getProcessKey() {
        return processKey;
    }

    @Override
    public void setProcessKey(String processKey) {
        this.processKey = processKey == null ? null : processKey.trim();
    }

    @Override
    public String getFireType() {
        return fireType;
    }

    @Override
    public void setFireType(String fireType) {
        this.fireType = fireType == null ? null : fireType.trim();
    }

    @Override
    public String getFileKey() {
        return fileKey;
    }

    @Override
    public void setFileKey(String fileKey) {
        this.fileKey = fileKey == null ? null : fileKey.trim();
    }

    @Override
    public String getMailClass() {
        return mailClass;
    }

    @Override
    public void setMailClass(String mailClass) {
        this.mailClass = mailClass == null ? null : mailClass.trim();
    }
}