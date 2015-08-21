package com.googlecode.easyec.modules.bpmn2.command.persistence.entity;

import java.nio.charset.Charset;

/**
 * 扩展ACTIVITI的备注实现
 *
 * @author JunJie
 */
public class CommentEntity extends org.activiti.engine.impl.persistence.entity.CommentEntity {

    private static final long serialVersionUID = 6618911356735795774L;

    @Override
    public byte[] getFullMessageBytes() {
        return (fullMessage != null ? fullMessage.getBytes(Charset.forName("utf-8")) : null);
    }

    @Override
    public void setFullMessageBytes(byte[] fullMessageBytes) {
        this.fullMessage = (fullMessageBytes != null ? new String(fullMessageBytes, Charset.forName("utf-8")) : null);
    }
}
