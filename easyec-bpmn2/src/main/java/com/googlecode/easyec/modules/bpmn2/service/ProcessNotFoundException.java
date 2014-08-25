package com.googlecode.easyec.modules.bpmn2.service;

/**
 * 流程对象没有查找到的异常类
 *
 * @author JunJie
 */
public class ProcessNotFoundException extends ProcessPersistentException {

    private static final long serialVersionUID = -1364228184679352200L;

    public ProcessNotFoundException() { /* no op */ }

    public ProcessNotFoundException(String message) {
        super(message);
    }

    public ProcessNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessNotFoundException(Throwable cause) {
        super(cause);
    }
}
