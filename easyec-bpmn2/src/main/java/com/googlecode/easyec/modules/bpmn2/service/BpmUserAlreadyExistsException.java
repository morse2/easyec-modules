package com.googlecode.easyec.modules.bpmn2.service;

/**
 * 表示用户已存在于系统中的异常类
 *
 * @author JunJie
 */
public class BpmUserAlreadyExistsException extends Exception {

    private static final long serialVersionUID = -8920887273363473312L;

    public BpmUserAlreadyExistsException() { /* no op */ }

    public BpmUserAlreadyExistsException(String message) {
        super(message);
    }

    public BpmUserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpmUserAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
