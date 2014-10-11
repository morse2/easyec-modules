package com.googlecode.easyec.modules.bpmn2.service;

/**
 * 流程用户未找到异常类
 *
 * @author JunJie
 */
public class BpmUserNotFoundException extends Exception {

    private static final long serialVersionUID = -7958480230682866879L;

    public BpmUserNotFoundException() { /* no op */ }

    public BpmUserNotFoundException(String message) {
        super(message);
    }

    public BpmUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpmUserNotFoundException(Throwable cause) {
        super(cause);
    }
}
